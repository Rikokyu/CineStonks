package com.example.cinestonks;

public class Ve {
    private String MaVe, TenVe;
    private int GiaTien;

    public String getMaVe() {
        return MaVe;
    }

    public void setMaVe(String maVe) {
        MaVe = maVe;
    }

    public String getTenVe() {
        return TenVe;
    }

    public void setTenVe(String tenVe) {
        TenVe = tenVe;
    }

    public int getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(int giaTien) {
        GiaTien = giaTien;
    }

    public Ve(String maVe, String tenVe, int giaTien) {
        MaVe = maVe;
        TenVe = tenVe;
        GiaTien = giaTien;
    }
    public Ve(){ }
}


