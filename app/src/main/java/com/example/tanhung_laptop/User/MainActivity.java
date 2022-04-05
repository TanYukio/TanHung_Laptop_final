package com.example.tanhung_laptop.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tanhung_laptop.Adapter.TimKiemAdapter;
import com.example.tanhung_laptop.Models.TAIKHOAN;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.TimKiem;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerlaout;
    NavigationView navigationeview_home;
    ImageView menu;
    Toolbar toolbar;
    EditText edt_timkiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        anh_xa();



        ActionBar();
        Intent intent = getIntent();
        int GioHangIntent = intent.getIntExtra("giohang", R.id.trangchu);
        if(GioHangIntent == R.id.giohang){
            navigationeview_home.setCheckedItem(GioHangIntent);
            replaceFragment(new GioHangFragment());
        }

    }

    private TAIKHOAN laythongtin(int idtk) {

            Cursor cursor = BatDau_activity.database.GetData("SELECT * FROM TAIKHOAN WHERE IDTAIKHOAN = " + idtk);
            while (cursor.moveToNext()) {
                return new TAIKHOAN(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6),
                        cursor.getString(7),
                        cursor.getBlob(8)
                );
            }
            return null;

    }

    private void anh_xa() {
        drawerlaout=findViewById(R.id.drawerlaout);
        navigationeview_home=findViewById(R.id.navigationeview_home);
        menu=findViewById(R.id.menu);
        toolbar = findViewById(R.id.toolbar);
        edt_timkiem = findViewById(R.id.edt_timkiem);
        edt_timkiem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                startActivity(new Intent(MainActivity.this, TimKiem.class));
            }
        });
        navigationeview_home.setNavigationItemSelectedListener(this);
        navigationeview_home.setCheckedItem(R.id.trangchu);
        // Drawer

        replaceFragment(new TrangChuFragment());
    }


    private void ActionBar(){
        navigationeview_home.bringToFront();
        navigationeview_home.setNavigationItemSelectedListener(this);
        navigationeview_home.setCheckedItem(R.id.trangchu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerlaout.isDrawerVisible(GravityCompat.END))
                    drawerlaout.closeDrawer(GravityCompat.END);
                else drawerlaout.openDrawer(GravityCompat.END);
            }
        });
    }
    @Override
    protected void onStart() {
        Menu menu = navigationeview_home.getMenu();
        if(DangNhap_Activity.taikhoan.getIDTAIKHOAN() == -1){
            menu.findItem(R.id.logout).setVisible(false);
        }else {
            menu.findItem(R.id.login).setVisible(false);
        }
        super.onStart();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.trangchu){
            startActivity(new Intent(this,MainActivity.class));

        }else if(id == R.id.apple){
            replaceFragment(new APPLE_Fragment());

        }else if(id == R.id.asus){
            replaceFragment(new ASUS_Fragment());

        }
        else if(id == R.id.dell){
            replaceFragment(new Dell_Fragment());

        }
        else if(id == R.id.hp){
            replaceFragment(new HP_Fragment());

        }
        else if(id == R.id.giohang){
            replaceFragment(new GioHangFragment());
        }
        else if(id == R.id.acer){
            replaceFragment(new ACER_Fragment());

        }
        else if(id == R.id.gopy){
            replaceFragment(new GopY_Fragment());

        }else if(id == R.id.donhang){
            if(DangNhap_Activity.taikhoan.getIDTAIKHOAN()== -1){
                Toast.makeText(this, " Vui lòng đăng nhập !", Toast.LENGTH_SHORT).show();
            }else
            {
                startActivity(new Intent(MainActivity.this, DonHang_Activity.class));
                finish();
            }

        }
        else if(id == R.id.user){
            if(DangNhap_Activity.taikhoan.getIDTAIKHOAN()== -1){
                Toast.makeText(this, " Vui lòng đăng nhập !", Toast.LENGTH_SHORT).show();
            }else
            {
                startActivity(new Intent(MainActivity.this, ThongTinNguoiDung_Activity.class));
                finish();
            }

        }
        else if(id == R.id.login){
            startActivity(new Intent(MainActivity.this, DangNhap_Activity.class));
            finish();

        }
        else if(id == R.id.logout){
            startActivity(new Intent(this,DangNhap_Activity.class));
            finish();

        }
        drawerlaout.closeDrawer(GravityCompat.END);
        return true;
    }
    private void  replaceFragment (Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }
}