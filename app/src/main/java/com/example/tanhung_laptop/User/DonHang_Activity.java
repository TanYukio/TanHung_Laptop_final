package com.example.tanhung_laptop.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tanhung_laptop.Adapter.HoaDonAdapter;
import com.example.tanhung_laptop.Models.HoaDon;
import com.example.tanhung_laptop.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DonHang_Activity extends AppCompatActivity {

    ListView Listview_Lichsu;
    ArrayList<HoaDon> hoaDonArrayList;
    HoaDonAdapter adapter;
    TextView txtthongbao,title_qlhd,tongtien_HD,tongchi;
    ImageButton ibtnExit_lichsu;
    LinearLayout layoutdoanhthu;
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
                idcthd = hoaDon.getIDCTHOADON();
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
        Cursor cursor1 = BatDau_activity.database.GetData("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE IDTAIKHOAN = "
                + DangNhap_Activity.taikhoan.getIDTAIKHOAN());
        cursor1.moveToNext();
        tongtien_HD.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(cursor1.getInt(0)) + " VNĐ"));


        Cursor cursor = BatDau_activity.database.GetData("SELECT * FROM HOADON WHERE IDTAIKHOAN = " + DangNhap_Activity.taikhoan.getIDTAIKHOAN());
        hoaDonArrayList.clear();
        while (cursor.moveToNext())
        {
            hoaDonArrayList.add(new HoaDon(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            ));
        }
        adapter.notifyDataSetChanged();


        if (DangNhap_Activity.taikhoan.getIDTAIKHOAN() == -1)
        {
            txtthongbao.setText(" Bạn hãy đăng nhập để có thể xem hóa đơn !");
        }else if (hoaDonArrayList.isEmpty()){
            txtthongbao.setText(" Bạn chưa có hóa đơn !");
        }
    }
}