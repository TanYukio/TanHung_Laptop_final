package com.example.tanhung_laptop.ModelsPHP;

import com.example.tanhung_laptop.Models.BinhLuan;
import com.example.tanhung_laptop.Models.Category;
import com.example.tanhung_laptop.Models.DSTK;
import com.example.tanhung_laptop.Models.TAIKHOAN;

import java.util.List;

public class TheLoaiModel {
    boolean success;
    String message;
    List<DSTK> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DSTK> getResult() {
        return result;
    }

    public void setResult(List<DSTK> result) {
        this.result = result;
    }
}
