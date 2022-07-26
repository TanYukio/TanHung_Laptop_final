package com.example.tanhung_laptop.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;
import com.example.tanhung_laptop.ThanhToanActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import vn.momo.momo_partner.AppMoMoLib;
import vn.momo.momo_partner.MoMoParameterNameMap;

public class GioHangFragment extends Fragment {
    private View view;
    ListView Listview_SanPham;
    ArrayList<GioHang> sanPhamArrayList;
    GioHangAdapter adapter;
    Button btn_thanhtoan;
    TextView txtthongbao,tongthanhtien;
    double tong;
    CompositeDisposable compositeDisposable;
    API api;


    public GioHangFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();
        view = inflater.inflate(R.layout.fragment_gio_hang, container, false);
        AnhXa();
        Listview_SanPham = (ListView) view.findViewById(R.id.listview_danhsachsp_gohang);

        sanPhamArrayList = new ArrayList<>();
        adapter = new GioHangAdapter(GioHangFragment.this, R.layout.products_giohang, sanPhamArrayList);
        Listview_SanPham.setAdapter(adapter);
        registerForContextMenu(Listview_SanPham);

        GetData();

        return view;
    }

    private void ktGH(){
        if (DangNhap_Activity.taikhoan.getIDTAIKHOAN() == -1)
        {
            txtthongbao.setText(" Bạn hãy đăng nhập để có thể mua hàng !");
        } else if (sanPhamArrayList.isEmpty())
        {
            txtthongbao.setText(" Bạn chưa mua hàng !");
            btn_thanhtoan.setEnabled(false);
            btn_thanhtoan.setBackgroundResource(R.color.xam);
            tongthanhtien.setText("0");
        }
        else
        {
            Tongtien();
            btn_thanhtoan.setEnabled(true);
            btn_thanhtoan.setBackgroundResource(R.color.purple_500);
            btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   startActivity(new Intent(getActivity(), ThanhToanActivity.class));
                }
            });
        }
    }

    @Override
    public void onStart() {
        GetData();

        super.onStart();
    }
    private void Tongtien() {
        compositeDisposable.add(api.laytongtien(DangNhap_Activity.taikhoan.getIDTAIKHOAN())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        doubleModel -> {
                            if (doubleModel.isSuccess())
                            {
                                tong = doubleModel.getResult();
                                tongthanhtien.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(tong) + " VNĐ"));
                            }
                        }, throwable -> {
                            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
    }

    private void AnhXa() {

        txtthongbao = (TextView) view.findViewById(R.id.thongbaogiohang);
        tongthanhtien = (TextView) view.findViewById(R.id.tongthanhtien);
        btn_thanhtoan = (Button) view.findViewById(R.id.thanhtoan_giohang);
    }


    private void GetData() {
        //get data
        compositeDisposable.add(api.laygh(DangNhap_Activity.taikhoan.getIDTAIKHOAN())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        giohangModel -> {
                            sanPhamArrayList.clear();
                            if (giohangModel.isSuccess()) {
                                for (int i= 0;i<giohangModel.getResult().size();i++){
                                    sanPhamArrayList.add(giohangModel.getResult().get(i));
                                }
                            }
                            adapter.notifyDataSetChanged();
                            ktGH();
                        }, throwable -> {
                            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));

    }

    private void xoagh(int idlt)
    {
        compositeDisposable.add(api.xoagh(DangNhap_Activity.taikhoan.getIDTAIKHOAN(),idlt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        laptopModel -> {
                            GetData();
                        }, throwable -> {
                            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
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
                xoagh(gioHang.getIDLT());
                GetData();
                Tongtien();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}