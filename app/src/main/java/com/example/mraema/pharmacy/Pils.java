package com.example.mraema.pharmacy;

public class Pils {


    public Pils() {
    }



    private String pillName;
    private String pilCount;
    private int pilPrice;
    private int totalCost;
    private String imageKey;



    public Pils(String pilName, String pilcount, int totalPrice, int pilPrice, String imageKey) {
        this.pillName = pilName;
        this.pilCount = pilcount;
        this.pilPrice = pilPrice;
        this.totalCost = totalPrice;
        this.imageKey = imageKey;


    }


    public String getPillName() {
        return pillName;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public String getPilCount() {
        return pilCount;
    }

    public void setPilCount(String pilCount) {
        this.pilCount = pilCount;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getPilPrice() {
        return pilPrice;
    }

    public void setPilPrice(int pilPrice) {
        this.pilPrice = pilPrice;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }
}
