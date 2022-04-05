package com.example.tanhung_laptop.Models;

public class GopY{
    int IDGOPY;
    String TENTAIKHOAN;
    int SDT;
    String NOIDUNG;

    public GopY(int IDGOPY, String TENTAIKHOAN, int SDT, String NOIDUNG) {
        this.IDGOPY = IDGOPY;
        this.TENTAIKHOAN = TENTAIKHOAN;
        this.SDT = SDT;
        this.NOIDUNG = NOIDUNG;
    }

    public int getIDGOPY() {
        return IDGOPY;
    }

    public void setIDGOPY(int IDGOPY) {
        this.IDGOPY = IDGOPY;
    }

    public String getTENTAIKHOAN() {
        return TENTAIKHOAN;
    }

    public void setTENTAIKHOAN(String TENTAIKHOAN) {
        this.TENTAIKHOAN = TENTAIKHOAN;
    }

    public int getSDT() {
        return SDT;
    }

    public void setSDT(int SDT) {
        this.SDT = SDT;
    }

    public String getNOIDUNG() {
        return NOIDUNG;
    }

    public void setNOIDUNG(String NOIDUNG) {
        this.NOIDUNG = NOIDUNG;
    }
}
