package com.example.tanhung_laptop.ModelsPHP;

public class LapTop {
    int IDLT;
    String TENLAPTOP;
    int GIA;
    int SOLUONG;
    String MOTASP;
    int IDNSX;
    int LTMOI;
    String hinh;

    public LapTop(int IDLT, String hinh, String TENLAPTOP, int GIA, int SOLUONG, String MOTASP, int IDNSX, int LTMOI) {
        this.IDLT = IDLT;
        this.hinh = hinh;
        this.TENLAPTOP = TENLAPTOP;
        this.GIA = GIA;
        this.SOLUONG = SOLUONG;
        this.MOTASP = MOTASP;
        this.IDNSX = IDNSX;
        this.LTMOI = LTMOI;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
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
