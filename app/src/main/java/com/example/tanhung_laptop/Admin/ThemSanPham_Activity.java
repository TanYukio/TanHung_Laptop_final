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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.CategoryAdapter;
import com.example.tanhung_laptop.Models.Category;
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
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThemSanPham_Activity extends AppCompatActivity {
    ArrayList<Category> listCategory;
    ArrayList<Category> list;
    CategoryAdapter categoryAdapter;
    Spinner spinner_nsx;
    Button btnAdd, btnCancel;
    EditText editTen, edtDanhMuc, edtSoLuong, edt_GiaSP, edtSPmoi,edt_mota;
    ImageButton ibtnCamera, ibtnFolder;
    ImageView imgHinh, quaylai_QLSP;
    final int REQUEST_CODE_CAMERA = 123;
    final int REQUEST_CODE_FOLDER = 456;
    CompositeDisposable compositeDisposable;
    API api;
    String tendanhmuc=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);
        Anhxa();
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable = new CompositeDisposable();
        edtDanhMuc.setEnabled(false);
        listCategory = getListCategory();
        categoryAdapter = new CategoryAdapter(ThemSanPham_Activity.this, R.layout.item_select, listCategory);
        spinner_nsx.setAdapter(categoryAdapter);
        spinner_nsx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tendanhmuc = categoryAdapter.getItem(position).getName();
                edtDanhMuc.setText(categoryAdapter.getItem(position).getIDcategory()+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tendanhmuc = categoryAdapter.getItem(Integer.parseInt(edtDanhMuc.getText().toString())).getName();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chuyen data image view -> mang byte[]
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
                byte[] b = baos.toByteArray();

                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                compositeDisposable.add(api.themlt(encodedImage,
                                editTen.getText().toString().trim(),
                                Integer.parseInt(edt_GiaSP.getText().toString().trim()),
                                Integer.parseInt(edtSoLuong.getText().toString().trim()),
                                edt_mota.getText().toString(),
                                Integer.parseInt(edtDanhMuc.getText().toString().trim()),
                                Integer.parseInt(edtSPmoi.getText().toString().trim()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                messageModel -> {
                                    Toast.makeText(ThemSanPham_Activity.this, messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                , throwable -> {
                                    Toast.makeText(ThemSanPham_Activity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        )
                );

                Toast.makeText(ThemSanPham_Activity.this, " Thêm sản phẩm thành công", Toast.LENGTH_LONG).show();
                edt_GiaSP.setText("");
                edtDanhMuc.setText("");
                edtSoLuong.setText("");
                edtSPmoi.setText("");
                editTen.setText("");
                edt_mota.setText("");
                imgHinh.setImageResource(R.drawable.photo);

            }
        });

        ibtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        ThemSanPham_Activity.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA
                );

            }
        });

        ibtnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        ThemSanPham_Activity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOLDER
                );
            }
        });
        quaylai_QLSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ThemSanPham_Activity.this,HomeAdmin_Activity.class));
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThemSanPham_Activity.this,HomeAdmin_Activity.class));

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                } else {
                    Toast.makeText(ThemSanPham_Activity.this, " Bạn không cho phép mở camera", Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_CODE_FOLDER:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_FOLDER);
                } else {
                    Toast.makeText(ThemSanPham_Activity.this, " Bạn không cho phép mở folder", Toast.LENGTH_LONG).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinh.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
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
        edt_mota = findViewById(R.id.edt_mota);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnCancel = (Button) findViewById(R.id.buttonHuy_QlSP);
        editTen = (EditText) findViewById(R.id.edt_TenSP_QLSP);
        edtDanhMuc = (EditText) findViewById(R.id.edt_IDDanhMuc_QLSP);
        edtSoLuong = (EditText) findViewById(R.id.edt_SLSP_QLSP);
        edt_GiaSP = (EditText) findViewById(R.id.edt_GiaSP_QLSP);
        edtSPmoi = (EditText) findViewById(R.id.edt_SPmoi_QLSP);
        quaylai_QLSP = findViewById(R.id.quaylai_QLSP);
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