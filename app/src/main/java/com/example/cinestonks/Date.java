package com.example.cinestonks;

public class Date {
    private String dayOfWeek;
    private String date;
    private boolean isSelected;

    public Date(String dayOfWeek, String date, boolean isSelected) {
        this.dayOfWeek = dayOfWeek;
        this.date = date;
        this.isSelected = isSelected;
    }

    // Getters và Setters
    public String getDayOfWeek() { return dayOfWeek; }
    public String getDate() { return date; }
    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { isSelected = selected; }
}
