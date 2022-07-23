package com.example.tanhung_laptop.Models;

public class DSTK {
    private String TENTAIKHOAN;
    private int IDTAIKHOAN;

    public DSTK(String TENTAIKHOAN, int IDTAIKHOAN) {
        this.TENTAIKHOAN = TENTAIKHOAN;
        this.IDTAIKHOAN = IDTAIKHOAN;
    }

    public String getTENTAIKHOAN() {
        return TENTAIKHOAN;
    }

    public void setTENTAIKHOAN(String TENTAIKHOAN) {
        this.TENTAIKHOAN = TENTAIKHOAN;
    }

    public int getIDTAIKHOAN() {
        return IDTAIKHOAN;
    }

    public void setIDTAIKHOAN(int IDTAIKHOAN) {
        this.IDTAIKHOAN = IDTAIKHOAN;
    }
}
