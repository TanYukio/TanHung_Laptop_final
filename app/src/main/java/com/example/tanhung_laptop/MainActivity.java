package com.example.tanhung_laptop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanhung_laptop.Models.TAIKHOAN;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerlaout;
    NavigationView navigationeview_home;
    ImageView menu;
    Toolbar toolbar;
    CircleImageView img_user_home;
    TextView txtTennguoidung;
    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_apple = 2;
    private static final int FRAGMENT_asus = 3;
    private static final int FRAGMENT_dell = 4;

    private static final int FRAGMENT_hp = 5;
    private static final int FRAGMENT_acer = 6;
    private static final int FRAGMENT_user = 7;
    private static final int FRAGMENT_login = 8;
    private static final int FRAGMENT_logout = 9;
    private static final int FRAGMENT_giohang = 10;/**/


    private int currentFragment = FRAGMENT_HOME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        int idtk = intent.getIntExtra("idtk",123);
        Toast.makeText(this, "id = " + idtk, Toast.LENGTH_SHORT).show();
        anh_xa();
        if(idtk != 123){
            BatDau_activity.taikhoan = laythongtin(idtk);
//            byte[] hinhAnh = BatDau_activity.taikhoan.getHINHANH();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
//            img_user_home.setImageBitmap(bitmap);
//            txtTennguoidung.setText(BatDau_activity.taikhoan.getTENTAIKHOAN());
        }


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
        drawerlaout=findViewById(R.id.drawerlaout);
        navigationeview_home=findViewById(R.id.navigationeview_home);
        menu=findViewById(R.id.menu);
        toolbar = findViewById(R.id.toolbar);
        img_user_home = findViewById(R.id.img_user_home);
        txtTennguoidung = findViewById(R.id.txtTennguoidung);







        navigationeview_home.setNavigationItemSelectedListener(this);
        navigationeview_home.setCheckedItem(R.id.trangchu);
        // Drawer

        replaceFragment(new TrangChuFragment());
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
        else if(id == R.id.acer){
            replaceFragment(new ACER_Fragment());

        }
        else if(id == R.id.user){
            startActivity(new Intent(this,MainActivity.class));

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