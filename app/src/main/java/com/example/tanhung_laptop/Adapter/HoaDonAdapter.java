package com.example.tanhung_laptop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tanhung_laptop.Models.HoaDon;
import com.example.tanhung_laptop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HoaDonAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    public static List<HoaDon> ListHoaDon;
    int id,idcthd;



    public HoaDonAdapter(Context context, int layout, List<HoaDon> ListHoaDon) {
        this.context = context;
        this.layout = layout;
        this.ListHoaDon = ListHoaDon;
    }


    @Override
    public int getCount() {
        return ListHoaDon.size();
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
        TextView txtTongTien,txtdiachi,txtghichu;
        ListView listView;
    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {

        ViewHolder holder;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtTongTien = (TextView) view.findViewById(R.id.textviewTongTien_lichsu);
            holder.txtdiachi = (TextView) view.findViewById(R.id.textviewdc_lichsu);
            holder.txtghichu = (TextView) view.findViewById(R.id.textviewgc_lichsu);
            holder.listView = (ListView) view.findViewById(R.id.listview_danhsachhoadon_lichsu);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        HoaDon hoaDon = ListHoaDon.get(i);


        holder.txtTongTien.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(hoaDon.getTONGTIEN())) + " VNĐ");
        holder.txtdiachi.setText("Địa chỉ : " +  hoaDon.getDIACHI());
        holder.txtghichu.setText("Ghi chú : " + hoaDon.getGHICHU());
        id = hoaDon.getIDHOADON();
        idcthd = hoaDon.getIDCTHOADON();





        return view;
    }

}
