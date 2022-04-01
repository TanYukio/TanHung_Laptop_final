package com.example.tanhung_laptop.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.GioHangAdapter;
import com.example.tanhung_laptop.Models.GioHang;
import com.example.tanhung_laptop.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GioHangFragment extends Fragment {
    private View view;
    ListView Listview_SanPham;
    ArrayList<GioHang> sanPhamArrayList;
    GioHangAdapter adapter;
    Button btn_thanhtoan;
    TextView txtthongbao,tongthanhtien;
    int tong;
    int idcthd = 0;

    public GioHangFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gio_hang, container, false);
        AnhXa();
        Listview_SanPham = (ListView) view.findViewById(R.id.listview_danhsachsp_gohang);

        sanPhamArrayList = new ArrayList<>();
        adapter = new GioHangAdapter(GioHangFragment.this, R.layout.products_giohang, sanPhamArrayList);
        Listview_SanPham.setAdapter(adapter);
        registerForContextMenu(Listview_SanPham);

        GetData();

        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog();
            }
        });






        return view;
    }

    @Override
    public void onStart() {
        Tongtien();

        super.onStart();
    }

    private void Tongtien() {
        Cursor cursor = BatDau_activity.database.GetData("SELECT SUM ( TONGTIEN ) FROM GIOHANG WHERE IDTK = "
                + DangNhap_Activity.taikhoan.getIDTAIKHOAN());
        cursor.moveToNext();
        tong = cursor.getInt(0);
        tongthanhtien.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(tong) + " VNĐ"));
    }

    private void AnhXa() {

        txtthongbao = (TextView) view.findViewById(R.id.thongbaogiohang);
        tongthanhtien = (TextView) view.findViewById(R.id.tongthanhtien);

        btn_thanhtoan = (Button) view.findViewById(R.id.thanhtoan_giohang);


    }

    private void GetData() {
        //get data

        Cursor cursor = BatDau_activity.database.GetData("SELECT * FROM GIOHANG WHERE IDTK = " + DangNhap_Activity.taikhoan.getIDTAIKHOAN());
        sanPhamArrayList.clear();
        while (cursor.moveToNext())
        {
            sanPhamArrayList.add(new GioHang(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getBlob(6)
            ));
        }
        adapter.notifyDataSetChanged();


        if (DangNhap_Activity.taikhoan.getIDTAIKHOAN() == -1)
        {
            txtthongbao.setText(" Bạn hãy đăng nhập để có thể mua hàng !");
        }else if (sanPhamArrayList.isEmpty()){
            txtthongbao.setText(" Bạn chưa mua hàng !");
        }
    }
    private void showdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_thanhtoan,null);
        final EditText diachi = view.findViewById(R.id.diachi_thanhtoan);
        final EditText ghichu= view.findViewById(R.id.ghichu_thanhtoan);
        diachi.setText(DangNhap_Activity.taikhoan.getDIACHI());
        builder.setView(view);
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(BatDau_activity.database.HoaDonChuaCoTrongHD()){
                    idcthd = 1;
                }
                else {

                    Cursor cursor = BatDau_activity.database.GetData("SELECT IDCTHOADON FROM CHITIETHOADON ORDER BY IDCTHOADON DESC");
                    cursor.moveToNext();
                    idcthd = cursor.getInt(0) + 1;
                }



                for (int position = 0; position<GioHangAdapter.sanPhamGioHangList.size();position++)
                {
                    GioHang themhoadon = GioHangAdapter.sanPhamGioHangList.get(position);
                    BatDau_activity.database.INSERT_CTHOADON(idcthd, themhoadon.getIDTK(), themhoadon.getIDSP(), themhoadon.getTENSP(),
                            themhoadon.getSOLUONG(), themhoadon.getTONGTIEN());
                    BatDau_activity.database.UPDATE_SOLUONG(themhoadon.getIDSP(),themhoadon.getSOLUONG());

                }
                BatDau_activity.database.INSERT_HOADON(tong,idcthd,diachi.getText().toString(),ghichu.getText().toString(),DangNhap_Activity.taikhoan.getIDTAIKHOAN());
                BatDau_activity.database.DELETE_GIOHANG(DangNhap_Activity.taikhoan.getIDTAIKHOAN());
                GetData();
                Tongtien();
                Toast.makeText(getActivity(),"Thanh toán thành công",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(), MainActivity.class));

            }
        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_content, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId())
        {
            case R.id.menu_delete_item:

                GioHang gioHang = GioHangAdapter.sanPhamGioHangList.get(info.position);
                BatDau_activity.database.DELETE_DOAN(
                        gioHang.getIDSP(),
                        gioHang.getIDTK()
                );

                Toast.makeText(getActivity(),"Xóa thành công",Toast.LENGTH_LONG).show();
                GetData();
                Tongtien();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}