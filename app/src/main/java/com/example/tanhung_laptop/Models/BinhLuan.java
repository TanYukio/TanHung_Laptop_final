package com.example.tanhung_laptop.Models;

public class BinhLuan {
    Integer IDTAIKHOAN;
    String HINHANH;
    String THOIGIAN;
    String NOIDUNG;

    public BinhLuan(Integer IDTAIKHOAN, String HINHANH, String THOIGIAN, String NOIDUNG) {
        this.IDTAIKHOAN = IDTAIKHOAN;
        this.HINHANH = HINHANH;
        this.THOIGIAN = THOIGIAN;
        this.NOIDUNG = NOIDUNG;
    }

    public Integer getIDTAIKHOAN() {
        return IDTAIKHOAN;
    }

    public void setIDTAIKHOAN(Integer IDTAIKHOAN) {
        this.IDTAIKHOAN = IDTAIKHOAN;
    }

    public String getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(String HINHANH) {
        this.HINHANH = HINHANH;
    }

    public String getTHOIGIAN() {
        return THOIGIAN;
    }

    public void setTHOIGIAN(String THOIGIAN) {
        this.THOIGIAN = THOIGIAN;
    }

    public String getNOIDUNG() {
        return NOIDUNG;
    }

    public void setNOIDUNG(String NOIDUNG) {
        this.NOIDUNG = NOIDUNG;
    }
}
