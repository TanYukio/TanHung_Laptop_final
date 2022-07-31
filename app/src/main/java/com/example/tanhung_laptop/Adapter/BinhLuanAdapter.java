package com.example.tanhung_laptop.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tanhung_laptop.Models.BinhLuan;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BinhLuanAdapter extends RecyclerView.Adapter<BinhLuanAdapter.viewholder> {

    ArrayList<BinhLuan> listBL;
    CompositeDisposable compositeDisposable;
    public BinhLuanAdapter() {

    }
    public BinhLuanAdapter(ArrayList<BinhLuan> listBL) {
        this.listBL = listBL;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.binhluan,parent,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        BinhLuan binhLuan = listBL.get(holder.getAdapterPosition());
        byte[] decodedString = Base64.decode(binhLuan.getHINHANH(), Base64.DEFAULT);
        Bitmap imgBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.img_Hinh_binhluan.setImageBitmap(imgBitMap);

//        Bitmap bitmap = BitmapFactory.decodeByteArray(binhLuan.getHinhBL(),0,binhLuan.getHinhBL().length);
//        holder.img_Hinh_binhluan.setImageBitmap(bitmap);
        API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();
        compositeDisposable.add(api.laytentk(binhLuan.getIDTAIKHOAN())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        stringModel -> {
                            if (stringModel.isSuccess()) {
                                holder.txt_NoiDung_binhluan.setText(binhLuan.getNOIDUNG());
                                holder.txtV_ThoiGian_binhluan.setText(String.valueOf(binhLuan.getTHOIGIAN()));
                                holder.txtV_ten_chatbox.setText(stringModel.getResult());
                            }
//                            Toast.makeText(getContext(),messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }, throwable -> {
                            Toast.makeText(holder.itemView.getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));


    }

    @Override
    public int getItemCount() {
        if (listBL!=null){
            return listBL.size();
        }
        return 0;
    }

    public class viewholder extends RecyclerView.ViewHolder{
        CircleImageView img_Hinh_binhluan;
        TextView txt_NoiDung_binhluan,txtV_ThoiGian_binhluan,txtV_ten_chatbox;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            img_Hinh_binhluan = itemView.findViewById(R.id.img_Hinh_chatbox);
            txt_NoiDung_binhluan = itemView.findViewById(R.id.txt_NoiDung_chatbox);
            txtV_ThoiGian_binhluan = itemView.findViewById(R.id.txtV_ThoiGian_chatbox);
            txtV_ten_chatbox = itemView.findViewById(R.id.txtV_ten_chatbox);
        }
    }
}
