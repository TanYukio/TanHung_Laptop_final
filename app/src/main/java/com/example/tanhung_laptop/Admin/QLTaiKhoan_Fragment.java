package com.example.tanhung_laptop.Admin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.TaiKhoanAdminAdapter;
import com.example.tanhung_laptop.Models.TAIKHOAN;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;
import com.example.tanhung_laptop.User.BatDau_activity;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QLTaiKhoan_Fragment extends Fragment {
    View view;
    API api;
    GridView gridviewQLTaiKhoan;
    CompositeDisposable compositeDisposable;
    ArrayList<TAIKHOAN> taiKhoanArrayList;
    TaiKhoanAdminAdapter adapter;
    public QLTaiKhoan_Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_q_l_tai_khoan_, container, false);
        gridviewQLTaiKhoan = (GridView) view.findViewById(R.id.gridviewQLTaiKhoan);
        taiKhoanArrayList = new ArrayList<>();
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();

        adapter = new TaiKhoanAdminAdapter(QLTaiKhoan_Fragment.this, R.layout.taikhoan_admin, taiKhoanArrayList);
        gridviewQLTaiKhoan.setAdapter(adapter);
        gridviewQLTaiKhoan.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), SuaTaiKhoan_Activity.class);
                intent.putExtra("id",i);

                startActivity(intent);

            }
        });
        registerForContextMenu(gridviewQLTaiKhoan);

        GetData();
        return view;
    }

    @Override
    public void onResume() {
        GetData();
        super.onResume();
    }

    private void GetData() {

        Log.e("GetData","");
        compositeDisposable.add(api.layhetTk()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        taiKhoanModel -> {
                            taiKhoanArrayList.clear();
                            if (taiKhoanModel.isSuccess()) {


                                for (int i= 0;i<taiKhoanModel.getResult().size();i++){
                                    taiKhoanArrayList.add(taiKhoanModel.getResult().get(i));
                                }
                                Toast.makeText(getContext(), "Thành công", Toast.LENGTH_LONG).show();


                            }
                            adapter.notifyDataSetChanged();
                        }, throwable -> {
                            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
//        Cursor cursor = BatDau_activity.database.GetData("SELECT * FROM TAIKHOAN ");
//        taiKhoanArrayList.clear();
//        while (cursor.moveToNext())
//        {
//            taiKhoanArrayList.add(new TAIKHOAN(
//                    cursor.getInt(0),
//                    cursor.getString(1),
//                    cursor.getString(2),
//                    cursor.getInt(3),
//                    cursor.getString(4),
//                    cursor.getString(5),
//                    cursor.getInt(6),
//                    cursor.getString(7),
//                    cursor.getString(8)
//            ));
//        }
//        adapter.notifyDataSetChanged();
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
                TAIKHOAN taiKhoan = TaiKhoanAdminAdapter.taiKhoanList.get(info.position);

                compositeDisposable.add(api.xoatk(taiKhoan.getIDTAIKHOAN())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                messageModel -> {
                                    GetData();
                                    Toast.makeText(getContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }, throwable -> {
                                    Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }));
//                BatDau_activity.database.DELETE_TAIKHOAN(
//                        taiKhoan.getIDTAIKHOAN()
//                );
//
//                Toast.makeText(getActivity(),"Xóa thành công",Toast.LENGTH_LONG).show();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}