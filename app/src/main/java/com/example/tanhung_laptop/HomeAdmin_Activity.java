package com.example.tanhung_laptop;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tanhung_laptop.Models.TAIKHOAN;
import com.example.tanhung_laptop.User.BatDau_activity;
import com.example.tanhung_laptop.User.DangNhap_Activity;
import com.example.tanhung_laptop.User.GopY_Fragment;
import com.google.android.material.navigation.NavigationView;

public class HomeAdmin_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Drawer
    DrawerLayout drawerlaout;
    NavigationView navigationeview_home;
    ImageView menu;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        Toast.makeText(this, "Chào mừng bạn đến với giao diện Admin!", Toast.LENGTH_SHORT).show();


        anh_xa();



        ActionBar();

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
        drawerlaout=findViewById(R.id.drawerlaout_admin);
        navigationeview_home=findViewById(R.id.navigationeview_home);
        menu=findViewById(R.id.menu);
        toolbar = findViewById(R.id.toolbar);







        navigationeview_home.setNavigationItemSelectedListener(this);
        navigationeview_home.setCheckedItem(R.id.nav_HomeAdmin);
        // Drawer

        replaceFragment(new HomeAdmin_Fragment());
    }



//    @Override
//    public void onBackPressed() {
//        if (drawerlaout.isDrawerOpen(GravityCompat.END)) {
//            drawerlaout.closeDrawer(GravityCompat.END);
//        } else {
//            super.onBackPressed();
//        }
//    }


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

        }
        else if(id == R.id.nav_ql_hoadon){
           startActivity(new Intent(HomeAdmin_Activity.this,HoaDon_Activity.class));

        }
        else if(id == R.id.nav_ql_gopy){
            Toast.makeText(this, "Chưa có", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.nav_ql_soluongsanpham){
            replaceFragment(new ThongKe_Fragment());
        }
        else if(id == R.id.login){
            startActivity(new Intent(HomeAdmin_Activity.this, DangNhap_Activity.class));
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