package com.ringolatechapps.pgrooms;

public class Room {

    private String pgName;
    private String costPerMonth;
    private String seater;

    public Room() {
    }

    public Room(String pgName, String costPerMonth, String seater) {
        this.pgName = pgName;
        this.costPerMonth = costPerMonth;
        this.seater = seater;
    }

    public String getPgName() {
        return pgName;
    }

    public String getCostPerMonth() {
        return costPerMonth;
    }

    public String getSeater() {
        return seater;
    }
}
