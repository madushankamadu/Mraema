package com.example.mraema.user;



public class Apharmacy implements Comparable<Apharmacy> {

    public double distance;
    public String name;
    public String town;
    public String id;

    public Apharmacy(){

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int compareTo(Apharmacy apharmacy) {
        return Double.compare(this.distance, apharmacy.distance);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

