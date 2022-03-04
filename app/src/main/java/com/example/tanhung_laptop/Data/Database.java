package com.example.tanhung_laptop.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tanhung_laptop.Models.TAIKHOAN;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    public boolean tontaitaikhoan(String tk, String mk){
        Cursor cursor = GetData("SELECT * FROM TAIKHOAN WHERE TENTAIKHOAN = '" + tk +"' AND MATKHAU =" + mk);
        while (cursor.moveToNext()){
            return false;
        }
        return true;
    }
    public long themtaikhoan(TAIKHOAN taikhoan){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENTAIKHOAN", taikhoan.getTENTAIKHOAN());
        contentValues.put("MATKHAU", taikhoan.getMATKHAU());
        contentValues.put("SDT", taikhoan.getSDT());
        contentValues.put("EMAIL", taikhoan.getSDT());
        contentValues.put("QUYENTK",1);
        long kiemtra = database.insert("TAIKHOAN", null, contentValues);
        return kiemtra;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
