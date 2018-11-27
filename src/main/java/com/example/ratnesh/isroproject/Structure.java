package com.example.ratnesh.isroproject;

/**
 * Created by QUEST on 6/5/2018.
 */

public class Structure {

    double lat1,long1;
    String Name;
    public String  getName() {
        return Name;
    }
    
    public double getLat1() {
        return lat1;
    }

       public double getLong1() {
        return long1;
    }

    public void setName(String Name) {

        this.Name = Name;
    }
    public void setLat1(double lat1) {

        this.lat1 = lat1;
    }
    public void setLong1(double long1) {

        this.long1= long1;
    }

}
