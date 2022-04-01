package com.example.tanhung_laptop.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tanhung_laptop.Adapter.CTHoaDonAdapter;
import com.example.tanhung_laptop.Adapter.HoaDonAdapter;
import com.example.tanhung_laptop.Models.CTHoaDon;
import com.example.tanhung_laptop.Models.HoaDon;
import com.example.tanhung_laptop.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChiTietDonHang_Activity extends AppCompatActivity {
    ListView Listview_Lichsu;
    ImageView ibtnExit_lichsu,imageHinhlichsu_HD;
    TextView textviewTongTien_HD,textviewdc_HD,textviewgc_HD;
    ArrayList<CTHoaDon> cthoaDonArrayList;
    CTHoaDonAdapter adapter;
    int idcthd,KEYhd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);

        Intent intent = getIntent();
        idcthd = intent.getIntExtra("idcthd",1123);
        KEYhd = intent.getIntExtra("KEYHD",123);
//        Toast.makeText(ChiTietLichSu.this, "ssss : " + idcthd, Toast.LENGTH_SHORT).show();

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



        Cursor cursor = BatDau_activity.database.GetData("SELECT * FROM CHITIETHOADON WHERE IDCTHOADON = " + idcthd);
        cthoaDonArrayList.clear();
        while (cursor.moveToNext())
        {
            cthoaDonArrayList.add(new CTHoaDon(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5)
            ));
        }
        adapter.notifyDataSetChanged();
    }
}