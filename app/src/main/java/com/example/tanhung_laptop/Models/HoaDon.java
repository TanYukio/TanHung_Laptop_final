package com.example.tanhung_laptop.Models;

public class HoaDon {
    int IDHOADON,IDTAIKHOAN,TONGTIEN;
    String GHICHU, DIACHI;

    public HoaDon(int IDHOADON, int IDTAIKHOAN, int TONGTIEN, String GHICHU, String DIACHI) {
        this.IDHOADON = IDHOADON;
        this.IDTAIKHOAN = IDTAIKHOAN;
        this.TONGTIEN = TONGTIEN;
        this.GHICHU = GHICHU;
        this.DIACHI = DIACHI;
    }

    public int getIDHOADON() {
        return IDHOADON;
    }

    public void setIDHOADON(int IDHOADON) {
        this.IDHOADON = IDHOADON;
    }

    public int getIDTAIKHOAN() {
        return IDTAIKHOAN;
    }

    public void setIDTAIKHOAN(int IDTAIKHOAN) {
        this.IDTAIKHOAN = IDTAIKHOAN;
    }

    public int getTONGTIEN() {
        return TONGTIEN;
    }

    public void setTONGTIEN(int TONGTIEN) {
        this.TONGTIEN = TONGTIEN;
    }

    public String getGHICHU() {
        return GHICHU;
    }

    public void setGHICHU(String GHICHU) {
        this.GHICHU = GHICHU;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }
}
