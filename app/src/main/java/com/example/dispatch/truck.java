package com.example.dispatch;

public class truck {
String id;

    public truck() {
    }

    String name;
String status;
String model;

    public truck(String id, String name, String status, String model) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
