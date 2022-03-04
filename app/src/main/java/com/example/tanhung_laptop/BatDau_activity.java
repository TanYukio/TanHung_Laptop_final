package com.example.tanhung_laptop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.tanhung_laptop.Data.Database;
import com.example.tanhung_laptop.Models.TAIKHOAN;

public class BatDau_activity extends AppCompatActivity {

    public static Database database;
    public static TAIKHOAN taikhoan = new TAIKHOAN() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bat_dau);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(BatDau_activity.this,MainActivity.class));
            }
        },2000);
        database = new Database(this,"LAPTOPTANHUNG",null,1);

//        database.QueryData("CREATE TABLE IF NOT EXISTS LAPTOP(Id INTEGER PRIMARY KEY AUTOINCREMENT" +
//        ", TenSP VARCHAR(150), MOTASP  VARCHAR(250), HinhAnh BLOB)");



    }
}