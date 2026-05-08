package com.example.cinestonks;

public class Seat {
    private String name;
    private int type; // 0: Normal, 1: Double, 2: Sold
    private boolean isSelected = false;

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_DOUBLE = 1;
    public static final int TYPE_SOLD = 2;

    public Seat(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
