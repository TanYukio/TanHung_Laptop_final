package com.example.tanhung_laptop.Models;

public class GioHang {
    int IDGIOHANG;
    int IDSP;
    String TENSP;
    int IDTK;
    int SOLUONG;
    int TONGTIEN;
    byte[] HINHANH;

    public int getIDGIOHANG() {
        return IDGIOHANG;
    }

    public void setIDGIOHANG(int IDGIOHANG) {
        this.IDGIOHANG = IDGIOHANG;
    }

    public int getIDSP() {
        return IDSP;
    }

    public void setIDSP(int IDSP) {
        this.IDSP = IDSP;
    }

    public String getTENSP() {
        return TENSP;
    }

    public void setTENSP(String TENSP) {
        this.TENSP = TENSP;
    }

    public int getIDTK() {
        return IDTK;
    }

    public void setIDTK(int IDTK) {
        this.IDTK = IDTK;
    }

    public int getSOLUONG() {
        return SOLUONG;
    }

    public void setSOLUONG(int SOLUONG) {
        this.SOLUONG = SOLUONG;
    }

    public int getTONGTIEN() {
        return TONGTIEN;
    }

    public void setTONGTIEN(int TONGTIEN) {
        this.TONGTIEN = TONGTIEN;
    }

    public byte[] getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(byte[] HINHANH) {
        this.HINHANH = HINHANH;
    }

    public GioHang(int IDGIOHANG, int IDSP, String TENSP, int IDTK, int SOLUONG, int TONGTIEN, byte[] HINHANH) {
        this.IDGIOHANG = IDGIOHANG;
        this.IDSP = IDSP;
        this.TENSP = TENSP;
        this.IDTK = IDTK;
        this.SOLUONG = SOLUONG;
        this.TONGTIEN = TONGTIEN;
        this.HINHANH = HINHANH;
    }
}
