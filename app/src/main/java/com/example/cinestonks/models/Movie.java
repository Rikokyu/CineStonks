package com.example.cinestonks.models;

import java.util.List;

public class Movie {
    public Movie(){ }

    public String getMaPhim() {
        return MaPhim;
    }

    public String getTenPhim() {
        return TenPhim;
    }

    public String getTheLoai() {
        return TheLoai;
    }

    public int getDoTuoi() {
        return DoTuoi;
    }

    public void setMaPhim(String maPhim) {
        MaPhim = maPhim;
    }

    public void setTenPhim(String tenPhim) {
        TenPhim = tenPhim;
    }

    public void setTheLoai(String theLoai) {
        TheLoai = theLoai;
    }

    public void setDoTuoi(int doTuoi) {
        DoTuoi = doTuoi;
    }

    private String MaPhim;
    private String TenPhim;
    private String TheLoai;
    private int DoTuoi;

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public int getThoiLuong() {
        return ThoiLuong;
    }

    public void setThoiLuong(int thoiLuong) {
        ThoiLuong = thoiLuong;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    private String MoTa;
    private int ThoiLuong;
    private String TrangThai;
    private String URL;
    private List<SuatChieu> suatChieuList; // QUAN TRỌNG

    public List<SuatChieu> getSuatChieuList() {
        return suatChieuList;
    }

}