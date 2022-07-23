package com.example.tanhung_laptop.ModelsPHP;

import com.example.tanhung_laptop.Models.BinhLuan;

import java.util.List;

public class BinhluanModel {
    boolean success;
    String message;
    List<BinhLuan> result;

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

    public List<BinhLuan> getResult() {
        return result;
    }

    public void setResult(List<BinhLuan> result) {
        this.result = result;
    }
}
