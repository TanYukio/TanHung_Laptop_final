package com.example.tanhung_laptop.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tanhung_laptop.Models.GopY;
import com.example.tanhung_laptop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class QL_GOPY_Adapter extends BaseAdapter {

    private Fragment context;
    private int layout;
    public static List<GopY> gopYList;

    public QL_GOPY_Adapter(Fragment context, int layout,List<GopY> gopYList) {
        this.context = context;
        this.layout = layout;
        this.gopYList= gopYList;
    }

    @Override
    public int getCount() {
        return gopYList.size();
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
        TextView ten_gopy, sdt_gopy,noidung_gopy;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        QL_GOPY_Adapter.ViewHolder holder;

        if (view == null){
            holder = new QL_GOPY_Adapter.ViewHolder();
            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.ten_gopy = view.findViewById(R.id.ten_gopy);
            holder.sdt_gopy = view.findViewById(R.id.sdt_gopy);
            holder.noidung_gopy = view.findViewById(R.id.noidung_gopy);

            view.setTag(holder);
        } else {
            holder = (QL_GOPY_Adapter.ViewHolder) view.getTag();
        }

        GopY gopY = gopYList.get(i);
        holder.ten_gopy.setText(gopY.getTENTAIKHOAN());
        holder.sdt_gopy.setText(String.valueOf(gopY.getSDT()));
        holder.noidung_gopy.setText(gopY.getNOIDUNG());

        return view;
    }
}

