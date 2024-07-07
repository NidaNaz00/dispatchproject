package com.example.dispatch;

public class dispatch {
    private String productId;
    private String truckId;
    private String source;
    private String destination;
    private String status;

    public dispatch(String productId, String truckId, String source, String destination, String status) {
        this.productId = productId;
        this.truckId = truckId;
        this.source = source;
        this.destination = destination;
        this.status = status;
    }

    public dispatch() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
