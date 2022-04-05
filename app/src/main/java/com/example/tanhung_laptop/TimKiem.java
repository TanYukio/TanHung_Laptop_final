package com.example.tanhung_laptop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.TimKiemAdapter;
import com.example.tanhung_laptop.Models.LAPTOP;
import com.example.tanhung_laptop.User.BatDau_activity;
import com.example.tanhung_laptop.User.Chitietsanpham_Activity;

import java.util.ArrayList;
import java.util.Locale;

public class TimKiem extends AppCompatActivity {
    EditText edt_tk;
    ListView listview_tk;
    ImageButton img_search_Tk;
    ArrayList<LAPTOP> laptopArrayList;
    TimKiemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        listview_tk = findViewById(R.id.listview_tk);
        laptopArrayList = new ArrayList<>();
        Anhxa();
        adapter = new TimKiemAdapter(TimKiem.this, R.layout.timkiem, laptopArrayList);
        listview_tk.setAdapter(adapter);
        listview_tk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TimKiem.this, Chitietsanpham_Activity.class);
                intent.putExtra("idtk",i);
                startActivity(intent);

            }
        });
        edt_tk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                GetDataALL();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                GetData(edt_tk.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void GetData(String ten) {
        //get data
        Cursor cursor = BatDau_activity.database.GetData("SELECT * FROM LAPTOP WHERE TENLAPTOP LIKE '%" + ten +"%'" );
        laptopArrayList.clear();
        while (cursor.moveToNext())
        {
            laptopArrayList.add(new LAPTOP(
                    cursor.getInt(0),
                    cursor.getBlob(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7)
            ));
        }
        adapter.notifyDataSetChanged();
    }
    private void GetDataALL() {
        //get data
        Cursor cursor = BatDau_activity.database.GetData("SELECT * FROM LAPTOP ");
        laptopArrayList.clear();
        while (cursor.moveToNext())
        {
            laptopArrayList.add(new LAPTOP(
                    cursor.getInt(0),
                    cursor.getBlob(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7)
            ));
        }
        adapter.notifyDataSetChanged();
    }

    private void Anhxa() {
        edt_tk = findViewById(R.id.edt_tk);
        img_search_Tk = findViewById(R.id.img_search_Tk);
    }

}