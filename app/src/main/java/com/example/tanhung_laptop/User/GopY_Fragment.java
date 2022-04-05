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


public class GopY_Fragment extends Fragment {
    View view;
    ImageButton ibtn_Exit;
    EditText edt_Tentaikhoan, edt_Sdt, edt_NoiDunggopy;
    Button btn_Gopy, btn_Thoat;
    public GopY_Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                    BatDau_activity.database.INSERT_GOPY(
                            edt_Tentaikhoan.getText().toString().trim(),
                            Integer.parseInt(edt_Sdt.getText().toString().trim()),
                            edt_NoiDunggopy.getText().toString().trim()
                    );
                    Toast.makeText(getActivity(), "Gửi góp ý thành công !", Toast.LENGTH_LONG).show();
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