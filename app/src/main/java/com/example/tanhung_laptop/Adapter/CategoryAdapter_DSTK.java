package com.example.tanhung_laptop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tanhung_laptop.Models.Category;
import com.example.tanhung_laptop.Models.DSTK;
import com.example.tanhung_laptop.R;

import java.util.List;

public class CategoryAdapter_DSTK extends ArrayAdapter<DSTK> {
    public CategoryAdapter_DSTK(@NonNull Context context, int resource, @NonNull List<DSTK> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select, parent, false);
        TextView txt_Selected = convertView.findViewById(R.id.txtSelected);

        DSTK DSTK = this.getItem(position);
        if (DSTK != null){
            txt_Selected.setText(DSTK.getTENTAIKHOAN());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        TextView txt_Category = convertView.findViewById(R.id.txtCategory);

        DSTK DSTK = this.getItem(position);
        if (DSTK != null){
            txt_Category.setText(DSTK.getTENTAIKHOAN());
        }
        return convertView;
    }
}
