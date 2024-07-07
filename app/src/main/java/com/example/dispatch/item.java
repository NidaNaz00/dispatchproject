package com.example.dispatch;
// Item.java

public class item {
    private String itemId;
    private String itemName;
    private String itemCategory;
    private String source;
    private String destination;

    // Default constructor (required for Firebase)
    public item() {
        // Default constructor required for calls to DataSnapshot.getValue(Item.class)
    }

    // Constructor with parameters
    public item(String itemId, String itemName, String itemCategory, String source, String destination) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.source = source;
        this.destination = destination;
    }

    // Getters and setters (required for Firebase)
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
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
}
