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

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GioHangFragment extends Fragment {
    private View view;
    ListView Listview_SanPham;
    ArrayList<GioHang> sanPhamArrayList;
    GioHangAdapter adapter;
    Button btn_thanhtoan;
    TextView txtthongbao,tongthanhtien;
    double tong;
    String thoigian;
    int idcthd = 0;
    CompositeDisposable compositeDisposable;

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
                    showdialog();
                }
            });
        }
    }

    @Override
    public void onStart() {
        GetData();

        super.onStart();
    }
    private void themhd(EditText ghichu,EditText diachi)
    {
        API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();
        compositeDisposable.add(api.themhd(DangNhap_Activity.taikhoan.getIDTAIKHOAN(),thoigian,
                        tong,ghichu.getText().toString(),diachi.getText().toString()
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        laptopModel -> {
                            for (int position = 0; position<GioHangAdapter.sanPhamGioHangList.size();position++)
                            {
                                GioHang themhoadon = GioHangAdapter.sanPhamGioHangList.get(position);
                                Log.e("idlt",themhoadon.getIDLT() + " , " + themhoadon.getTENLAPTOP());
                                themcthd(themhoadon.getIDLT(),themhoadon.getTENLAPTOP(),themhoadon.getSOLUONG(),themhoadon.getTONGTIEN());

                            }
                            xoahetgh();
                        }, throwable -> {
                            Log.e("Lỗi them hd", throwable.getMessage());
                            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
    }
    private void Tongtien() {


        API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();
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
                            Log.e("Lỗi lay tong tien", throwable.getMessage());
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
        API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();
        compositeDisposable.add(api.laygh(DangNhap_Activity.taikhoan.getIDTAIKHOAN())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        giohangModel -> {
                            sanPhamArrayList.clear();
                            if (giohangModel.isSuccess()) {
//                                Log.e("1",laptopModel.getResult().get(0).getTENLAPTOP());
                                for (int i= 0;i<giohangModel.getResult().size();i++){
                                    sanPhamArrayList.add(giohangModel.getResult().get(i));
                                }
                                Toast.makeText(getContext(), giohangModel.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            adapter.notifyDataSetChanged();
                            Log.e("Getdata", Calendar.getInstance().getTime() + "Check" + giohangModel.getResult().size());
                            ktGH();
                        }, throwable -> {
                            Log.e("Lỗi loi lay du lieu", throwable.getMessage());
                            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));

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
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                thoigian = simpleDateFormat.format(Calendar.getInstance().getTime());
                themhd(ghichu,diachi);
                startActivity(new Intent(getActivity(), MainActivity.class));

            }
        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
    private void themcthd(int idlt, String tenlaptop, int soluong, int thanhtien)
    {
        API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();
        Log.e("time", thoigian);
        compositeDisposable.add(api.themcthd(DangNhap_Activity.taikhoan.getIDTAIKHOAN(),thoigian,idlt,
                        tenlaptop,soluong,thanhtien
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        laptopModel -> {

                        }, throwable -> {
                            Log.e("Lỗi themcthd", throwable.getMessage());
                            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
    }
    private void xoahetgh()
    {
        API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();

        compositeDisposable.add(api.xoahetgh(DangNhap_Activity.taikhoan.getIDTAIKHOAN())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        laptopModel -> {
                            GetData();
                        }, throwable -> {
                            Log.e("Lỗi xoa het", throwable.getMessage());
                            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
    }
    private void xoagh(int idlt)
    {
        API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();

        compositeDisposable.add(api.xoagh(DangNhap_Activity.taikhoan.getIDTAIKHOAN(),idlt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        laptopModel -> {
                            GetData();
                        }, throwable -> {
                            Log.e("Lỗi xoa 1", throwable.getMessage());
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
//                BatDau_activity.database.DELETE_DOAN(
//                        gioHang.getIDSP(),
//                        gioHang.getIDTK()
//                );
                xoagh(gioHang.getIDLT());
//                Toast.makeText(getActivity(),"Xóa thành công",Toast.LENGTH_LONG).show();
                GetData();
                Tongtien();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}