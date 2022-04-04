package com.example.mraema.pharmacy;

public class Orders {
    private String pillName;
    private String pillQuantity;
    private String pillPrice;
    private String dateOfOrdered;
    private String customerID;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    public Orders() {
    }

    public Orders(String pillName, String pillQuantity, String pillPrice, String dateOfOrdered, String customerID, String userName) {
        this.pillName = pillName;
        this.pillQuantity = pillQuantity;
        this.pillPrice = pillPrice;
        this.dateOfOrdered = dateOfOrdered;
        this.customerID = customerID;
        this.userName = userName;

    }

    public String getPillName() {
        return pillName;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public String getPillQuantity() {
        return pillQuantity;
    }

    public void setPillQuantity(String pillQuantity) {
        this.pillQuantity = pillQuantity;
    }

    public String getPillPrice() {
        return pillPrice;
    }

    public void setPillPrice(String pillPrice) {
        this.pillPrice = pillPrice;
    }


    public String getDateOfOrdered() {
        return dateOfOrdered;
    }

    public void setDateOfOrdered(String dateOfOrdered) {
        this.dateOfOrdered = dateOfOrdered;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
}
