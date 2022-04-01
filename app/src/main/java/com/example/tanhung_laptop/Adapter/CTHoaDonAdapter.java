package com.example.tanhung_laptop.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanhung_laptop.User.BatDau_activity;
import com.example.tanhung_laptop.Models.CTHoaDon;
import com.example.tanhung_laptop.Models.LAPTOP;
import com.example.tanhung_laptop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CTHoaDonAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    public static List<CTHoaDon> ListCTHoaDon;
    int id;



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

        holder.txtTenSanPham.setText(cthoaDon.getTENSANPHAM());
        holder.txtsoluong.setText("Số lượng : " + String.valueOf(cthoaDon.getSOLUONG()));
        holder.txtthanhtien.setText("Thành tiền : " + String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(cthoaDon.getTHANHTIEN())) + " VNĐ");
        id = cthoaDon.getIDCTHOADON();

        LAPTOP sanPham = BatDau_activity.database.laptop(cthoaDon.getIDSANPHAM());
        // chuyen byte[] -> ve bitmap
        byte[] hinhAnh = sanPham.getHINHANH();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
        holder.img_HinhAnh.setImageBitmap(bitmap);




        return view;
    }

}
