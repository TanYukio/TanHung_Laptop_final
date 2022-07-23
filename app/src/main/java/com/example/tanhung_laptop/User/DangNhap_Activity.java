package com.example.tanhung_laptop.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanhung_laptop.Admin.HomeAdmin_Activity;
import com.example.tanhung_laptop.Models.TAIKHOAN;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhap_Activity extends AppCompatActivity {
    TextView txtdangky;
    Button btnDangnhap_login;
    CheckBox cb_luumatkhau_dangnhap;
    EditText edt_Matkhau_dangnhap,edt_Taikhoan_Dangnhap;
    ImageView img_back;
    CompositeDisposable compositeDisposable;
    API api;
    public static TAIKHOAN taikhoan = new TAIKHOAN() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anh_xa();
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();

        btnDangnhap_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    compositeDisposable.add(api.dangNhap(edt_Taikhoan_Dangnhap.getText().toString(), edt_Matkhau_dangnhap.getText().toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    taikhoanModel -> {
                                        if (taikhoanModel.isSuccess()) {
                                                taikhoan = taikhoanModel.getResult().get(0);
                                                if (taikhoan.getQUYENTK()==1){
                                                    Intent intent = new Intent(DangNhap_Activity.this, MainActivity.class);
                                                    intent.putExtra("idtk",taikhoan.getIDTAIKHOAN());
                                                    startActivity(intent);
                                                }
                                                else
                                                {
                                                    startActivity(new Intent(DangNhap_Activity.this, HomeAdmin_Activity.class));
                                                }
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(), "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_LONG).show();

                                        }
                                        Toast.makeText(getApplicationContext(), taikhoanModel.getMessage(), Toast.LENGTH_LONG).show();
                                    }, throwable -> {
                                        Toast.makeText(getApplicationContext()," Lỗi:" +  throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }));
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

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}