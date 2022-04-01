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

import com.example.tanhung_laptop.Models.GioHang;
import com.example.tanhung_laptop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class GioHangAdapter extends BaseAdapter {

    private Fragment context;
    private int layout;
    public static List<GioHang> sanPhamGioHangList;
    int id;



    public GioHangAdapter(Fragment context, int layout, List<GioHang> sanPhamGioHangList) {
        this.context = context;
        this.layout = layout;
        this.sanPhamGioHangList = sanPhamGioHangList;
    }


    @Override
    public int getCount() {
        return sanPhamGioHangList.size();
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
        TextView txt_TenSP, txt_GiaSP, txt_SLSP,txt_count;
        ImageView img_HinhAnh;


    }


    @Override
    public View getView(int i, View view, ViewGroup parent) {

        ViewHolder holder;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txt_TenSP = (TextView) view.findViewById(R.id.textviewTenCustom);
            holder.txt_GiaSP = (TextView) view.findViewById(R.id.textviewTTCustom);
            holder.txt_SLSP = (TextView) view.findViewById(R.id.textviewSLCustom) ;
            holder.img_HinhAnh = (ImageView) view.findViewById(R.id.imageHinhCustom);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        GioHang gioHang = sanPhamGioHangList.get(i);


        // chuyen byte[] -> ve bitmap
        byte[] hinhAnh = gioHang.getHINHANH();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
        holder.img_HinhAnh.setImageBitmap(bitmap);

        holder.txt_TenSP.setText(gioHang.getTENSP());
        holder.txt_GiaSP.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(gioHang.getTONGTIEN())) + " VNĐ");
        holder.txt_SLSP.setText(String.valueOf(gioHang.getSOLUONG()) );
        id = gioHang.getIDGIOHANG();





        return view;
    }

}
