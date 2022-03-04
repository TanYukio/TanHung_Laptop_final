package com.example.tanhung_laptop.Models;

public class LAPTOP {
    int IDLT;
    byte[] HINHANH;
    String TENLAPTOP;
    int GIA;
    int SOLUONG;
    String MOTASP;
    int IDNSX;
    int LTMOI;

    public LAPTOP(int IDLT, byte[] HINHANH, String TENLAPTOP, int GIA, int SOLUONG, String MOTASP, int IDNSX, int LTMOI) {
        this.IDLT = IDLT;
        this.HINHANH = HINHANH;
        this.TENLAPTOP = TENLAPTOP;
        this.GIA = GIA;
        this.SOLUONG = SOLUONG;
        this.MOTASP = MOTASP;
        this.IDNSX = IDNSX;
        this.LTMOI = LTMOI;
    }

    public int getIDLT() {
        return IDLT;
    }

    public void setIDLT(int IDLT) {
        this.IDLT = IDLT;
    }

    public byte[] getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(byte[] HINHANH) {
        this.HINHANH = HINHANH;
    }

    public String getTENLAPTOP() {
        return TENLAPTOP;
    }

    public void setTENLAPTOP(String TENLAPTOP) {
        this.TENLAPTOP = TENLAPTOP;
    }

    public int getGIA() {
        return GIA;
    }

    public void setGIA(int GIA) {
        this.GIA = GIA;
    }

    public int getSOLUONG() {
        return SOLUONG;
    }

    public void setSOLUONG(int SOLUONG) {
        this.SOLUONG = SOLUONG;
    }

    public String getMOTASP() {
        return MOTASP;
    }

    public void setMOTASP(String MOTASP) {
        this.MOTASP = MOTASP;
    }

    public int getIDNSX() {
        return IDNSX;
    }

    public void setIDNSX(int IDNSX) {
        this.IDNSX = IDNSX;
    }

    public int getLTMOI() {
        return LTMOI;
    }

    public void setLTMOI(int LTMOI) {
        this.LTMOI = LTMOI;
    }
}
