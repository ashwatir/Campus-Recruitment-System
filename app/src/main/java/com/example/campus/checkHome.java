package com.example.campus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

@SuppressWarnings("ALL")
public class checkHome extends AppCompatActivity {
    private TextView tv;

    DBHelper DB;
    DrawerLayout dl;
    TextView name, email;
    ImageView image;
    BottomNavigationView bottomNavigationView;
    NavigationView nv;
    SharedPreferences pref;
    Button edit, logout,info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_home);

        DB = new DBHelper(this);
        nv = findViewById(R.id.nav_view);
        View v = nv.getHeaderView(0);
        pref = getApplicationContext().getSharedPreferences("mypref", MODE_PRIVATE);
        name = v.findViewById(R.id.navHeadName);
        email = v.findViewById(R.id.navHeadEmail);
        image = v.findViewById(R.id.navHeadImage);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        String ema = pref.getString("KEY_USER", null);

        if (!ema.equals("admin")) {
            name.setText(DB.getName(ema));
            email.setText(ema);

        }
//        if(DB.getPPValue(ema)!=null){
//
//            byte[] bytesPP=DB.getPPImage(ema);
//            if(bytesPP!=null){
//                Bitmap bmp = BitmapFactory.decodeByteArray(bytesPP, 0,bytesPP.length);
//                image.setImageBitmap(bmp);
//            }
//
//        }


        Fragment defaultFrag;


        switch (getIntent().getIntExtra("load", 0)) {
            case 0:
                defaultFrag = new NewsFragment();
                break;
            case 1:
                defaultFrag = new OngoingFragment();
                break;
            case 2:
                defaultFrag = new ProfileFragment();
                break;
            case 3:
                defaultFrag = new ApplyFragment();
                break;
            default:
                defaultFrag = new NewsFragment();

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, defaultFrag).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        dl = findViewById(R.id.drawer_layout);
        edit = findViewById(R.id.edit_profile);
        logout = findViewById(R.id.logout);
        info=findViewById(R.id.info);

        if (ema.equals("admin")) {
            edit.setVisibility(View.GONE);
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(checkHome.this, EditProfile.class);
                startActivity(intent);
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(checkHome.this, Location.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = pref.edit();
                edit.clear();
                edit.apply();
                Intent intent = new Intent(checkHome.this, LogIn.class);
                startActivity(intent);
                finish();
            }
        });

    }
        BottomNavigationView.OnNavigationItemSelectedListener navListener=
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment=null;
                        switch (item.getItemId()){
                            case R.id.news:
                                selectedFragment= new NewsFragment();
                                break;
                            case R.id.ongoing:
                                selectedFragment= new OngoingFragment();
                                break;
                            case R.id.profile:
                                selectedFragment= new ProfileFragment();
                                break;
                            case R.id.apply:
                                selectedFragment= new ApplyFragment();
                                break;
                            default:
                                selectedFragment= new NewsFragment();
                                break;
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                        return true;
                    }
                };
    private Bitmap covertByteToBitmap(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    }
