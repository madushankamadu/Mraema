package com.example.mraema;



public class Distances implements Comparable<Distances> {

    public double distance;
    public String name;
    public String town;

    public Distances(){

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int compareTo(Distances distances) {
        return Double.compare(this.distance, distances.distance);
    }
}

