package com.example.cinestonks.models;
import java.util.List;

public class HoaDon {

    private List<String> DanhSachGhe;
    private String MaHoaDon;
    private String MaND;
    private String NgayThanhToan;
    private String PhongChieu;
    private String SuatChieu;
    private String TenPhim;
    private String TenRap;
    private String TheLoai;
    private String TongTien;
    private String TrangThai;

    // Constructor rỗng bắt buộc cho Firebase
    public HoaDon() {
    }

    public HoaDon(List<String> danhSachGhe,
                  String maHoaDon,
                  String maND,
                  String ngayThanhToan,
                  String phongChieu,
                  String suatChieu,
                  String tenPhim,
                  String tenRap,
                  String theLoai,
                  String tongTien,
                  String trangThai) {

        DanhSachGhe = danhSachGhe;
        MaHoaDon = maHoaDon;
        MaND = maND;
        NgayThanhToan = ngayThanhToan;
        PhongChieu = phongChieu;
        SuatChieu = suatChieu;
        TenPhim = tenPhim;
        TenRap = tenRap;
        TheLoai = theLoai;
        TongTien = tongTien;
        TrangThai = trangThai;
    }

    public List<String> getDanhSachGhe() {
        return DanhSachGhe;
    }

    public void setDanhSachGhe(List<String> danhSachGhe) {
        DanhSachGhe = danhSachGhe;
    }

    public String getMaHoaDon() {
        return MaHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        MaHoaDon = maHoaDon;
    }

    public String getMaND() {
        return MaND;
    }

    public void setMaND(String maND) {
        MaND = maND;
    }

    public String getNgayThanhToan() {
        return NgayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        NgayThanhToan = ngayThanhToan;
    }

    public String getPhongChieu() {
        return PhongChieu;
    }

    public void setPhongChieu(String phongChieu) {
        PhongChieu = phongChieu;
    }

    public String getSuatChieu() {
        return SuatChieu;
    }

    public void setSuatChieu(String suatChieu) {
        SuatChieu = suatChieu;
    }

    public String getTenPhim() {
        return TenPhim;
    }

    public void setTenPhim(String tenPhim) {
        TenPhim = tenPhim;
    }

    public String getTenRap() {
        return TenRap;
    }

    public void setTenRap(String tenRap) {
        TenRap = tenRap;
    }

    public String getTheLoai() {
        return TheLoai;
    }

    public void setTheLoai(String theLoai) {
        TheLoai = theLoai;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String tongTien) {
        TongTien = tongTien;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }
}
