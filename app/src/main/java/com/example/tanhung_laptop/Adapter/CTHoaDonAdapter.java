package com.example.tanhung_laptop.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;
import com.example.tanhung_laptop.User.BatDau_activity;
import com.example.tanhung_laptop.Models.CTHoaDon;
import com.example.tanhung_laptop.Models.LAPTOP;
import com.example.tanhung_laptop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CTHoaDonAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    public static List<CTHoaDon> ListCTHoaDon;
    int id;
    CompositeDisposable compositeDisposable;



    public CTHoaDonAdapter(Context context, int layout, List<CTHoaDon> ListCTHoaDon) {
        this.context = context;
        this.layout = layout;
        this.ListCTHoaDon = ListCTHoaDon;
    }


    @Override
    public int getCount() {
        return ListCTHoaDon.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder{
        TextView txtTenSanPham,txtsoluong,txtthanhtien;
        ImageView img_HinhAnh;

    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {

        ViewHolder holder;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtTenSanPham = (TextView) view.findViewById(R.id.textviewTen_CTlichsu);
            holder.txtsoluong = (TextView) view.findViewById(R.id.textviewsoluong_CTlichsu);
            holder.txtthanhtien = (TextView) view.findViewById(R.id.textviewthanhtien_CTlichsu);
            holder.img_HinhAnh = (ImageView) view.findViewById(R.id.imageHinhChiTietLichSu);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        CTHoaDon cthoaDon = ListCTHoaDon.get(i);

        holder.txtTenSanPham.setText(cthoaDon.getTENLAPTOP());
        holder.txtsoluong.setText("Số lượng : " + String.valueOf(cthoaDon.getSOLUONG()));
        holder.txtthanhtien.setText("Thành tiền : " + String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(cthoaDon.getTHANHTIEN())) + " VNĐ");

        API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();
        compositeDisposable.add(api.layhinhanh(cthoaDon.getIDLT())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        stringModel -> {
                            if (stringModel.isSuccess()) {
                                byte[] decodedString = Base64.decode(stringModel.getResult(), Base64.DEFAULT);
                                Bitmap imgBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                holder.img_HinhAnh.setImageBitmap(imgBitMap);
                            }
//                            Toast.makeText(getContext(),messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }, throwable -> {
                            Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
//        LAPTOP sanPham = BatDau_activity.database.laptop(cthoaDon.getIDSANPHAM());
//        // chuyen byte[] -> ve bitmap

//        byte[] hinhAnh = sanPham.getHINHANH();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
//        holder.img_HinhAnh.setImageBitmap(bitmap);




        return view;
    }

}
