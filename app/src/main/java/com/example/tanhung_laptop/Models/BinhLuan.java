package com.example.tanhung_laptop.Models;

public class BinhLuan {
    Integer TaiKhoanBL;
    byte[] HinhBL;
    String NoidungBL;
    String ThoiGianBL;

    public BinhLuan() {
    }

    public BinhLuan(Integer taiKhoanBL, byte[] hinhBL, String noidungBL, String thoiGianBL) {
        TaiKhoanBL = taiKhoanBL;
        HinhBL = hinhBL;
        NoidungBL = noidungBL;
        ThoiGianBL = thoiGianBL;
    }

    public Integer getTaiKhoanBL() {
        return TaiKhoanBL;
    }

    public void setTaiKhoanBL(Integer taiKhoanBL) {
        TaiKhoanBL = taiKhoanBL;
    }

    public byte[] getHinhBL() {
        return HinhBL;
    }

    public void setHinhBL(byte[] hinhBL) {
        HinhBL = hinhBL;
    }

    public String getNoidungBL() {
        return NoidungBL;
    }

    public void setNoidungBL(String noidungBL) {
        NoidungBL = noidungBL;
    }

    public String getThoiGianBL() {
        return ThoiGianBL;
    }

    public void setThoiGianBL(String thoiGianBL) {
        ThoiGianBL = thoiGianBL;
    }
}
