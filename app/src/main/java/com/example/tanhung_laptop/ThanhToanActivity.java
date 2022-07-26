package com.example.tanhung_laptop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.GioHangAdapter;
import com.example.tanhung_laptop.Models.GioHang;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;
import com.example.tanhung_laptop.User.DangNhap_Activity;
import com.example.tanhung_laptop.User.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vn.momo.momo_partner.AppMoMoLib;

public class ThanhToanActivity extends AppCompatActivity {

    EditText diachi;
    EditText ghichu;
    Button btnMoMo,btnDathang;
    CompositeDisposable compositeDisposable;
    TextView tongthanhtien;
    String thoigian;
    API api;
    double tong;
    private Integer amount = 1000000;
    private Integer fee = 0;
    int environment = 0;//developer default
    private String merchantName = "Tân Hưng";
    private String merchantCode = "MOMOFTQR20220725";
    private String merchantNameLabel = "Nhà cung cấp";
    private String description = "Thanh toán online cửa hàng Tân Hưng";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        compositeDisposable = new CompositeDisposable();
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
        AnhXa();
        GetData();
        Events();

    }

    private void Events() {
        btnMoMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compositeDisposable.add(api.layidhd()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                doubleModel -> {
                                    requestPayment(doubleModel.getResult());
                                }, throwable -> {
                                    Log.e("Lỗi loi lay du lieu", throwable.getMessage());
                                }));

            }
        });
        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themhd(ghichu.getText().toString(),diachi.getText().toString(),"");

            }
        });
    }

    private void GetData() {
        diachi.setText(DangNhap_Activity.taikhoan.getDIACHI());
        Tongtien();
    }
    private void themhd(String ghichu,String diachi, String TokenMoMo)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        thoigian = simpleDateFormat.format(Calendar.getInstance().getTime());
        compositeDisposable.add(api.themhd(DangNhap_Activity.taikhoan.getIDTAIKHOAN(),thoigian,
                tong,ghichu,diachi, TokenMoMo
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        laptopModel -> {
                            for (int position = 0; position< GioHangAdapter.sanPhamGioHangList.size(); position++)
                            {
                                GioHang themhoadon = GioHangAdapter.sanPhamGioHangList.get(position);
                                themcthd(themhoadon.getIDLT(),themhoadon.getTENLAPTOP(),themhoadon.getSOLUONG(),themhoadon.getTONGTIEN());
                                startActivity(new Intent(ThanhToanActivity.this, MainActivity.class));
                            }
                            xoahetgh();
                        }, throwable -> {
                            Toast.makeText(ThanhToanActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
    }
    private void Tongtien() {
        compositeDisposable.add(api.laytongtien(DangNhap_Activity.taikhoan.getIDTAIKHOAN())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        doubleModel -> {
//                            Toast.makeText(getContext(), doubleModel.getMessage(), Toast.LENGTH_SHORT).show();
                            if (doubleModel.isSuccess())
                            {
                                tong = doubleModel.getResult();
                                tongthanhtien.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(tong) + " VNĐ"));
                            }
                        }, throwable -> {
                            Toast.makeText(ThanhToanActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
    }
    private void themcthd(int idlt, String tenlaptop, int soluong, int thanhtien)
    {
        compositeDisposable.add(api.themcthd(DangNhap_Activity.taikhoan.getIDTAIKHOAN(),thoigian,idlt,
                tenlaptop,soluong,thanhtien
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                        }, throwable -> {
                            Toast.makeText(ThanhToanActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
    }
    private void xoahetgh()
    {
        compositeDisposable.add(api.xoahetgh(DangNhap_Activity.taikhoan.getIDTAIKHOAN())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        laptopModel -> {
                            GetData();
                        }, throwable -> {
                            Toast.makeText(ThanhToanActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
    }
    private void requestPayment(int idcthd) {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", tong / 1000); //Kiểu integer
        eventValue.put("orderId", idcthd); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", "Mã đơn hàng"); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", fee); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(ThanhToanActivity.this, eventValue);


    }
    //Get token callback from MoMo app an submit to server side
    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    String token = data.getStringExtra("data"); //Token response
                    themhd(ghichu.getText().toString(), diachi.getText().toString(), token);

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        Log.e("Momo", "Loi");
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Toast.makeText(ThanhToanActivity.this, "Loi", Toast.LENGTH_SHORT).show();
                    Log.e("Momo", "Loi");
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Toast.makeText(ThanhToanActivity.this, "Loi", Toast.LENGTH_SHORT).show();
                    Log.e("Momo", "Loi");
                } else {
                    Toast.makeText(ThanhToanActivity.this, "Loi", Toast.LENGTH_SHORT).show();
                    //TOKEN FAIL
                    Log.e("Momo", "Loi");
                }
            } else {
                Toast.makeText(ThanhToanActivity.this, "Loi", Toast.LENGTH_SHORT).show();
                Log.e("Momo", "Loi");
            }
        } else {
            Toast.makeText(ThanhToanActivity.this, "Loi", Toast.LENGTH_SHORT).show();
            Log.e("Momo", "Loi");
        }
    }
    private void AnhXa() {
        diachi = findViewById(R.id.diachi_thanhtoan);
        ghichu= findViewById(R.id.ghichu_thanhtoan);
        btnMoMo= findViewById(R.id.btnMoMo);
        btnDathang= findViewById(R.id.btnDathang);
        tongthanhtien= findViewById(R.id.tongthanhtien);
    }
}