package com.example.tanhung_laptop.Models;

public class CTHoaDon {
    int IDHOADON, IDLT;
    String TENLAPTOP;
    int SOLUONG, THANHTIEN;

    public CTHoaDon(int IDHOADON, int IDLT, String TENLAPTOP, int SOLUONG, int THANHTIEN) {
        this.IDHOADON = IDHOADON;
        this.IDLT = IDLT;
        this.TENLAPTOP = TENLAPTOP;
        this.SOLUONG = SOLUONG;
        this.THANHTIEN = THANHTIEN;
    }

    public int getIDHOADON() {
        return IDHOADON;
    }

    public void setIDHOADON(int IDHOADON) {
        this.IDHOADON = IDHOADON;
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

    public int getTHANHTIEN() {
        return THANHTIEN;
    }

    public void setTHANHTIEN(int THANHTIEN) {
        this.THANHTIEN = THANHTIEN;
    }
}
