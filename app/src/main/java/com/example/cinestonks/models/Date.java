package com.example.cinestonks.models;

public class Date {
    private String dayOfWeek;
    private String date;
    private String fullDate; // Thêm trường này để lưu ngày đầy đủ dd/MM/yyyy
    private boolean isSelected;

    public Date(String dayOfWeek, String date, String fullDate, boolean isSelected) {
        this.dayOfWeek = dayOfWeek;
        this.date = date;
        this.fullDate = fullDate;
        this.isSelected = isSelected;
    }

    public String getDayOfWeek() { return dayOfWeek; }
    public String getDate() { return date; }
    public String getFullDate() { return fullDate; }
    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { isSelected = selected; }
}
