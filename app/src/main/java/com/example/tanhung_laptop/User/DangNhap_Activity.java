package com.example.tanhung_laptop.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanhung_laptop.HomeAdmin_Activity;
import com.example.tanhung_laptop.Models.TAIKHOAN;
import com.example.tanhung_laptop.R;

public class DangNhap_Activity extends AppCompatActivity {
    TextView txtdangky;
    Button btnDangnhap_login;
    CheckBox cb_luumatkhau_dangnhap;
    EditText edt_Matkhau_dangnhap,edt_Taikhoan_Dangnhap;
    ImageView img_back;
    public static TAIKHOAN taikhoan = new TAIKHOAN() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anh_xa();


        btnDangnhap_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(BatDau_activity.database.tontaitaikhoan(edt_Taikhoan_Dangnhap.getText().toString(),edt_Matkhau_dangnhap.getText().toString())){
                        Toast.makeText(DangNhap_Activity.this, " Lỗi đăng nhập", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        taikhoan = Laythongtintaikhoan();

                        if (taikhoan.getQUYENTK()==1){
                            Intent intent = new Intent(DangNhap_Activity.this, MainActivity.class);
                            intent.putExtra("idtk",taikhoan.getIDTAIKHOAN());
                            startActivity(intent);
                            Toast.makeText(DangNhap_Activity.this, "Ten nguoi dung: " + taikhoan.getTENTAIKHOAN(), Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            startActivity(new Intent(DangNhap_Activity.this, HomeAdmin_Activity.class));
                        }

                    }
                }catch (Exception e){
                    Toast.makeText(DangNhap_Activity.this, " Vui lòng nhập thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private TAIKHOAN Laythongtintaikhoan() {
        Cursor cursor = BatDau_activity.database.GetData("SELECT * FROM TAIKHOAN WHERE TENTAIKHOAN = '" +
                edt_Taikhoan_Dangnhap.getText().toString() +
                "' AND MATKHAU =" + edt_Matkhau_dangnhap.getText().toString());
        while (cursor.moveToNext()) {
            return new TAIKHOAN(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getString(7),
                    cursor.getBlob(8)
            );
        }
        return null;
    }

    private void anh_xa() {
        txtdangky = findViewById(R.id.txtdangky);
        txtdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangNhap_Activity.this,DangKy_Activity.class));
            }
        });
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangNhap_Activity.this,MainActivity.class));
            }
        });
        edt_Taikhoan_Dangnhap = findViewById(R.id.edt_Taikhoan_Dangnhap);
        edt_Matkhau_dangnhap = findViewById(R.id.edt_Matkhau_dangnhap);
        cb_luumatkhau_dangnhap = findViewById(R.id.cb_luumatkhau_dangnhap);
        btnDangnhap_login = findViewById(R.id.btnDangnhap_login);
    }
}