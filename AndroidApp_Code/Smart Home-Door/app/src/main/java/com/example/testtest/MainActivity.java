package com.example.testtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Fragment;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.testtest.ui.gallery.GalleryFragment;
import com.example.testtest.ui.guard.guardFragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.testtest.ui.home.HomeFragment;
import com.example.testtest.ui.home.HomeViewModel;
import com.example.testtest.ui.slideshow.SlideshowFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;


    Fragment fragment;
    HomeFragment homeFragment;
    guardFragment guardFragment;
    SlideshowFragment slideshowFragment;
    GalleryFragment galleryFragment;


    NavigationView nav_view;
    LinearLayout homelinear;
    int LED = 0;
    TextView textView;
    Button btnLED;
    Button btnDB;
    ConstraintLayout content_main, co1, co2;
    FrameLayout nav_host_fragment;


    private String html = "";
    private Handler mHandler, myHandler;

    private Socket socket;

    private BufferedReader networkReader;
    private PrintWriter networkWriter;

    private DataOutputStream dos;
    private DataInputStream dis;

    private String ip = "192.168.0.146";            // IP 번호
    private int port = 9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        homelinear = (LinearLayout) findViewById(R.id.homelinear);
        //------------------------------------------------------
        String tmp = "";
        //Fragment guardFragment = new Fragment();

        //실험용 텍스트 뷰
        textView = (TextView) findViewById(R.id.textView2);

        //백&포어 그라운드 상태 상관없이 값을 받아오는 부분
        if (getIntent().getExtras() != null) {
            tmp = getIntent().getExtras().get("body").toString();
        }
        //--------------------------------------------------------

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_실시간, R.id.nav_guard)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.co1, homeFragment.newInstance()).commit();


        if (tmp.equals("Door Open")) {
            Log.e("PLZ", "이게 되네");
            //실험용 텍스트 뷰
            textView.setText(tmp);
            // 방문자가 아는 등록된 사람 이었을 경우
            ((MainActivity) this).replaceFragment(SlideshowFragment.newInstance());
//            fragmentTransaction.addToBackStack(null);

        } else if (tmp.equals("Check Visitor")) {
            // 방문자가 모르는 사람 이었을 경우
            textView.setText(tmp);
            ((MainActivity) this).replaceFragment(guardFragment.newInstance());


        } else {
            // 초기 실행시
            textView.setText("방문한 손님이 없습니다.");
//            HomeViewModel homeViewModel = new HomeViewModel();
//
//                HomeViewModel.mText.setValue("dd");


//            Log.e("PLZ", "이게 안되네");
            /*

            textView.setText("wow");*/
            // 방법 1. 이곳에 이미지 뷰 객체 생성 or 이미지 뷰 아이디 값 받아온 뒤에
            // 안보여주게 처리 + 버튼도 동일하게 비활성화 & 안보이게
            // 방법 2. 초기 프래그먼트 실행
        }


    }

    public void replaceFragment(androidx.fragment.app.Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.homelinear, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
        //fragmentTransaction.remove(homeFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        //88888
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}


