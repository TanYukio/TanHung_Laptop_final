package com.example.tanhung_laptop.User;

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
import android.widget.Toast;

import com.example.tanhung_laptop.Models.TAIKHOAN;
import com.example.tanhung_laptop.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nguoi_dung);
        Anhxa();

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
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                    byte[] hinhAnh = byteArray.toByteArray();
                    BatDau_activity.database.UPDATE_IMAGE_TK(IDTAIKHOAN,hinhAnh);
                    imageButtonCamera.setImageResource(R.drawable.ic_baseline_photo_camera_24);
                    imageButtonFolder.setEnabled(true);
                    GetData();
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
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                    byte[] hinhAnh = byteArray.toByteArray();
                    BatDau_activity.database.UPDATE_IMAGE_TK(IDTAIKHOAN,hinhAnh);
                    imageButtonFolder.setImageResource(R.drawable.ic_baseline_folder_open_24);
                    imageButtonCamera.setEnabled(true);
                    Toast.makeText(ThongTinNguoiDung_Activity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    GetData();
                }

            }

        });
    }
    private void GetData() {
        //get data
        int id = DangNhap_Activity.taikhoan.getIDTAIKHOAN();
        TAIKHOAN taiKhoan = BatDau_activity.database.Load(id);
        String tentaikhoan = taiKhoan.getTENTAIKHOAN();
        int sdt = taiKhoan.getSDT();
        String email = taiKhoan.getEMAIL();
        String diachi = taiKhoan.getDIACHI();
        enableControl();
        if (taiKhoan.getHINHANH() == null){
            img_user_cn.setImageResource(R.drawable.user);
        }else
        {
            byte[] hinhAnh = taiKhoan.getHINHANH();
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
            img_user_cn.setImageBitmap(bitmap);
//            Toast.makeText(InforUserActivity.this, "sssss : " + hinhAnh, Toast.LENGTH_SHORT).show();

        }
        IDTAIKHOAN = taiKhoan.getIDTAIKHOAN();


        edtTaikhoan.setText(tentaikhoan);
        edtTaikhoan.setEnabled(false);
        edtSdt.setText(String.valueOf(sdt));
        edtEmail.setText(email);
        edtDiachi.setText(diachi);


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
                    BatDau_activity.database.CapNhatTaiKhoan(DangNhap_Activity.taikhoan.getIDTAIKHOAN(), Integer.parseInt(edtSdt.getText().toString().trim()),
                            edtEmail.getText().toString(), edtDiachi.getText().toString());
                    Toast.makeText(ThongTinNguoiDung_Activity.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        });

        btnDoimatkhau = findViewById(R.id.btnDoimatkhau);
        btnDoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(ThongTinNguoiDung_Activity.this, .class));
                Toast.makeText(ThongTinNguoiDung_Activity.this, " Đổi mật khẩu ", Toast.LENGTH_SHORT).show();
            }
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