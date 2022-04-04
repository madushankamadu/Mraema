package com.example.mraema.cart;

public class Items {

    public Items() {
    }


    private String userName;
    private String itemName;
    private String pharmacyUserId;
    private String count;
    private String pharmacyName;
    private String unitPrice;
    private int totalPrice;

    public Items(String userName, String itemName, String pharmacyName, String count, String pharmacyUserId, String unitPrice, int totalPrice) {
        this.userName = userName;
        this.itemName = itemName;
        this.pharmacyUserId = pharmacyUserId;
        this.pharmacyName = pharmacyName;
        this.count = count;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;

    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPharmacyUserId() {
        return pharmacyUserId;
    }

    public void setPharmacyUserId(String pharmacyId) {
        this.pharmacyUserId = pharmacyId;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
