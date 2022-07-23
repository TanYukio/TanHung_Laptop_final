package com.example.tanhung_laptop.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.TaiKhoanAdminAdapter;
import com.example.tanhung_laptop.Models.TAIKHOAN;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;
import com.example.tanhung_laptop.User.BatDau_activity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SuaTaiKhoan_Activity extends AppCompatActivity {
    EditText edt_TenTaiKhoan_QLTK,edt_MatKhau_QLTK,edt_SDT_QLTK,edt_Email_QLTK,edt_NgaySinh_QLTK,
            edt_LoaiTK_QLTK,edt_DiaChi_QLTK;
    ImageView imageViewHinh_QLTK,quaylai_qltk;
    Button buttonAdd_QLTK,buttonHuy_QLTK,chonngay;
    ImageButton imageButtonCamera_QLTK,imageButtonFolder_QLTK;
    TAIKHOAN taiKhoan;
    final int REQUEST_CODE_CAMERA=123;
    final int REQUEST_CODE_FOLDER=456;
    int id,MATK;
    DatePickerDialog.OnDateSetListener setListener;
    CompositeDisposable compositeDisposable;
    API api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_tai_khoan);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",1123);
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable = new CompositeDisposable();

        Anhxa();
        Getdata();
        quaylai_qltk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        buttonAdd_QLTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chuyen data image view -> mang byte[]
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageViewHinh_QLTK.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
                byte[] b = baos.toByteArray();

                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                compositeDisposable.add(api.doitk(MATK,
                                        edt_TenTaiKhoan_QLTK.getText().toString().trim(),
                                        edt_MatKhau_QLTK.getText().toString().trim(),
                                        edt_SDT_QLTK.getText().toString().trim(),
                                        edt_Email_QLTK.getText().toString().trim(),
                                        edt_NgaySinh_QLTK.getText().toString().trim(),
                                        Integer.parseInt(edt_LoaiTK_QLTK.getText().toString().trim()),
                                        edt_DiaChi_QLTK.getText().toString().trim(),
                                encodedImage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                messageModel -> {
                                    Toast.makeText(SuaTaiKhoan_Activity.this, messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                , throwable -> {
                                    Toast.makeText(SuaTaiKhoan_Activity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        )
                );
//                BatDau_activity.database.UPDATE_TAIKHOAN(
//                        MATK,
//                        edt_TenTaiKhoan_QLTK.getText().toString().trim(),
//                        edt_MatKhau_QLTK.getText().toString().trim(),
//                        Integer.parseInt(edt_SDT_QLTK.getText().toString().trim()) ,
//                        edt_Email_QLTK.getText().toString().trim(),
//                        edt_NgaySinh_QLTK.getText().toString().trim(),
//                        Integer.parseInt(edt_LoaiTK_QLTK.getText().toString().trim()),
//                        edt_DiaChi_QLTK.getText().toString().trim(),
//                        hinhAnh
//                );
//
//                Toast.makeText(SuaTaiKhoan_Activity.this," Sửa thành công ",Toast.LENGTH_LONG).show();
                onBackPressed();

            }
        });

        imageButtonCamera_QLTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        SuaTaiKhoan_Activity.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA
                );

            }
        });

        imageButtonFolder_QLTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        SuaTaiKhoan_Activity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOLDER
                );
            }
        });

        buttonHuy_QLTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Calendar today = Calendar.getInstance();
        final int year = today.get(Calendar.YEAR);
        final int month = today.get(Calendar.MONTH);
        final int day = today.get(Calendar.DAY_OF_MONTH);
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 += 1;
                String date = i + "-" + i1 + "-" + i2;
                edt_NgaySinh_QLTK.setText(date);
            }
        };

        chonngay.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    android.R.style.Theme_Holo_Dialog_MinWidth, setListener, year, month, day);

            datePickerDialog.getDatePicker().setMaxDate(today.getTime().getTime());
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_CAMERA:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CODE_CAMERA);
                }else
                {
                    Toast.makeText(SuaTaiKhoan_Activity.this," Bạn không được phép mở camera", Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_CODE_FOLDER:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,REQUEST_CODE_FOLDER);
                }else
                {
                    Toast.makeText(SuaTaiKhoan_Activity.this," Bạn không được phép mở folder", Toast.LENGTH_LONG).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageViewHinh_QLTK.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewHinh_QLTK.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void Getdata() {
        taiKhoan = TaiKhoanAdminAdapter.taiKhoanList.get(id);
        MATK = taiKhoan.getIDTAIKHOAN();
        edt_TenTaiKhoan_QLTK.setText(taiKhoan.getTENTAIKHOAN());
        edt_MatKhau_QLTK.setText(taiKhoan.getMATKHAU());
        edt_SDT_QLTK.setText(String.valueOf(taiKhoan.getSDT()));
        edt_Email_QLTK.setText(taiKhoan.getEMAIL());
        edt_NgaySinh_QLTK.setText(taiKhoan.getNGAYSINH());
        edt_NgaySinh_QLTK.setEnabled(false);
        edt_LoaiTK_QLTK.setText(String.valueOf(taiKhoan.getQUYENTK()));
        edt_DiaChi_QLTK.setText(taiKhoan.getDIACHI());

        if(taiKhoan.getHINHANH() == null)
        {
            imageViewHinh_QLTK.setImageResource(R.drawable.user);
        }
        else
        {
//            byte[] hinhAnh = taiKhoan.getHINHANH();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
//            imageViewHinh_QLTK.setImageBitmap(bitmap);
            byte[] decodedString = Base64.decode(taiKhoan.getHINHANH(), Base64.DEFAULT);
            Bitmap imgBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageViewHinh_QLTK.setImageBitmap(imgBitMap);
        }

    }
    private void Anhxa() {
        chonngay = findViewById(R.id.chonngay);
        imageButtonCamera_QLTK = findViewById(R.id.imageButtonCamera_QLTK);
        quaylai_qltk = findViewById(R.id.quaylai_qltk);
        imageButtonFolder_QLTK = findViewById(R.id.imageButtonFolder_QLTK);
        edt_TenTaiKhoan_QLTK = findViewById(R.id.edt_TenTaiKhoan_QLTK);
        edt_MatKhau_QLTK = findViewById(R.id.edt_MatKhau_QLTK);
        edt_SDT_QLTK = findViewById(R.id.edt_SDT_QLTK);
        edt_Email_QLTK = findViewById(R.id.edt_Email_QLTK);
        edt_NgaySinh_QLTK = findViewById(R.id.edt_NgaySinh_QLTK);
        edt_LoaiTK_QLTK = findViewById(R.id.edt_LoaiTK_QLTK);
        edt_DiaChi_QLTK = findViewById(R.id.edt_DiaChi_QLTK);
        imageViewHinh_QLTK = findViewById(R.id.imageViewHinh_QLTK);
        buttonAdd_QLTK = findViewById(R.id.buttonAdd_QLTK);
        buttonHuy_QLTK = findViewById(R.id.buttonHuy_QLTK);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}