package com.example.tanhung_laptop.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class GopY_Fragment extends Fragment {
    View view;
    ImageButton ibtn_Exit;
    EditText edt_Tentaikhoan, edt_Sdt, edt_NoiDunggopy;
    Button btn_Gopy, btn_Thoat;
    CompositeDisposable compositeDisposable;
    API api;

    public GopY_Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = RetrofitClient.getInstance(Utils.BASE_URL).create(API.class);
        compositeDisposable =  new CompositeDisposable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gop_y_, container, false);
        Anhxa();
        return view;
    }

    private void Anhxa() {

        edt_Tentaikhoan = view.findViewById(R.id.edtTaikhoangopy);
        edt_Sdt = view.findViewById(R.id.edtSdtgopy);
        edt_NoiDunggopy = view.findViewById(R.id.edtNoidunggopy);
        btn_Gopy = view.findViewById(R.id.btnGopy);
        if(DangNhap_Activity.taikhoan.getIDTAIKHOAN()==-1){
            edt_Tentaikhoan.setText("Khách");
        }else {
            edt_Tentaikhoan.setText(DangNhap_Activity.taikhoan.getTENTAIKHOAN());
        }

        edt_Sdt.setText(String.valueOf(DangNhap_Activity.taikhoan.getSDT()));
        btn_Gopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_NoiDunggopy.getText().toString().isEmpty()||edt_Sdt.getText().toString().isEmpty()||edt_Tentaikhoan.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Không được để trống!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    compositeDisposable.add(api.gopY(edt_Tentaikhoan.getText().toString().trim(),edt_Sdt.getText().toString().trim(),
                                    edt_NoiDunggopy.getText().toString().trim())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        Toast.makeText(getContext(),messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }, throwable -> {
                                        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }));
                    edt_NoiDunggopy.setText("");
                }
            }
        });

        btn_Thoat = view.findViewById(R.id.btnThoatgopy);
        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });


    }
}