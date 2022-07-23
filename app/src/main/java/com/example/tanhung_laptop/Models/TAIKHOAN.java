package com.example.tanhung_laptop.Models;

public class TAIKHOAN {
    int IDTAIKHOAN;
    String TENTAIKHOAN;
    String MATKHAU;
    int SDT;
    String EMAIL;
    String NGAYSINH;
    int QUYENTK;
    String DIACHI;
    String HINHANH;

    public TAIKHOAN(int IDTAIKHOAN, String TENTAIKHOAN, String MATKHAU, int SDT, String EMAIL, String NGAYSINH, int QUYENTK, String DIACHI, String HINHANH) {
        this.IDTAIKHOAN = IDTAIKHOAN;
        this.TENTAIKHOAN = TENTAIKHOAN;
        this.MATKHAU = MATKHAU;
        this.SDT = SDT;
        this.EMAIL = EMAIL;
        this.NGAYSINH = NGAYSINH;
        this.QUYENTK = QUYENTK;
        this.DIACHI = DIACHI;
        this.HINHANH = HINHANH;
    }

    public TAIKHOAN( String TENTAIKHOAN, int IDTAIKHOAN) {
        this.TENTAIKHOAN = TENTAIKHOAN;
        this.IDTAIKHOAN = IDTAIKHOAN;

    }

    public String getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(String HINHANH) {
        this.HINHANH = HINHANH;
    }

    public TAIKHOAN() {
        IDTAIKHOAN = -1;
    }


    public int getIDTAIKHOAN() {
        return IDTAIKHOAN;
    }

    public void setIDTAIKHOAN(int IDTAIKHOAN) {
        this.IDTAIKHOAN = IDTAIKHOAN;
    }

    public String getTENTAIKHOAN() {
        return TENTAIKHOAN;
    }

    public void setTENTAIKHOAN(String TENTAIKHOAN) {
        this.TENTAIKHOAN = TENTAIKHOAN;
    }

    public String getMATKHAU() {
        return MATKHAU;
    }

    public void setMATKHAU(String MATKHAU) {
        this.MATKHAU = MATKHAU;
    }

    public int getSDT() {
        return SDT;
    }

    public void setSDT(int SDT) {
        this.SDT = SDT;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getNGAYSINH() {
        return NGAYSINH;
    }

    public void setNGAYSINH(String NGAYSINH) {
        this.NGAYSINH = NGAYSINH;
    }

    public int getQUYENTK() {
        return QUYENTK;
    }

    public void setQUYENTK(int QUYENTK) {
        this.QUYENTK = QUYENTK;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }
}
