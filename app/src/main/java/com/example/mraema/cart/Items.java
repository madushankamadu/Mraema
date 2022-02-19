package com.example.mraema.cart;

public class Items {

    public Items() {
    }


    private String userName, itemName, pharmacyId,count, pharmacyName, unitPrice;

    public Items(String userName, String itemName, String pharmacyName, String count, String pharmacyId, String unitPrice) {
        this.userName = userName;
        this.itemName = itemName;
        this.pharmacyId = pharmacyId;
        this.pharmacyName = pharmacyName;
        this.count = count;
        this.unitPrice = unitPrice;
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

    public String getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId;
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
}
