package com.example.tanhung_laptop.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tanhung_laptop.Models.GioHang;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GioHangAdapter extends BaseAdapter {

    private Fragment context;
    private int layout;
    public static List<GioHang> sanPhamGioHangList;
    int id;
    CompositeDisposable compositeDisposable;



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



        holder.txt_TenSP.setText(gioHang.getTENLAPTOP());
        Log.e("Lỗi", "\n" +  gioHang.getTENLAPTOP()  );
        holder.txt_GiaSP.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(gioHang.getTONGTIEN())) + " VNĐ");
        holder.txt_SLSP.setText(String.valueOf(gioHang.getSOLUONG()) );

        API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();
        compositeDisposable.add(api.layhinhanh(gioHang.getIDLT())
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
                            Toast.makeText(context.getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));




        return view;
    }

}
