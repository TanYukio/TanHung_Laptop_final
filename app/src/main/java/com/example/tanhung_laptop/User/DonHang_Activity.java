package com.example.tanhung_laptop.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.HoaDonAdapter;
import com.example.tanhung_laptop.Models.HoaDon;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DonHang_Activity extends AppCompatActivity {

    ListView Listview_Lichsu;
    ArrayList<HoaDon> hoaDonArrayList;
    HoaDonAdapter adapter;
    TextView txtthongbao,title_qlhd,tongtien_HD,tongchi;
    ImageButton ibtnExit_lichsu;
    LinearLayout layoutdoanhthu;
    CompositeDisposable compositeDisposable;
    int idcthd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        AnhXa();
        Listview_Lichsu = (ListView) findViewById(R.id.listview_danhsachhoadon_lichsu);

        hoaDonArrayList = new ArrayList<>();
        adapter = new HoaDonAdapter(DonHang_Activity.this, R.layout.danhsach_lichsu, hoaDonArrayList);
        Listview_Lichsu.setAdapter(adapter);
        registerForContextMenu(Listview_Lichsu);
        Listview_Lichsu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DonHang_Activity.this,ChiTietDonHang_Activity.class);
                HoaDon hoaDon = HoaDonAdapter.ListHoaDon.get(i);
                idcthd = hoaDon.getIDHOADON();
                intent.putExtra("idcthd",idcthd);
                intent.putExtra("KEYHD", i);
                startActivity(intent);
            }
        });

        GetData();
    }
    private void AnhXa() {
        layoutdoanhthu = findViewById(R.id.layoutdoanhthu);
        layoutdoanhthu.setBackgroundResource(R.color.cam);
        ibtnExit_lichsu = findViewById(R.id.ibtnExit_lichsu);
        ibtnExit_lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DonHang_Activity.this, MainActivity.class));
                finish();
            }
        });
        txtthongbao = (TextView) findViewById(R.id.thongbaolichsu);
        title_qlhd = findViewById(R.id.title_qlhd);
        tongtien_HD = findViewById(R.id.tongtien_HD);
        title_qlhd.setText(" Lịch sử mua hàng");
        tongchi = findViewById(R.id.tongchi);
        tongchi.setText(" Tổng chi :");
    }

    private void GetData() {
        //get data
        // api laytongtienhd
        laytongtienhd();
        // api layhd
        layhoadon();


    }

    private void layhoadon() {

        API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();
        compositeDisposable.add(api.layHd(DangNhap_Activity.taikhoan.getIDTAIKHOAN())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        hoadonModel -> {

                            hoaDonArrayList.clear();
                            if (hoadonModel.isSuccess()) {
//                                Log.e("1",laptopModel.getResult().get(0).getTENLAPTOP());

                                for (int i= 0;i<hoadonModel.getResult().size();i++){
                                    hoaDonArrayList.add(hoadonModel.getResult().get(i));
                                }
//                                Toast.makeText(this, "Thành công", Toast.LENGTH_LONG).show();
//                                Log.e("đâ", laptopArrayList.size() + "");

                            }
                            adapter.notifyDataSetChanged();
                            if (DangNhap_Activity.taikhoan.getIDTAIKHOAN() == -1)
                            {
                                txtthongbao.setText(" Bạn hãy đăng nhập để có thể xem hóa đơn !");
                            }else if (hoaDonArrayList.isEmpty()){
                                txtthongbao.setText(" Bạn chưa có hóa đơn !");
                            }
                        }, throwable -> {
//                            Log.e("Lỗi", throwable.getMessage());
                            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
    }

    private void laytongtienhd()
    {
        API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();
        compositeDisposable.add(api.laytongtienhd(DangNhap_Activity.taikhoan.getIDTAIKHOAN())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        integerModel -> {
                            if (integerModel.isSuccess())
                            {
                                tongtien_HD.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(integerModel.getResult()) + " VNĐ"));

                            }
//                            Toast.makeText(this, " lay tong tien hoa don", Toast.LENGTH_SHORT).show();

                        }, throwable -> {
//                            Toast.makeText(DonHang_Activity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e(" tổng chi ", "" + throwable.getMessage());
                        }));
    }
}