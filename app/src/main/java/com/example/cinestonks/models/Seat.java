package com.example.cinestonks.models;

public class Seat {
    // Các trường tương ứng với cấu trúc trong Firebase
    private String MaGhe;
    private String LoaiGhe;
    private String MaPhong;
    private String MaSuatChieu;
    private String TenGhe;
    private String TrangThai; // Trong Firebase đang là dạng String "O" (0)

    // Biến phụ trợ cho logic hiển thị trên UI
    private boolean isSelected = false;

    // Constructor mặc định (cần thiết cho Firebase)
    public Seat() {
    }

    // Constructor đầy đủ
    public Seat(String maGhe, String loaiGhe, String maPhong, String maSuatChieu, String tenGhe, String trangThai) {
        MaGhe = maGhe;
        LoaiGhe = loaiGhe;
        MaPhong = maPhong;
        MaSuatChieu = maSuatChieu;
        TenGhe = tenGhe;
        TrangThai = trangThai;
    }

    // Các hàm Getter và Setter
    public String getMaGhe() { return MaGhe; }
    public void setMaGhe(String maGhe) { MaGhe = maGhe; }

    public String getLoaiGhe() { return LoaiGhe; }
    public void setLoaiGhe(String loaiGhe) { LoaiGhe = loaiGhe; }

    public String getMaPhong() { return MaPhong; }
    public void setMaPhong(String maPhong) { MaPhong = maPhong; }

    public String getMaSuatChieu() { return MaSuatChieu; }
    public void setMaSuatChieu(String maSuatChieu) { MaSuatChieu = maSuatChieu; }

    public String getTenGhe() { return TenGhe; }
    public void setTenGhe(String tenGhe) { TenGhe = tenGhe; }

    public String getTrangThai() { return TrangThai; }
    public void setTrangThai(String trangThai) { TrangThai = trangThai; }

    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { isSelected = selected; }
}