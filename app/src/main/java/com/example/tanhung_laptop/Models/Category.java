package com.example.tanhung_laptop.Models;

public class Category {
    private String name;
    private int IDcategory;


    public Category(String name, int IDcategory) {
        this.name = name;
        this.IDcategory = IDcategory;
    }

    public int getIDcategory() {
        return IDcategory;
    }

    public void setIDcategory(int IDcategory) {
        this.IDcategory = IDcategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
