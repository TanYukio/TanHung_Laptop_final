package com.example.tanhung_laptop.ModelsPHP;

import com.example.tanhung_laptop.Models.GopY;

import java.util.List;

public class GopyModel {
    boolean success;
    String message;
    List<GopY> result;

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

    public List<GopY> getResult() {
        return result;
    }

    public void setResult(List<GopY> result) {
        this.result = result;
    }
}
