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
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;
import com.example.tanhung_laptop.User.BatDau_activity;
import com.example.tanhung_laptop.User.Chitietsanpham_Activity;

import java.util.ArrayList;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TimKiem extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    EditText edt_tk;
    ListView listview_tk;
    ImageButton img_search_Tk , img_voice_Tk;
    ArrayList<LAPTOP> laptopArrayList;
    TimKiemAdapter adapter;
    CompositeDisposable compositeDisposable;
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
//                GetDataALL();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                GetData(edt_tk.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        img_voice_Tk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
    }
    private MenuItem speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi speak something");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if (resultCode == RESULT_OK && null != data){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edt_tk.setText(result.get(0));
                }
                break;
            }
        }
    }

    private void GetData(String ten) {
        //get data
        API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();
        compositeDisposable.add(api.TimKiem(ten)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        laptopModel -> {
                            laptopArrayList.clear();
                            if (laptopModel.isSuccess()) {


                                for (int i= 0;i<laptopModel.getResult().size();i++){
                                    laptopArrayList.add(laptopModel.getResult().get(i));
                                }

                            }
                            adapter.notifyDataSetChanged();
//                            Toast.makeText(TimKiem.this, laptopModel.getMessage(), Toast.LENGTH_LONG).show();
                        }, throwable -> {
                            Toast.makeText(TimKiem.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));


    }
    private void GetDataALL() {
        API api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();
        compositeDisposable.add(api.layhetSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        laptopModel -> {
                            laptopArrayList.clear();
                            if (laptopModel.isSuccess()) {
                                for (int i= 0;i<laptopModel.getResult().size();i++){
                                    laptopArrayList.add(laptopModel.getResult().get(i));
                                }

                            }
                            adapter.notifyDataSetChanged();
//                            Toast.makeText(TimKiem.this, laptopModel.getMessage(), Toast.LENGTH_LONG).show();
                        }, throwable -> {
                            Toast.makeText(TimKiem.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
    }

    private void Anhxa() {
        img_voice_Tk = findViewById(R.id.img_voice_Tk);
        edt_tk = findViewById(R.id.edt_tk);
        img_search_Tk = findViewById(R.id.img_search_Tk);
    }

}