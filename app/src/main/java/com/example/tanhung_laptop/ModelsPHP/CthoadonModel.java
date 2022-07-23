package com.example.tanhung_laptop.ModelsPHP;

import com.example.tanhung_laptop.Models.CTHoaDon;
import com.example.tanhung_laptop.Models.HoaDon;

import java.util.List;

public class CthoadonModel {
    boolean success;
    String message;
    List<CTHoaDon> result;

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

    public List<CTHoaDon> getResult() {
        return result;
    }

    public void setResult(List<CTHoaDon> result) {
        this.result = result;
    }
}
