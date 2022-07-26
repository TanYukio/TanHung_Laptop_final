package com.example.tanhung_laptop.Admin;

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

import com.example.tanhung_laptop.Models.TAIKHOAN;
import com.example.tanhung_laptop.R;
import com.example.tanhung_laptop.Retrofit.API;
import com.example.tanhung_laptop.Retrofit.RetrofitClient;
import com.example.tanhung_laptop.Retrofit.Utils;
import com.example.tanhung_laptop.TimKiem;
import com.example.tanhung_laptop.User.BatDau_activity;
import com.example.tanhung_laptop.User.DangNhap_Activity;
import com.example.tanhung_laptop.User.MainActivity;
import com.google.android.material.navigation.NavigationView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeAdmin_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Drawer
    DrawerLayout drawerlaout;
    NavigationView navigationeview_home;
    ImageView menu;
    Toolbar toolbar;
    EditText edt_timkiem;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        anh_xa();
        ActionBar();

    }

    private void anh_xa() {
        drawerlaout=findViewById(R.id.drawerlaout_admin);
        navigationeview_home=findViewById(R.id.navigationeview_home);
        menu=findViewById(R.id.menu);
        toolbar = findViewById(R.id.toolbar);
        edt_timkiem = findViewById(R.id.edt_timkiem);
        edt_timkiem.setEnabled(true);

        navigationeview_home.setNavigationItemSelectedListener(this);
        navigationeview_home.setCheckedItem(R.id.nav_HomeAdmin);
        // Drawer

        replaceFragment(new HomeAdmin_Fragment());
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
            menu.findItem(R.id.nav_logout).setVisible(false);
        }else {
            menu.findItem(R.id.nav_login).setVisible(false);
        }
        super.onStart();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_ql_sanpham){
            replaceFragment(new QLSanPham_Fragment());


        }else if(id == R.id.nav_ql_taikhoan){
            replaceFragment(new QLTaiKhoan_Fragment());

        }
        else if(id == R.id.nav_ql_themsanpham){
            startActivity(new Intent(HomeAdmin_Activity.this,ThemSanPham_Activity.class));
            finish();

        }
        else if(id == R.id.nav_ql_hoadon){
           startActivity(new Intent(HomeAdmin_Activity.this,HoaDon_Activity.class));
            finish();

        }
        else if(id == R.id.nav_ql_gopy){
            replaceFragment(new QLGopY_Fragment());

        }
        else if(id == R.id.nav_ql_soluongsanpham){
            replaceFragment(new ThongKe_Fragment());

        }
        else if(id == R.id.nav_login){
            startActivity(new Intent(HomeAdmin_Activity.this, DangNhap_Activity.class));
            finish();

        }
        else if(id == R.id.nav_logout){
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