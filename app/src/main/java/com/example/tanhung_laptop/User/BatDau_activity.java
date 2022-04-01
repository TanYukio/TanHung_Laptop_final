package com.example.tanhung_laptop.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.tanhung_laptop.Data.Database;
import com.example.tanhung_laptop.R;

public class BatDau_activity extends AppCompatActivity {

    public static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bat_dau);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(BatDau_activity.this, MainActivity.class));
                finish();
            }
        },2000);
        database = new Database(this,"LAPTOPTANHUNG",null,1);

//        database.QueryData("CREATE TABLE IF NOT EXISTS LAPTOP(Id INTEGER PRIMARY KEY AUTOINCREMENT" +
//        ", TenSP VARCHAR(150), MOTASP  VARCHAR(250), HinhAnh BLOB)");



    }
}