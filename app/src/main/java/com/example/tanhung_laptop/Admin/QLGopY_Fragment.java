package com.example.tanhung_laptop.Admin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.QL_GOPY_Adapter;
import com.example.tanhung_laptop.Models.GopY;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;
import com.example.tanhung_laptop.User.BatDau_activity;
import com.example.tanhung_laptop.User.Chitietsanpham_Activity;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QLGopY_Fragment extends Fragment {
    View view;
    GridView gridviewgopY;
    ArrayList<GopY> gopYArrayList;
    QL_GOPY_Adapter adapter;
    CompositeDisposable compositeDisposable;
    API api;
    public QLGopY_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_q_l_gop_y_, container, false);
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();

        anh_xa();
        gopYArrayList = new ArrayList<>();
        adapter = new QL_GOPY_Adapter(QLGopY_Fragment.this, R.layout.ql_gopy, gopYArrayList);
        gridviewgopY.setAdapter(adapter);
        registerForContextMenu(gridviewgopY);

        GetData();
        return  view;
    }

    private void GetData() {
        compositeDisposable.add(api.layhetGopy()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        gopyModel -> {
                            gopYArrayList.clear();
                            if (gopyModel.isSuccess()) {
                                for (int i= 0;i<gopyModel.getResult().size();i++){
                                    gopYArrayList.add(gopyModel.getResult().get(i));
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }, throwable -> {
//                            Log.e("Lỗi", throwable.getMessage());
                            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
    }

    private void anh_xa() {
        gridviewgopY = view.findViewById(R.id.gridviewgopY);
    }
}