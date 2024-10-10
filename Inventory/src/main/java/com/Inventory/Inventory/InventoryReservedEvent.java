package com.Inventory.Inventory;

import java.util.List;

public class InventoryReservedEvent {

    private String orderId;
    private List<String> productList;
    private String status;

    // Constructor, Getters y Setters

    public InventoryReservedEvent(int orderId, List<String> productList, String status) {
        this.orderId = orderId;
        this.productList = productList;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<String> getProductList() {
        return productList;
    }

    public void setProductList(List<String> productList) {
        this.productList = productList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InventoryReservedEvent{" +
                "orderId='" + orderId + '\'' +
                ", productList=" + productList +
                ", status='" + status + '\'' +
                '}';
    }
}
