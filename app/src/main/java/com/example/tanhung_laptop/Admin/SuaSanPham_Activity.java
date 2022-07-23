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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.CategoryAdapter;
import com.example.tanhung_laptop.Adapter.LaptopAdminAdapter;
import com.example.tanhung_laptop.Models.Category;
import com.example.tanhung_laptop.Models.LAPTOP;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;
import com.example.tanhung_laptop.User.BatDau_activity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SuaSanPham_Activity extends AppCompatActivity {
    ArrayList<Category> listCategory;
    ArrayList<Category> list;
    CategoryAdapter categoryAdapter;
    Spinner spinner_nsx;
    Button btnAdd,btnCancel;
    EditText editTen, edtDanhMuc,edtSoLuong, edt_GiaSP,edtSPmoi,edt_mota_QLSP;
    ImageButton ibtnCamera,ibtnFolder;
    ImageView imgHinh,quaylai_QLSP;
    final int REQUEST_CODE_CAMERA=123;
    final int REQUEST_CODE_FOLDER=456;
    LAPTOP laptop;
    int id,MASP;
    API api;
    String tendanhmuc=null;
    TextView idnsx;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_san_pham);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",1123);
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable = new CompositeDisposable();

        Anhxa();
        Getdata();
        edtDanhMuc.setEnabled(false);
        listCategory = getListCategory();
        categoryAdapter = new CategoryAdapter(SuaSanPham_Activity.this, R.layout.item_select, listCategory);
        spinner_nsx.setAdapter(categoryAdapter);
        spinner_nsx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                tendanhmuc = categoryAdapter.getItem(position).getName();
                edtDanhMuc.setText(categoryAdapter.getItem(position).getIDcategory()+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
                byte[] b = baos.toByteArray();

                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                compositeDisposable.add(api.capNhatlt(MASP,
                                encodedImage,
                                editTen.getText().toString().trim(),
                                Integer.parseInt(edt_GiaSP.getText().toString().trim()),
                                Integer.parseInt(edtSoLuong.getText().toString().trim()),
                                edt_mota_QLSP.getText().toString(),
                                Integer.parseInt(edtDanhMuc.getText().toString().trim()),
                                Integer.parseInt(edtSPmoi.getText().toString().trim()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                messageModel -> {
                                    if (messageModel.isSuccess())
                                    {
                                        startActivity(new Intent(SuaSanPham_Activity.this, HomeAdmin_Activity.class));

                                    }
                                    Toast.makeText(SuaSanPham_Activity.this, messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                , throwable -> {
                                    Log.e("Lỗi Sửa",throwable.getMessage() );
                                    Toast.makeText(SuaSanPham_Activity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        )
                );


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
        Log.e("TAG", "Getdata: " + MASP );
        editTen.setText(laptop.getTENLAPTOP());
        edt_GiaSP.setText(String.valueOf(laptop.getGIASP()));
        edtDanhMuc.setText(String.valueOf(laptop.getIDNSX()));
        edtSoLuong.setText(String.valueOf(laptop.getSOLUONG()));
        edtSPmoi.setText(String.valueOf(laptop.getLTMOI()));
        edt_mota_QLSP.setText(laptop.getMOTASP());

        byte[] decodedString = Base64.decode(laptop.getHINHANH(), Base64.DEFAULT);
        Bitmap imgBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgHinh.setImageBitmap(imgBitMap);
//        byte[] hinhAnh = laptop.getHINHANH();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
//        imgHinh.setImageBitmap(bitmap);
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
        spinner_nsx = findViewById(R.id.spinner_nsx);
        idnsx = findViewById(R.id.idnsx);
        edt_mota_QLSP= findViewById(R.id.edt_mota_QLSP);
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

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
    private ArrayList<Category> getListCategory() {
        list = new ArrayList<>();
        list.clear();
        list.add(new Category(
                        " APPLE ",
                        1
                )
        );
        list.add(new Category(
                        " ASUS ",
                        2
                )
        );
        list.add(new Category(
                        " DELL ",
                        3
                )
        );
        list.add(new Category(
                        " HP ",
                        4
                )
        );
        list.add(new Category(
                        " ACER ",
                        5
                )
        );




        return list;
    }
}