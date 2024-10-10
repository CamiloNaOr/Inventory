package com.Inventory.Inventory;

import java.util.List;

public class InventoryFailedEvent {

    private String orderId;
    private List<String> productList;
    private String status;
    private String reason;

    // Constructor, Getters y Setters

    public InventoryFailedEvent(int orderId, List<String> productList, String status, String reason) {
        this.orderId = orderId;
        this.productList = productList;
        this.status = status;
        this.reason = reason;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "InventoryFailedEvent{" +
                "orderId='" + orderId + '\'' +
                ", productList=" + productList +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}

