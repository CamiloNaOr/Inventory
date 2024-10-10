package com.Inventory.Inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderCreatedListener {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private InventoryEventProducer inventoryEventProducer;

    @KafkaListener(topics = "order-created", groupId = "notification-group")
    public void consume(OrderCreatedEvent orderCreatedEvent) {
        List<String> productList = orderCreatedEvent.getProductList(); // IDs de los productos
        int orderId = orderCreatedEvent.getOrderId();
        boolean sufficientInventory = true;

        // Verificar inventario
        for (String productId : productList) {
            Optional<Producto> productoOptional = productoRepository.findById(Integer.parseInt(productId));

            if (productoOptional.isPresent()) {
                Producto producto = productoOptional.get();
                if (producto.getCantidad() <= 0) {
                    System.out.println("No hay suficiente inventario para el producto: " + producto.getNombre());
                    sufficientInventory = false;
                    break;
                }
            } else {
                System.out.println("Producto no encontrado: " + productId);
                sufficientInventory = false;
                break;
            }
        }

        if (sufficientInventory) {
            // Actualizar el inventario si hay suficiente
            for (String productId : productList) {
                Optional<Producto> productoOptional = productoRepository.findById(Integer.parseInt(productId));

                if (productoOptional.isPresent()) {
                    Producto producto = productoOptional.get();
                    producto.setCantidad(producto.getCantidad() - 1);
                    productoRepository.save(producto); // Guardar el stock actualizado
                    System.out.println("Stock actualizado para producto: " + producto.getNombre());
                }
            }

            // Emitir el evento InventoryReserved
            InventoryReservedEvent inventoryReservedEvent = new InventoryReservedEvent(orderId, productList, "reserved");
            inventoryEventProducer.sendInventoryReservedEvent(inventoryReservedEvent);
        } else {
            // Emitir el evento InventoryFailed si no hay suficiente inventario
            InventoryFailedEvent inventoryFailedEvent = new InventoryFailedEvent(orderId, productList, "failed", "Insufficient stock");
            inventoryEventProducer.sendInventoryFailedEvent(inventoryFailedEvent);
        }
    }
}

