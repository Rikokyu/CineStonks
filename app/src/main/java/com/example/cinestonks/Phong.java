package com.example.cinestonks;

public class Phong {
    private String MaPhong, TenPhong, MaRap;
    private int SoLuongGhe;
    public Phong(String maPhong, String tenPhong, String maRap, int soLuongGhe) {
        MaPhong = maPhong;
        TenPhong = tenPhong;
        MaRap = maRap;
        SoLuongGhe = soLuongGhe;
    }

    public Phong(){}

    public String getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(String maPhong) {
        MaPhong = maPhong;
    }

    public String getTenPhong() {
        return TenPhong;
    }

    public void setTenPhong(String tenPhong) {
        TenPhong = tenPhong;
    }

    public String getMaRap() {
        return MaRap;
    }

    public void setMaRap(String maRap) {
        MaRap = maRap;
    }

    public int getSoLuongGhe() {
        return SoLuongGhe;
    }

    public void setSoLuongGhe(int soLuongGhe) {
        SoLuongGhe = soLuongGhe;
    }
}
