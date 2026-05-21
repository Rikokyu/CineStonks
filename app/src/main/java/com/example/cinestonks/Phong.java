package com.example.cinestonks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Phong implements Serializable {
    private String MaPhong, TenPhong, MaRap;
    private int SoLuongGhe;
    private List<Ghe> danhSachGhe;

    public Phong(String maPhong, String tenPhong, String maRap, int soLuongGhe) {
        MaPhong = maPhong;
        TenPhong = tenPhong;
        MaRap = maRap;
        SoLuongGhe = soLuongGhe;
        this.danhSachGhe = new ArrayList<>();
    }

    public Phong() {
        this.danhSachGhe = new ArrayList<>();
    }

    // Class đại diện cho từng chiếc ghế trong phòng
    public static class Ghe implements Serializable {
        private String tenGhe;
        private int loaiGhe; // 0: Thường, 1: Đôi, 2: Đã bán
        private boolean dangChon = false;

        public static final int LOAI_THUONG = 0;
        public static final int LOAI_DOI = 1;
        public static final int LOAI_DA_BAN = 2;

        public Ghe(String tenGhe, int loaiGhe) {
            this.tenGhe = tenGhe;
            this.loaiGhe = loaiGhe;
        }

        public String getTenGhe() { return tenGhe; }
        public int getLoaiGhe() { return loaiGhe; }
        public void setLoaiGhe(int loaiGhe) { this.loaiGhe = loaiGhe; }
        public boolean isDangChon() { return dangChon; }
        public void setDangChon(boolean dangChon) { this.dangChon = dangChon; }
    }

    public String getMaPhong() { return MaPhong; }
    public void setMaPhong(String maPhong) { MaPhong = maPhong; }
    public String getTenPhong() { return TenPhong; }
    public void setTenPhong(String tenPhong) { TenPhong = tenPhong; }
    public String getMaRap() { return MaRap; }
    public void setMaRap(String maRap) { MaRap = maRap; }
    public int getSoLuongGhe() { return SoLuongGhe; }
    public void setSoLuongGhe(int soLuongGhe) { SoLuongGhe = soLuongGhe; }
    public List<Ghe> getDanhSachGhe() { return danhSachGhe; }
    public void setDanhSachGhe(List<Ghe> danhSachGhe) { this.danhSachGhe = danhSachGhe; }
}
