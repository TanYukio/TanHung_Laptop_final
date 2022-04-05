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

import com.example.tanhung_laptop.Adapter.QL_GOPY_Adapter;
import com.example.tanhung_laptop.Models.GopY;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.User.BatDau_activity;
import com.example.tanhung_laptop.User.Chitietsanpham_Activity;

import java.util.ArrayList;

public class QLGopY_Fragment extends Fragment {
    View view;
    GridView gridviewgopY;
    ArrayList<GopY> gopYArrayList;
    QL_GOPY_Adapter adapter;

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
        anh_xa();
        gopYArrayList = new ArrayList<>();
        adapter = new QL_GOPY_Adapter(QLGopY_Fragment.this, R.layout.ql_gopy, gopYArrayList);
        gridviewgopY.setAdapter(adapter);
        registerForContextMenu(gridviewgopY);

        GetData();
        return  view;
    }

    private void GetData() {
        Cursor cursor = BatDau_activity.database.GetData("SELECT * FROM GOPY ");
        gopYArrayList.clear();
        while (cursor.moveToNext())
        {
            gopYArrayList.add(new GopY(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3)

            ));
        }
        adapter.notifyDataSetChanged();
    }

    private void anh_xa() {
        gridviewgopY = view.findViewById(R.id.gridviewgopY);
    }
}