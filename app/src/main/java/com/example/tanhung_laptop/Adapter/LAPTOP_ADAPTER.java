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

import androidx.fragment.app.Fragment;

import com.example.tanhung_laptop.Models.LAPTOP;
import com.example.tanhung_laptop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class LAPTOP_ADAPTER extends BaseAdapter {

    private Fragment context;
    private int layout;
    public static List<LAPTOP> laptopList;

    public LAPTOP_ADAPTER(Fragment context, int layout, List<LAPTOP> laptopList) {
        this.context = context;
        this.layout = layout;
        this.laptopList = laptopList;
    }

    @Override
    public int getCount() {
        return laptopList.size();
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
        TextView txt_TenSP, txt_GiaSP;
        ImageView img_HinhAnh;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txt_TenSP = view.findViewById(R.id.product_name);
            holder.txt_GiaSP = (TextView) view.findViewById(R.id.product_price);
            holder.img_HinhAnh = (ImageView) view.findViewById(R.id.product_image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        LAPTOP laptop = laptopList.get(i);
        String gia = String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(laptop.getGIA())) + " VNĐ";
        holder.txt_TenSP.setText(laptop.getTENLAPTOP());
        holder.txt_GiaSP.setText(gia);

        // chuyen byte[] -> ve bitmap
        byte[] hinhAnh = laptop.getHINHANH();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
        holder.img_HinhAnh.setImageBitmap(bitmap);

        return view;
    }
}
