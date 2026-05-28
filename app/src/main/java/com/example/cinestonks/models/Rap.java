package com.example.cinestonks.models;

public class Rap {
    private String Id;
    private String TenRap;
    private String DiaChi;
    private String SDT;

    public Rap() {} // bắt buộc

    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }

    public String getTenRap() { return TenRap; }
    public String getDiaChi() { return DiaChi; }
    public String getSDT() { return SDT; }
}
