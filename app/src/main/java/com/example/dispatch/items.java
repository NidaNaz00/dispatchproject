package com.example.dispatch;

public class items {
    String id;
    String name;
    String status;

    public items() {
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


    public items(String id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;

    }
}
