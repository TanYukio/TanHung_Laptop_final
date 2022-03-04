package com.example.tanhung_laptop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tanhung_laptop.Models.TAIKHOAN;

public class DangKy_Activity extends AppCompatActivity {
    EditText edtTaikhoan,edtMatkhau,edtnhaplai_matkhau,edtsdt,edtemail;
    Button btnDangky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        anh_xa();
        btnDangky.setOnClickListener(new View.OnClickListener() {
            String matkhau = edtMatkhau.getText().toString().trim();
            String nlmatkhau = edtnhaplai_matkhau.getText().toString().trim();
            @Override
            public void onClick(View view) {
                if (edtTaikhoan.getText().toString() == null || edtTaikhoan.getText().toString().equals("")){
                    Toast.makeText(DangKy_Activity.this, "Vui lòng nhập tài khoản !", Toast.LENGTH_LONG).show();
                } else if (edtMatkhau.getText().toString() == null || edtMatkhau.getText().toString().equals("")) {
                    Toast.makeText(DangKy_Activity.this, "Vui lòng nhập mật khẩu !", Toast.LENGTH_SHORT).show();
                } else if (String.valueOf(edtsdt.getText().toString()).length() != 10) {
                    Toast.makeText(DangKy_Activity.this, "Số điện thoại không hợp lệ !", Toast.LENGTH_SHORT).show();
                }
                else if (isEmailValid(edtemail.getText().toString())) {
                    Toast.makeText(DangKy_Activity.this, "email không hợp lệ ", Toast.LENGTH_SHORT).show();
                }else {
                    TAIKHOAN taiKhoan = new TAIKHOAN();
                    taiKhoan.setTENTAIKHOAN(edtTaikhoan.getText().toString());
                    taiKhoan.setMATKHAU(edtMatkhau.getText().toString());
                    taiKhoan.setSDT(Integer.valueOf(edtsdt.getText().toString()));
                    taiKhoan.setEMAIL(edtemail.getText().toString());

                    long kiemtra = BatDau_activity.database.themtaikhoan(taiKhoan);
                    if (kiemtra != 0){
                        Toast.makeText(DangKy_Activity.this, "Thêm thành công !", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DangKy_Activity.this, DangNhap_Activity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DangKy_Activity.this, "Thêm thất bại !", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }

    private void anh_xa() {
        edtTaikhoan = findViewById(R.id.edtTaikhoan);
        edtMatkhau = findViewById(R.id.edtMatkhau);
        edtnhaplai_matkhau = findViewById(R.id.edtnhaplai_matkhau);
        edtsdt = findViewById(R.id.edtsdt);
        edtemail=findViewById(R.id.edtemail);
        btnDangky = findViewById(R.id.btnDangky);
    }
    public boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}