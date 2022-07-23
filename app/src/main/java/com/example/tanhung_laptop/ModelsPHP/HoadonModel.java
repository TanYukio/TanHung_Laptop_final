package com.example.tanhung_laptop.ModelsPHP;

import com.example.tanhung_laptop.Models.HoaDon;
import com.example.tanhung_laptop.Models.LAPTOP;

import java.util.List;

public class HoadonModel {
    boolean success;
    String message;
    List<HoaDon> result;

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

    public List<HoaDon> getResult() {
        return result;
    }

    public void setResult(List<HoaDon> result) {
        this.result = result;
    }
}
