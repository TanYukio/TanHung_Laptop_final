package com.example.tanhung_laptop.ModelsPHP;

import com.example.tanhung_laptop.Models.LAPTOP;

import java.util.ArrayList;
import java.util.List;

public class LaptopModel {
    boolean success;
    String message;
    List<LAPTOP> result = new ArrayList<>();

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

    public List<LAPTOP> getResult() {
        return result;
    }

    public void setResult(List<LAPTOP> result) {
        this.result = result;
    }
}
