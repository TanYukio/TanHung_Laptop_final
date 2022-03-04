package com.example.tanhung_laptop;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.tanhung_laptop.Adapter.LAPTOP_ADAPTER;
import com.example.tanhung_laptop.Models.LAPTOP;

import java.util.ArrayList;

public class TrangChuFragment extends Fragment {

    View view;
    GridView gridviewSanPham;
    ArrayList<LAPTOP> laptopArrayList;
    LAPTOP_ADAPTER adapter;
    public TrangChuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        anh_xa();
        laptopArrayList = new ArrayList<>();
        adapter = new LAPTOP_ADAPTER(TrangChuFragment.this, R.layout.laptop_layout, laptopArrayList);
        gridviewSanPham.setAdapter(adapter);
//        gridviewSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(), Products_information_activity.class);
//
//
//                intent.putExtra("id",i);
//                startActivity(intent);
//
//            }
//        });
        registerForContextMenu(gridviewSanPham);

        GetData();
        return  view;
    }

    private void GetData() {
        Cursor cursor = BatDau_activity.database.GetData("SELECT * FROM LAPTOP WHERE LTMOI = 1");
        laptopArrayList.clear();
        while (cursor.moveToNext())
        {
            laptopArrayList.add(new LAPTOP(
                    cursor.getInt(0),
                    cursor.getBlob(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7)
            ));
        }
        adapter.notifyDataSetChanged();
    }

    private void anh_xa() {
        gridviewSanPham = view.findViewById(R.id.gridviewSanPham);
    }
}