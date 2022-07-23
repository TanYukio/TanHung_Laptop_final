package com.example.tanhung_laptop.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.CTHoaDonAdapter;
import com.example.tanhung_laptop.Adapter.HoaDonAdapter;
import com.example.tanhung_laptop.Models.CTHoaDon;
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

public class ChiTietDonHang_Activity extends AppCompatActivity {

    ListView Listview_Lichsu;
    ImageView ibtnExit_lichsu,imageHinhlichsu_HD;
    TextView textviewTongTien_HD,textviewdc_HD,textviewgc_HD;
    ArrayList<CTHoaDon> cthoaDonArrayList;
    CTHoaDonAdapter adapter;
    CompositeDisposable compositeDisposable;
    int idcthd,KEYhd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);

        Intent intent = getIntent();
        idcthd = intent.getIntExtra("idcthd",1123);
        KEYhd = intent.getIntExtra("KEYHD",123);

        AnhXa();
        Listview_Lichsu = (ListView) findViewById(R.id.listview_danhsachchitiethoadon_lichsu);

        cthoaDonArrayList = new ArrayList<>();
        adapter = new CTHoaDonAdapter(ChiTietDonHang_Activity.this, R.layout.danhsachchitietlichsu, cthoaDonArrayList);
        Listview_Lichsu.setAdapter(adapter);
        registerForContextMenu(Listview_Lichsu);

        GetData();
    }
    private void AnhXa() {
        textviewgc_HD = findViewById(R.id.textviewgc_HD);
        textviewdc_HD = findViewById(R.id.textviewdc_HD);
        textviewTongTien_HD = findViewById(R.id.textviewTongTien_HD);
        imageHinhlichsu_HD = findViewById(R.id.imageHinhlichsu_HD);
        ibtnExit_lichsu = findViewById(R.id.ibtnExit_lichsu);
        ibtnExit_lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void GetData() {
        //get data
        HoaDon hoaDon = HoaDonAdapter.ListHoaDon.get(KEYhd);
        textviewgc_HD.setText("Ghi chú : " + hoaDon.getGHICHU());
        textviewdc_HD.setText("Địa chỉ : " + hoaDon.getDIACHI());
        textviewTongTien_HD.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(hoaDon.getTONGTIEN())) + " VNĐ");

        API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();
        Log.e("idcthd", "" + idcthd);
        compositeDisposable.add(api.layCthd(idcthd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        cthoadonModel -> {

                            cthoaDonArrayList.clear();
                            if (cthoadonModel.isSuccess()) {
//                                Log.e("1",laptopModel.getResult().get(0).getTENLAPTOP());

                                for (int i= 0;i<cthoadonModel.getResult().size();i++){
                                    cthoaDonArrayList.add(cthoadonModel.getResult().get(i));
                                }
//                                Toast.makeText(this, "Thành công", Toast.LENGTH_LONG).show();
//                                Log.e("đâ", laptopArrayList.size() + "");

                            }
                            adapter.notifyDataSetChanged();
                        }, throwable -> {
//                            Log.e("Lỗi", throwable.getMessage());
                            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
    }
}