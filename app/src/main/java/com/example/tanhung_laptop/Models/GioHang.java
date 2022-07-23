package com.example.tanhung_laptop.Models;

public class GioHang {
    int IDTK;
    int IDLT;
    String TENLAPTOP;
    int SOLUONG;
    int TONGTIEN;
    String HINHANH;

    public GioHang(int IDTK, int IDLT, String TENLAPTOP, int SOLUONG, int TONGTIEN, String HINHANH) {
        this.IDTK = IDTK;
        this.IDLT = IDLT;
        this.TENLAPTOP = TENLAPTOP;
        this.SOLUONG = SOLUONG;
        this.TONGTIEN = TONGTIEN;
        this.HINHANH = HINHANH;
    }

    public int getIDTK() {
        return IDTK;
    }

    public void setIDTK(int IDTK) {
        this.IDTK = IDTK;
    }

    public int getIDLT() {
        return IDLT;
    }

    public void setIDLT(int IDLT) {
        this.IDLT = IDLT;
    }

    public String getTENLAPTOP() {
        return TENLAPTOP;
    }

    public void setTENLAPTOP(String TENLAPTOP) {
        this.TENLAPTOP = TENLAPTOP;
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

    public String getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(String HINHANH) {
        this.HINHANH = HINHANH;
    }
}
