package com.example.tanhung_laptop.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.GioHangAdapter;
import com.example.tanhung_laptop.Models.GioHang;
import com.example.tanhung_laptop.Models.TAIKHOAN;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThongTinNguoiDung_Activity extends AppCompatActivity {

    EditText edtTaikhoan, edtSdt, edtEmail, edtDiachi;
    Button btnCapnhat, btnDoimatkhau;
    ImageButton ibtnExit,imageButtonCamera,imageButtonFolder;
    CircleImageView img_user_cn;
    private boolean isEnabled;
    final int REQUEST_CODE_CAMERA=123;
    final int REQUEST_CODE_FOLDER=456;
    boolean checkimage = true,checkimagecam=true;
    int IDTAIKHOAN;
    CompositeDisposable compositeDisposable;
    API api;
    @Override
    protected void onStart() {
//        GetData();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nguoi_dung);
        Anhxa();
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();
        GetData();
        imageButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkimagecam)
                {
                    ActivityCompat.requestPermissions(
                            ThongTinNguoiDung_Activity.this,
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_CODE_CAMERA
                    );
                    checkimagecam=false;
                    imageButtonCamera.setImageResource(R.drawable.ic_baseline_save_24);
                    imageButtonFolder.setEnabled(false);
                }else {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) img_user_cn.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
                    byte[] b = baos.toByteArray();

                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                    API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
                    compositeDisposable =  new CompositeDisposable();
                    compositeDisposable.add(api.doiavata(DangNhap_Activity.taikhoan.getIDTAIKHOAN(),
                                    encodedImage)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        Toast.makeText(ThongTinNguoiDung_Activity.this, messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }, throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }));
                    imageButtonCamera.setImageResource(R.drawable.ic_baseline_photo_camera_24);
                    imageButtonFolder.setEnabled(true);
                }

            }
        });

        imageButtonFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkimage)
                {
                    ActivityCompat.requestPermissions(
                            ThongTinNguoiDung_Activity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_FOLDER
                    );
                    checkimage=false;
                    imageButtonFolder.setImageResource(R.drawable.ic_baseline_save_24);
                    imageButtonCamera.setEnabled(false);
                }else {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) img_user_cn.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
                    byte[] b = baos.toByteArray();

                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                    API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
                    compositeDisposable =  new CompositeDisposable();
                    compositeDisposable.add(api.doiavata(DangNhap_Activity.taikhoan.getIDTAIKHOAN(),
                                    encodedImage)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        Toast.makeText(ThongTinNguoiDung_Activity.this, messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }, throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }));

                    imageButtonFolder.setImageResource(R.drawable.folder_open_white);
                    imageButtonCamera.setEnabled(true);
                }

            }

        });
    }
    private void GetData() {
        //get data
        int id = DangNhap_Activity.taikhoan.getIDTAIKHOAN();
        compositeDisposable.add(api.thongTinTaiKhoan(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        taiKhoanModel -> {
                            if (taiKhoanModel.isSuccess()) {
                                TAIKHOAN taiKhoan = taiKhoanModel.getResult().get(0);
                                String tentaikhoan = taiKhoan.getTENTAIKHOAN();
                                int sdt = taiKhoan.getSDT();
                                String email = taiKhoan.getEMAIL();
                                String diachi = taiKhoan.getDIACHI();
                                enableControl();
                                if (taiKhoan.getHINHANH() == null){
                                    img_user_cn.setImageResource(R.drawable.ic_baseline_person_pin_24);
                                }else
                                {
                                    byte[] decodedString = Base64.decode(taiKhoan.getHINHANH(), Base64.DEFAULT);
                                    Bitmap imgBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    img_user_cn.setImageBitmap(imgBitMap);

                                    edtTaikhoan.setText(tentaikhoan);
                                    edtTaikhoan.setEnabled(false);
                                    edtSdt.setText(String.valueOf(sdt));
                                    edtEmail.setText(email);
                                    edtDiachi.setText(diachi);
                                }
                                IDTAIKHOAN = taiKhoan.getIDTAIKHOAN();
                            }
                        }, throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));






    }

    private void Anhxa() {
        imageButtonCamera = findViewById(R.id.imageButtonCamera);
        imageButtonFolder = findViewById(R.id.imageButtonFolder);
        img_user_cn = findViewById(R.id.img_user_cn);
        edtTaikhoan = findViewById(R.id.edtTaikhoan);
        edtSdt = findViewById(R.id.edtSdt);
        edtEmail = findViewById(R.id.edtEmail);
        edtDiachi = findViewById(R.id.edtDiachi);

        ibtnExit = findViewById(R.id.ibtnExit);
        ibtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThongTinNguoiDung_Activity.this,MainActivity.class));
            }
        });

        btnCapnhat = findViewById(R.id.btnCapnhat);
        btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEnabled = !isEnabled;
                enableControl();
                if (isEnabled){
                    btnCapnhat.setText("Lưu");
                }
                else{
                    btnCapnhat.setText("Cập nhật");

                    // PHP cập nhật tài khoản
                    int idtk = DangNhap_Activity.taikhoan.getIDTAIKHOAN();
                    String sdt = edtSdt.getText().toString().trim();
                    String email = edtEmail.getText().toString();
                    String diachi = edtDiachi.getText().toString();

                    compositeDisposable.add(api.capnhatthongtin(idtk, sdt, email, "2000-06-10",diachi)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }, throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }));
                }
            }
        });

        btnDoimatkhau = findViewById(R.id.btnDoimatkhau);
        btnDoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog();
            }
        });


    }
    private void showdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ThongTinNguoiDung_Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau,null);
        final EditText nhapmkcu = view.findViewById(R.id.nhapmkcu);
        final EditText nhapmkmoi = view.findViewById(R.id.nhapmkmoi);
        final EditText nhaplaimk = view.findViewById(R.id.nhaplaimk);
        builder.setView(view);
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (nhapmkcu.getText().toString().isEmpty())
                {
                    Toast.makeText(ThongTinNguoiDung_Activity.this, "Không được để trống!", Toast.LENGTH_SHORT).show();
                }
                else if (nhapmkmoi.getText().toString().isEmpty())
                {
                    Toast.makeText(ThongTinNguoiDung_Activity.this, "Không được để trống!", Toast.LENGTH_SHORT).show();

                }
                else if (nhaplaimk.getText().toString().isEmpty())
                {
                    Toast.makeText(ThongTinNguoiDung_Activity.this, "Không được để trống!", Toast.LENGTH_SHORT).show();

                }
                else if (nhapmkmoi.getText().toString().equals(nhaplaimk.getText().toString()))
                {
                    compositeDisposable.add(api.doimatkhau(DangNhap_Activity.taikhoan.getIDTAIKHOAN(),
                                    nhapmkcu.getText().toString(),
                                    nhapmkmoi.getText().toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    taiKhoanModel -> {
                                        if (!taiKhoanModel.isSuccess())
                                        {
                                            showdialog();
                                        }
                                        Toast.makeText(ThongTinNguoiDung_Activity.this, taiKhoanModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }, throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }));
                }else
                {
                    nhaplaimk.setText("");
                    Toast.makeText(ThongTinNguoiDung_Activity.this, " Mật khẩu không khớp mật khẩu mới!", Toast.LENGTH_SHORT).show();
                }


            }
        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
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
                    Toast.makeText(ThongTinNguoiDung_Activity.this," Bạn không cho phép mở camera", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(ThongTinNguoiDung_Activity.this," Bạn không cho phép mở folder", Toast.LENGTH_LONG).show();
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
            img_user_cn.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_user_cn.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void enableControl() {
        edtSdt.setEnabled(isEnabled);
        edtEmail.setEnabled(isEnabled);
        edtDiachi.setEnabled(isEnabled);
    }
}