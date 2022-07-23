package com.example.tanhung_laptop.Admin;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.CategoryAdapter;
import com.example.tanhung_laptop.Adapter.CategoryAdapter_DSTK;
import com.example.tanhung_laptop.Adapter.HoaDonAdapter;
import com.example.tanhung_laptop.Models.Category;
import com.example.tanhung_laptop.Models.DSTK;
import com.example.tanhung_laptop.Models.HoaDon;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;
import com.example.tanhung_laptop.User.BatDau_activity;
import com.example.tanhung_laptop.User.ChiTietDonHang_Activity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HoaDon_Activity extends AppCompatActivity {
    Spinner spnAddTheloai_TK;
    ArrayList<DSTK> list;
    CategoryAdapter_DSTK categoryAdapter_dstk;
    int Danhmuc;

    ListView Listview_Lichsu;
    ArrayList<HoaDon> hoaDonArrayList;
    HoaDonAdapter adapter;
    LinearLayout layoutdoanhthu;
    TextView title_qlhd, tongtien_HD, tongchi;
    ImageButton ibtnExit_lichsu;
    CompositeDisposable compositeDisposable;
    API api;
    int idcthd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        AnhXa();
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable = new CompositeDisposable();

        Listview_Lichsu = (ListView) findViewById(R.id.listview_danhsachhoadon_lichsu);

        spnAddTheloai_TK = findViewById(R.id.spnAddTheloai_TK);
        list = new ArrayList<>();
        getListCategory();
        categoryAdapter_dstk = new CategoryAdapter_DSTK(HoaDon_Activity.this, R.layout.item_select, list);
        spnAddTheloai_TK.setAdapter(categoryAdapter_dstk);
        Danhmuc = 0;
        spnAddTheloai_TK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Danhmuc = categoryAdapter_dstk.getItem(position).getIDTAIKHOAN();
                GetData();
                GetTienAlone();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        hoaDonArrayList = new ArrayList<>();
        adapter = new HoaDonAdapter(HoaDon_Activity.this, R.layout.danhsach_lichsu, hoaDonArrayList);
        Listview_Lichsu.setAdapter(adapter);
        registerForContextMenu(Listview_Lichsu);
        Listview_Lichsu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HoaDon_Activity.this, ChiTietDonHang_Activity.class);
                HoaDon hoaDon = HoaDonAdapter.ListHoaDon.get(i);
                idcthd = hoaDon.getIDHOADON();
                intent.putExtra("idcthd", idcthd);
                intent.putExtra("KEYHD", i);
                startActivity(intent);
            }
        });
        GetTien();
        GetData();

    }

    @Override
    protected void onStart() {
        GetTien();
        GetData();
        super.onStart();
    }

    private void AnhXa() {
        layoutdoanhthu = findViewById(R.id.layoutdoanhthu);
        title_qlhd = findViewById(R.id.title_qlhd);
        tongtien_HD = findViewById(R.id.tongtien_HD);
        title_qlhd.setText("Thống kê Doanh Thu");
        tongchi = findViewById(R.id.tongchi);
        tongchi.setText(" Tổng Doanh Thu: ");
        ibtnExit_lichsu = findViewById(R.id.ibtnExit_lichsu);
        ibtnExit_lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HoaDon_Activity.this,HomeAdmin_Activity.class));
            }
        });
    }

    private void GetTien() {
        compositeDisposable.add(api.layhettongtienhd()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        integerModel -> {
                            if (integerModel.isSuccess()) {
                                tongtien_HD.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(integerModel.getResult()) + " VNĐ"));

//                                Toast.makeText(HoaDon_Activity.this, "Tổng tiền : " + String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(integerModel.getResult()) + " VNĐ"), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(this, integerModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }, throwable -> {
                            Log.e("Lỗi tiền 1 " , throwable.getMessage());
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));

    }

    private void GetTienAlone() {
        compositeDisposable.add(api.laytongtienhd(Danhmuc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        integerModel -> {

                            if (integerModel.isSuccess()) {
                                Toast.makeText(HoaDon_Activity.this, "Tổng tiền : " + String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(integerModel.getResult()) + " VNĐ"), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(this, integerModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }, throwable -> {
                            Log.e("Lỗi tiền " + Danhmuc  , throwable.getMessage());
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));

    }

    private void GetData() {
        //get data

        compositeDisposable.add(api.layHd(Danhmuc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        hoadonModel -> {
                            hoaDonArrayList.clear();
                            if (hoadonModel.isSuccess()) {
                                for (int i = 0; i < hoadonModel.getResult().size(); i++) {
                                    hoaDonArrayList.add(hoadonModel.getResult().get(i));
                                }
//                                Toast.makeText(getApplicationContext(), "Thành công", Toast.LENGTH_LONG).show();
                            }
                            adapter.notifyDataSetChanged();


                        }, throwable -> {
                            Log.e("Lỗi 1  " , throwable.getMessage());
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));

    }

    private void getListCategory() {
        compositeDisposable.add(api.layhetTkTK()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        theLoaiModel -> {
                            list.clear();
                            if (theLoaiModel.isSuccess()) {
                                for (int i = 0; i < theLoaiModel.getResult().size(); i++) {
                                    list.add(theLoaiModel.getResult().get(i));
                                    Log.e("Dữ liệu", theLoaiModel.getResult().get(i).getTENTAIKHOAN() + " / "+ theLoaiModel.getResult().get(i).getIDTAIKHOAN());
                                }
//                                Toast.makeText(getApplicationContext(), "Thành công", Toast.LENGTH_LONG).show();
                            }
                            Log.e("DanhSach",list.size() + "");
                            categoryAdapter_dstk.notifyDataSetChanged();

                        }, throwable -> {
                            Log.e("Lỗi 2 " , throwable.getMessage());
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
    }
}