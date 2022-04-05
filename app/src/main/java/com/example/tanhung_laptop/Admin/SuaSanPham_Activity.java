package com.example.tanhung_laptop.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.LaptopAdminAdapter;
import com.example.tanhung_laptop.Models.LAPTOP;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.User.BatDau_activity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SuaSanPham_Activity extends AppCompatActivity {
    Button btnAdd,btnCancel;
    EditText editTen, edtDanhMuc,edtSoLuong, edt_GiaSP,edtSPmoi;
    ImageButton ibtnCamera,ibtnFolder;
    ImageView imgHinh,quaylai_QLSP;
    final int REQUEST_CODE_CAMERA=123;
    final int REQUEST_CODE_FOLDER=456;
    LAPTOP laptop;
    int id,MASP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_san_pham);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",1123);

        Anhxa();
        Getdata();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chuyen data image view -> mang byte[]
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                byte[] hinhAnh = byteArray.toByteArray();

                BatDau_activity.database.UPDATE_DOAN(
                        editTen.getText().toString().trim(),
                        hinhAnh,
                        Integer.parseInt(edtSoLuong.getText().toString().trim()),
                        Integer.parseInt(edt_GiaSP.getText().toString().trim()),
                        Integer.parseInt(edtDanhMuc.getText().toString().trim()),
                        Integer.parseInt(edtSPmoi.getText().toString().trim())
                        ,MASP

                );

                Toast.makeText(SuaSanPham_Activity.this," Sửa thành công",Toast.LENGTH_LONG).show();
                startActivity(new Intent(SuaSanPham_Activity.this, HomeAdmin_Activity.class));

            }
        });

        ibtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        SuaSanPham_Activity.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA
                );

            }
        });

        ibtnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        SuaSanPham_Activity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOLDER
                );
            }
        });
        quaylai_QLSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void Getdata() {
        laptop = LaptopAdminAdapter.laptopList.get(id);
        MASP = laptop.getIDLT();
        editTen.setText(laptop.getTENLAPTOP());
        edt_GiaSP.setText(String.valueOf(laptop.getGIA()));
        edtDanhMuc.setText(String.valueOf(laptop.getIDNSX()));
        edtSoLuong.setText(String.valueOf(laptop.getSOLUONG()));
        edtSPmoi.setText(String.valueOf(laptop.getLTMOI()));
        byte[] hinhAnh = laptop.getHINHANH();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
        imgHinh.setImageBitmap(bitmap);
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
                    Toast.makeText(SuaSanPham_Activity.this," Bạn không cho phép mở camera", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(SuaSanPham_Activity.this," Bạn không cho phép mở Folder", Toast.LENGTH_LONG).show();
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
            imgHinh.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgHinh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Anhxa() {
        quaylai_QLSP = findViewById(R.id.quaylai_QLSP);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnCancel = (Button) findViewById(R.id.buttonHuy_QlSP);
        editTen =  (EditText) findViewById(R.id.edt_TenSP_QLSP);
        edtDanhMuc = (EditText) findViewById(R.id.edt_IDDanhMuc_QLSP);
        edtSoLuong = (EditText) findViewById(R.id.edt_SLSP_QLSP);
        edt_GiaSP = (EditText) findViewById(R.id.edt_GiaSP_QLSP);
        edtSPmoi = (EditText) findViewById(R.id.edt_SPmoi_QLSP);
        ibtnCamera = (ImageButton) findViewById(R.id.imageButtonCamera);
        ibtnFolder = (ImageButton) findViewById(R.id.imageButtonFolder);
        imgHinh = (ImageView) findViewById(R.id.imageViewHinh_QLSP);

    }
}