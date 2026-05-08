package com.example.cinestonks;

public class SuatChieu {
    public void setMaSuat(String maSuat) {
        MaSuat = maSuat;
    }

    private String MaSuat, NgayChieu, GioChieu, MaPhim, MaPhong;

    public SuatChieu() {}

    public String getGioChieu() { return GioChieu; }
    public String getMaPhim() { return MaPhim; }
    public String getNgayChieu() { return NgayChieu; }
    public String getMaSuat() { return MaSuat; }
    public String getMaPhong() { return MaPhong; }
}