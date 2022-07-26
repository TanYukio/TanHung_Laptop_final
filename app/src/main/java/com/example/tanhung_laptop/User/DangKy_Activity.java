package com.example.tanhung_laptop.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tanhung_laptop.Models.TAIKHOAN;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;

import java.io.ByteArrayOutputStream;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangKy_Activity extends AppCompatActivity {
    EditText edtTaikhoan,edtMatkhau,edtnhaplai_matkhau,edtsdt,edtemail;
    Button btnDangky;
    ImageView img_quaylai_dangky;
    API api;
    CompositeDisposable compositeDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        anh_xa();
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();

        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTaikhoan.getText().toString() == null || edtTaikhoan.getText().toString().equals("")){
                    Toast.makeText(DangKy_Activity.this, "Vui lòng nhập thông tin tài khoản !", Toast.LENGTH_LONG).show();
                } else if (edtMatkhau.getText().toString() == null || edtMatkhau.getText().toString().equals("")) {
                    Toast.makeText(DangKy_Activity.this, "Vui lòng nhập mật khẩu !", Toast.LENGTH_SHORT).show();
                } else if ( !edtMatkhau.getText().toString().equals( edtnhaplai_matkhau.getText().toString()) ) {
                    Toast.makeText(DangKy_Activity.this, "Nhập lại mật khẩu không trùng với mật khẩu !", Toast.LENGTH_SHORT).show();
                } else if (String.valueOf(edtsdt.getText().toString()).length() != 10) {
                    Toast.makeText(DangKy_Activity.this, "Số điện thoại không hợp lệ !", Toast.LENGTH_SHORT).show();
                }

                else if (isEmailValid(edtemail.getText().toString())== false) {
                    Toast.makeText(DangKy_Activity.this, "email không hợp lệ ", Toast.LENGTH_SHORT).show();
                }
                else {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable(R.drawable.person);
                    Bitmap bitmap = bitmapDrawable.getBitmap();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
                    byte[] b = baos.toByteArray();

                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


                    String tentaikhoan = edtTaikhoan.getText().toString();
                    String matkhau = edtMatkhau.getText().toString();
                    String sdt = edtsdt.getText().toString();
                    String email = edtemail.getText().toString();

                    compositeDisposable.add(api.dangKi(tentaikhoan, matkhau, sdt, email, 1,encodedImage)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        if (messageModel.isSuccess())
                                        {
                                            Intent intent = new Intent(DangKy_Activity.this, DangNhap_Activity.class);
                                            startActivity(intent);
                                        }
                                        // Đều xuất thông báo khi thành công lẫn thất bại
                                        Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                    }, throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }));
                }
            }
        });


    }

    private void anh_xa() {
        img_quaylai_dangky = findViewById(R.id.img_quaylai_dangky);
        img_quaylai_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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