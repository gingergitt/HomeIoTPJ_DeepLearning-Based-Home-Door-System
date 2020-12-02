package com.example.testtest.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.testtest.MainActivity;
import com.example.testtest.R;
import com.example.testtest.ui.guard.GuardViewModel;
import com.example.testtest.ui.guard.guardFragment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

//메인화면 프레그먼트
public class HomeFragment extends Fragment {



    //--------------------------------
//    MyHandler myHandler=null;
//
//    LinearLayout linear1;
    private HomeViewModel homeViewModel;
    private TextView texthome;
//    private Handler mHandler;
//
//    private Socket socket;
//
//    private BufferedReader networkReader;
//    private PrintWriter networkWriter;
//
//    private DataOutputStream dos;
//    private DataInputStream dis;
//
//    private String ip = "192.168.0.146";            // IP 번호
//    private int port = 9999;
//
//    private InputStream in;
//    private HomeViewModel homeViewModel;
//
//    ImageView openImage;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }



    //--------------------------------
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView texthome = root.findViewById(R.id.text_home);
//        openImage = (ImageView)root.findViewById(R.id.openImage);
//        openImage.setVisibility(View.INVISIBLE);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                texthome.setText(s);

            }

        });
//        texthome.setText("Smart home door system - YEREO");



        return root;


    }


}
