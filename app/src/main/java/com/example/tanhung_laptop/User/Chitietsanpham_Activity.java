package com.example.tanhung_laptop.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.BinhLuanAdapter;
import com.example.tanhung_laptop.Adapter.LAPTOP_ADAPTER;
import com.example.tanhung_laptop.Models.BinhLuan;
import com.example.tanhung_laptop.Models.LAPTOP;
import com.example.tanhung_laptop.R;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Chitietsanpham_Activity extends AppCompatActivity {

    LAPTOP laptop;
    TextView name,price,content,noidung_ctsp;
    ImageView imgHinh,img_hethang;
    EditText editTextSL;
    Button btnaddcart,btn_GuiBl;
    ImageButton btn_quaylai;
    int id,idtk;
    NestedScrollView scrollV;
    RecyclerView recV_chatbox;
    ArrayList<BinhLuan> listBL;
    BinhLuanAdapter binhLuanAdapter;
    EditText edt_noidungbl_sanpham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",1);
        Anhxa();
        Sukien();
        GetDataSP();
        btn_quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Chitietsanpham_Activity.this, MainActivity.class));
            }
        });

        btnaddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                byte[] hinhAnh = byteArray.toByteArray();


                int SL = Integer.parseInt(editTextSL.getText().toString());

                laptop = LAPTOP_ADAPTER.laptopList.get(id);


                if(DangNhap_Activity.taikhoan.getIDTAIKHOAN() == -1)
                {
                    Toast.makeText(Chitietsanpham_Activity.this, "Bạn phải đăng nhập để mua hàng !", Toast.LENGTH_SHORT).show();
                }else if(  laptop.getSOLUONG() == 1 ){
                    Toast.makeText(Chitietsanpham_Activity.this, " Sản phẩm hiện đã hết hàng !  " , Toast.LENGTH_SHORT).show();

                }else if( SL > (laptop.getSOLUONG() - 1) ){
                    Toast.makeText(Chitietsanpham_Activity.this, "Hàng trong kho chỉ còn : " + (laptop.getSOLUONG()- 1) + " sản phẩm ", Toast.LENGTH_SHORT).show();

                }else if(  SL == 0 ){
                    Toast.makeText(Chitietsanpham_Activity.this, " Số lượng không hợp lệ !  " , Toast.LENGTH_SHORT).show();

                }
                else
                {
                    BatDau_activity.database.SPGH(
                            DangNhap_Activity.taikhoan.getIDTAIKHOAN(),
                            hinhAnh,
                            laptop.getIDLT(),
                            laptop.getTENLAPTOP(),
                            SL,
                            SL * laptop.getGIA()
                    );
                    Toast.makeText(getApplicationContext()," Đã thêm vào giỏ hàng !",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Chitietsanpham_Activity.this, MainActivity.class);
                    intent.putExtra("giohang", R.id.giohang);
                    startActivity(intent);
                }

            }
        });
    }
    private void Sukien() {
        edt_noidungbl_sanpham.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                scrollV.scrollTo(0,edt_noidungbl_sanpham.getScrollY());
            }
        });
        btn_GuiBl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DangNhap_Activity.taikhoan.getIDTAIKHOAN() == -1) {
                    Toast.makeText(Chitietsanpham_Activity.this, "Bạn chưa đăng nhập !", Toast.LENGTH_LONG).show();
                } else {
                    if (edt_noidungbl_sanpham.getText().length() > 0); {
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());
//                        if(idtk==2){
//                            sanPham = SanPhamAdapter.sanPhamList.get(id);
//                        }else
//                        {
//                            sanPham = TimKiemAdapter.sanPhamList.get(idtk);
//                        }
                        laptop = LAPTOP_ADAPTER.laptopList.get(id);
                        BatDau_activity.database.ThemBL(DangNhap_Activity.taikhoan.getIDTAIKHOAN(), laptop.getIDLT(),edt_noidungbl_sanpham.getText().toString(),currentDateandTime);
                        listBL.add(0, new BinhLuan(
                                DangNhap_Activity.taikhoan.getIDTAIKHOAN(),DangNhap_Activity.taikhoan.getHINHANH(),
                                edt_noidungbl_sanpham.getText().toString(),currentDateandTime
                        ));
                        Log.e("Tag",String.valueOf(listBL.size()));
                        binhLuanAdapter.notifyItemInserted(0);
                        edt_noidungbl_sanpham.setText("");
                        Toast.makeText(Chitietsanpham_Activity.this, " Bình luận thành công ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void Anhxa() {
        edt_noidungbl_sanpham = findViewById(R.id.edt_noidungbl_sanpham);
        noidung_ctsp = findViewById(R.id.noidung_ctsp);
        scrollV =(NestedScrollView) findViewById(R.id.scrollV);
        name = (TextView) findViewById(R.id.product_name_CT);
        price = (TextView) findViewById(R.id.product_price_CT);
        imgHinh = (ImageView) findViewById(R.id.product_image_CT);
        btnaddcart= (Button) findViewById(R.id.btnadd_addtocart_CT);
        editTextSL = (EditText) findViewById(R.id.product_SL_CT);
        btn_quaylai = (ImageButton) findViewById(R.id.btn_quaylai);
        btn_GuiBl = findViewById(R.id.btn_GuiBl);
        recV_chatbox = findViewById(R.id.rec_Binhluan_sanpham);
    }

    private void GetDataSP() {
        //get data
        laptop = LAPTOP_ADAPTER.laptopList.get(id);
        String ten = laptop.getTENLAPTOP();
//        String mota = sanPham.getMotaSP();
        name.setText(ten);
//        content.setText(mota);
        noidung_ctsp.setText(laptop.getMOTASP());
        price.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(laptop.getGIA()) + " VNĐ"));
        byte[] hinhAnh = laptop.getHINHANH();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
        imgHinh.setImageBitmap(bitmap);


    }
    @Override
    protected void onStart() {

        //        if(idtk==2){
//            laptop = LAPTOP_ADAPTER.laptopList.get(id);
//        }else
//        {
//            laptop = LAPTOP_ADAPTER.laptopList.get(idtk);
//        }
        laptop = LAPTOP_ADAPTER.laptopList.get(id);
        listBL = BatDau_activity.database.LayBinhLuan(laptop.getIDLT());

        binhLuanAdapter = new BinhLuanAdapter(listBL);
        recV_chatbox.setAdapter(binhLuanAdapter);
        recV_chatbox.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        super.onStart();
    }
}