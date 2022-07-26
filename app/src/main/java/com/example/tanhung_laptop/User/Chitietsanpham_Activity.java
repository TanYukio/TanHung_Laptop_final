package com.example.tanhung_laptop.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.BinhLuanAdapter;
import com.example.tanhung_laptop.Adapter.LAPTOP_ADAPTER;
import com.example.tanhung_laptop.Adapter.TimKiemAdapter;
import com.example.tanhung_laptop.Models.BinhLuan;
import com.example.tanhung_laptop.Models.LAPTOP;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Chitietsanpham_Activity extends AppCompatActivity {

    LAPTOP laptop;
    TextView name, price, noidung_ctsp;
    ImageView imgHinh;
    EditText editTextSL;
    Button btnaddcart, btn_GuiBl;
    ImageButton btn_quaylai;
    int id, idtk;
    NestedScrollView scrollV;
    RecyclerView recV_chatbox;
    ArrayList<BinhLuan> listBL;
    BinhLuanAdapter binhLuanAdapter;
    EditText edt_noidungbl_sanpham;
    CompositeDisposable compositeDisposable;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1);
        idtk = intent.getIntExtra("idtk", 2);
        Anhxa();
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable = new CompositeDisposable();

        Sukien();
        GetDataSP();
        btn_quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Chitietsanpham_Activity.this, MainActivity.class));
            }
        });

        btnaddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int SL = Integer.parseInt(editTextSL.getText().toString());
                if (idtk == 2) {
                    laptop = LAPTOP_ADAPTER.laptopList.get(id);
                } else {
                    laptop = TimKiemAdapter.laptopList.get(idtk);
                }

                if (DangNhap_Activity.taikhoan.getIDTAIKHOAN() == -1) {
                    Toast.makeText(Chitietsanpham_Activity.this, "Bạn phải đăng nhập để mua hàng !", Toast.LENGTH_SHORT).show();
                } else if (laptop.getSOLUONG() == 1) {
                    Toast.makeText(Chitietsanpham_Activity.this, " Sản phẩm hiện đã hết hàng !  ", Toast.LENGTH_SHORT).show();

                } else if (SL > (laptop.getSOLUONG() - 1)) {
                    Toast.makeText(Chitietsanpham_Activity.this, "Hàng trong kho chỉ còn : " + (laptop.getSOLUONG() - 1) + " sản phẩm ", Toast.LENGTH_SHORT).show();

                } else if (SL == 0) {
                    Toast.makeText(Chitietsanpham_Activity.this, " Số lượng không hợp lệ !  ", Toast.LENGTH_SHORT).show();

                } else if (SL > 5) {
                    Toast.makeText(Chitietsanpham_Activity.this, " Mỗi lần chỉ mua được 4 sản phẩm !  ", Toast.LENGTH_SHORT).show();

                } else {

                    compositeDisposable.add(api.themspgh(DangNhap_Activity.taikhoan.getIDTAIKHOAN(),
                            laptop.getIDLT(),
                            laptop.getTENLAPTOP(),
                            SL,
                            SL * laptop.getGIASP())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    laptopModel -> {
                                        if (laptopModel.isSuccess()) {
                                            Intent intent = new Intent(Chitietsanpham_Activity.this, MainActivity.class);
                                            intent.putExtra("giohang", R.id.giohang);
                                            startActivity(intent);
                                        }
                                        Toast.makeText(Chitietsanpham_Activity.this, laptopModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }, throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }));

                }

            }
        });
    }

    private void Sukien() {
        edt_noidungbl_sanpham.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                scrollV.scrollTo(0, edt_noidungbl_sanpham.getScrollY());
            }
        });
        btn_GuiBl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DangNhap_Activity.taikhoan.getIDTAIKHOAN() == -1) {
                    Toast.makeText(Chitietsanpham_Activity.this, "Bạn chưa đăng nhập !", Toast.LENGTH_LONG).show();
                } else {
                    if (!TextUtils.isEmpty(edt_noidungbl_sanpham.getText().toString())) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());
                        if (idtk == 2) {
                            laptop = LAPTOP_ADAPTER.laptopList.get(id);
                        } else {
                            laptop = TimKiemAdapter.laptopList.get(idtk);
                        }
                        laptop = LAPTOP_ADAPTER.laptopList.get(id);

                        compositeDisposable.add(api.thembl(DangNhap_Activity.taikhoan.getIDTAIKHOAN(),
                                laptop.getIDLT(), currentDateandTime, edt_noidungbl_sanpham.getText().toString())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        messageModel -> {
                                            if (messageModel.isSuccess()) {
                                                listBL.add(0, new BinhLuan(
                                                        DangNhap_Activity.taikhoan.getIDTAIKHOAN(), DangNhap_Activity.taikhoan.getHINHANH(), currentDateandTime,
                                                        edt_noidungbl_sanpham.getText().toString()
                                                ));
                                                binhLuanAdapter.notifyItemInserted(0);
                                                edt_noidungbl_sanpham.setText("");
                                            }
                                            Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                        }, throwable -> {
                                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                        }));
                    } else {
                        Toast.makeText(Chitietsanpham_Activity.this, "Bạn chưa nhập bình luận", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void Anhxa() {
        edt_noidungbl_sanpham = findViewById(R.id.edt_noidungbl_sanpham);
        noidung_ctsp = findViewById(R.id.noidung_ctsp);
        scrollV = (NestedScrollView) findViewById(R.id.scrollV);
        name = (TextView) findViewById(R.id.product_name_CT);
        price = (TextView) findViewById(R.id.product_price_CT);
        imgHinh = (ImageView) findViewById(R.id.product_image_CT);
        btnaddcart = (Button) findViewById(R.id.btnadd_addtocart_CT);
        editTextSL = (EditText) findViewById(R.id.product_SL_CT);
        btn_quaylai = (ImageButton) findViewById(R.id.btn_quaylai);
        btn_GuiBl = findViewById(R.id.btn_GuiBl);
        recV_chatbox = findViewById(R.id.rec_Binhluan_sanpham);
    }

    private void GetDataSP() {
        if (idtk == 2) {
            laptop = LAPTOP_ADAPTER.laptopList.get(id);
        } else {
            laptop = TimKiemAdapter.laptopList.get(idtk);
        }
        //get data
        String ten = laptop.getTENLAPTOP();
        name.setText(ten);
        noidung_ctsp.setText(laptop.getMOTASP());
        price.setText(NumberFormat.getNumberInstance(Locale.US).format(laptop.getGIASP() + " VNĐ"));


        byte[] decodedString = Base64.decode(laptop.getHINHANH(), Base64.DEFAULT);
        Bitmap imgBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgHinh.setImageBitmap(imgBitMap);
    }

    @Override
    protected void onStart() {

        if (idtk == 2) {
            laptop = LAPTOP_ADAPTER.laptopList.get(id);
        } else {
            laptop = TimKiemAdapter.laptopList.get(idtk);
        }
        listBL = new ArrayList<>();

        compositeDisposable.add(api.layBl(laptop.getIDLT())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        binhluanModel -> {
                            listBL.clear();
                            if (binhluanModel.isSuccess()) {
                                for (int i = 0; i < binhluanModel.getResult().size(); i++) {
                                    listBL.add(binhluanModel.getResult().get(i));
                                }
                            }
                            binhLuanAdapter.notifyDataSetChanged();
                        }, throwable -> {
                            Toast.makeText(Chitietsanpham_Activity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));


        binhLuanAdapter = new BinhLuanAdapter(listBL);
        recV_chatbox.setAdapter(binhLuanAdapter);
        recV_chatbox.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        super.onStart();
    }

}