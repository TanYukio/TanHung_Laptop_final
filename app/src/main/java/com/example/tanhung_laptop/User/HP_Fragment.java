package com.example.tanhung_laptop.User;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.LAPTOP_ADAPTER;
import com.example.tanhung_laptop.Models.LAPTOP;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HP_Fragment extends Fragment {

    View view;
    GridView gridviewSanPham;
    ArrayList<LAPTOP> laptopArrayList;
    LAPTOP_ADAPTER adapter;
    TextView tieude;
    CompositeDisposable compositeDisposable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        anh_xa();
        laptopArrayList = new ArrayList<>();
        adapter = new LAPTOP_ADAPTER(HP_Fragment.this, R.layout.laptop_layout, laptopArrayList);
        gridviewSanPham.setAdapter(adapter);
        gridviewSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), Chitietsanpham_Activity.class);


                intent.putExtra("id",i);
                startActivity(intent);

            }
        });
        registerForContextMenu(gridviewSanPham);

        GetData();
        return  view;
    }

    private void GetData() {
        API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();
        compositeDisposable.add(api.laySpnsx(4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        laptopModel -> {
                            if (laptopModel.isSuccess()) {
                                Log.e("1",laptopModel.getResult().get(0).getTENLAPTOP());

                                laptopArrayList.clear();
                                for (int i= 0;i<laptopModel.getResult().size();i++){
                                    laptopArrayList.add(laptopModel.getResult().get(i));
                                }
//                                Toast.makeText(getContext(), "Thành công", Toast.LENGTH_LONG).show();
//                                Log.e("đâ", laptopArrayList.size() + "");
                                adapter.notifyDataSetChanged();
                            }
                        }, throwable -> {
//                            Log.e("Lỗi", throwable.getMessage());
                            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
//        Cursor cursor = BatDau_activity.database.GetData("SELECT * FROM LAPTOP WHERE IDNSX = 4");
//        laptopArrayList.clear();
////        while (cursor.moveToNext())
////        {
////            laptopArrayList.add(new LAPTOP(
////                    cursor.getInt(0),
////                    cursor.getBlob(1),
////                    cursor.getString(2),
////                    cursor.getInt(3),
////                    cursor.getInt(4),
////                    cursor.getString(5),
////                    cursor.getInt(6),
////                    cursor.getInt(7)
////            ));
////        }
//        adapter.notifyDataSetChanged();
    }

    private void anh_xa() {
        tieude = view.findViewById(R.id.tieude);
        tieude.setText("Sản phẩm HP");
        gridviewSanPham = view.findViewById(R.id.gridviewSanPham);
    }
}