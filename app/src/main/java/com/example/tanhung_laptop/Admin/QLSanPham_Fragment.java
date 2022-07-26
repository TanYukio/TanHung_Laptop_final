package com.example.tanhung_laptop.Admin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.CategoryAdapter;
import com.example.tanhung_laptop.Adapter.LaptopAdminAdapter;
import com.example.tanhung_laptop.Models.Category;
import com.example.tanhung_laptop.Models.LAPTOP;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;
import com.example.tanhung_laptop.User.BatDau_activity;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QLSanPham_Fragment extends Fragment {

    View view;
    ArrayList<Category> listCategory;
    Spinner spnTheloai;
    GridView gridView_SanPham;
    ArrayList<LAPTOP> laptopArrayList;
    LaptopAdminAdapter adapter;
    CategoryAdapter categoryAdapter;
    int IDNSX;
    API api;
    CompositeDisposable compositeDisposable;

    public QLSanPham_Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_q_l_san_pham_, container, false);
        gridView_SanPham = (GridView) view.findViewById(R.id.gridviewQLSanPham);
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable = new CompositeDisposable();
        Anhxa();
        listCategory = getListCategory();
        categoryAdapter = new CategoryAdapter(getActivity(), R.layout.item_select, listCategory);
        spnTheloai.setAdapter(categoryAdapter);
        IDNSX = 0;
        spnTheloai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                IDNSX = categoryAdapter.getItem(position).getIDcategory();
                GetData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        laptopArrayList = new ArrayList<>();
        adapter = new LaptopAdminAdapter(QLSanPham_Fragment.this, R.layout.product_sanpham_admin, laptopArrayList);
        gridView_SanPham.setAdapter(adapter);
        gridView_SanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), SuaSanPham_Activity.class);
                intent.putExtra("id", i);

                startActivity(intent);

            }
        });
        registerForContextMenu(gridView_SanPham);

        GetData();
        return view;
    }

    private void Anhxa() {
        spnTheloai = view.findViewById(R.id.spnAddTheloai);
    }

    @Override
    public void onStart() {
        GetData();
        super.onStart();
    }

    private void GetData() {
        if (IDNSX == 0) {
            compositeDisposable.add(api.layhetSp()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            laptopModel -> {
                                laptopArrayList.clear();
                                if (laptopModel.isSuccess()) {
                                    for (int i = 0; i < laptopModel.getResult().size(); i++) {
                                        laptopArrayList.add(laptopModel.getResult().get(i));
                                    }
                                } else {
                                    Toast.makeText(getContext(), laptopModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                adapter.notifyDataSetChanged();
                            }, throwable -> {
                                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }));

        } else {
            compositeDisposable.add(api.laySpnsx(IDNSX)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            laptopModel -> {
                                laptopArrayList.clear();
                                if (laptopModel.isSuccess()) {
                                    for (int i = 0; i < laptopModel.getResult().size(); i++) {
                                        laptopArrayList.add(laptopModel.getResult().get(i));
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }, throwable -> {
                                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }));
        }
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
        switch (item.getItemId()) {
            case R.id.menu_delete_item:
                LAPTOP laptop = LaptopAdminAdapter.laptopList.get(info.position);

                compositeDisposable.add(api.xoalt(laptop.getIDLT())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                messageModel -> {
                                    GetData();
                                    Toast.makeText(getContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }, throwable -> {
                                    Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private ArrayList<Category> getListCategory() {
        ArrayList<Category> list = new ArrayList<>();

        list.add(new Category("Apple", 1));
        list.add(new Category("ASUS", 2));
        list.add(new Category("Dell", 3));
        list.add(new Category("HP", 4));
        list.add(new Category("Acer", 5));

        return list;
    }

    @Override
    public void onDestroyView() {
        compositeDisposable.clear();
        super.onDestroyView();
    }
}