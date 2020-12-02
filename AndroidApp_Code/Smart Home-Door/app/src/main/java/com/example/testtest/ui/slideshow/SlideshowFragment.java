package com.example.testtest.ui.slideshow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.testtest.MainActivity;
import com.example.testtest.R;
import com.example.testtest.ui.guard.GuardViewModel;
import com.example.testtest.ui.home.HomeFragment;
import com.example.testtest.ui.home.HomeViewModel;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// 가족 방문시 사진만 확인하는 frament
public class SlideshowFragment extends Fragment {
    //--------------------------------
   //MyHandler myHandler=null;

    LinearLayout linear1;
    private GuardViewModel guardViewModel;
    private Handler nHandler;

    private Socket socket;

    private BufferedReader networkReader;
    private PrintWriter networkWriter;

    private DataOutputStream dos;
    private DataInputStream dis;

    private String ip = "192.168.0.146";            // IP 번호
    private int port = 9999;

    private InputStream in;
    private HomeViewModel homeViewModel;

    ImageView openImage;


    private SlideshowViewModel slideshowViewModel;

    public static SlideshowFragment newInstance() {
        return new SlideshowFragment();
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        final TextView textView = root.findViewById(R.id.text_home);
        openImage = (ImageView)root.findViewById(R.id.openImage);
        openImage.setVisibility(View.VISIBLE);
        connect_LEDON();

        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });
        return root;

    }

    //이미지띄우기

    void connect_LEDON() {


        Log.w("connect", "연결 하는중");
        // 받아오는거

        Thread checkUpdate = new Thread() {
            public void run() {
                File file;
                BufferedReader socketIn = null;
                String file_name = "test.jpg";
                int cnt = 0;
                Log.w("스레드 시작", "스레드");
                try {
                    socket = new Socket(ip, port);
                    Log.w("서버 접속됨", "서버 접속됨");
                } catch (IOException e1) {
                    Log.w("서버접속못함", "서버접속못함");
                    e1.printStackTrace();
                }

                // Buffered가 잘못된듯.
                try {
                    dos = new DataOutputStream(socket.getOutputStream());   // output에 보낼꺼 넣음
                    socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    dos.writeUTF("0");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                    // 서버에서 받아옴
                    try {

//                        file = new File(getContext().getFilesDir(), file_name);
                        file = new File("/data/data/com.example.testtest/files/test.jpg");
                        String line = socketIn.readLine();
                        int length = Integer.parseInt(line);
                        Log.d("------length", "length=" + length);

                        //line = socketIn.readLine();
                        //Log.w("리드", line);
                        in = socket.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(in);
                        file.createNewFile();

                        FileOutputStream fos = new FileOutputStream(file);
                        int ch;
                        int temp = 0;
                        byte[] buffer = new byte[1024];
                        Log.w("check", "check1");

                        //----------------
                        int offset = 0;
                        for (int j = 0; j < length; j++) {//파일 길이 만큼 읽습니다.
                            fos.write(bis.read());

                            fos.flush();
                        }

                        //-------------------
                        Log.w("check2", "Check2");

                        fos.close();


                    } catch (Exception e) {
                        Log.w("리드", "리드 오류");
                        e.printStackTrace();
                    }
                    try {
                        Log.d("------전송완료", "전송완료");
                        dos.writeUTF("END");
                        in.close();

                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//
                nHandler = new nHandler(Looper.getMainLooper());
                Message msg = nHandler.obtainMessage();
                nHandler.sendMessage(msg);
                }


        };
        checkUpdate.start();
    };

    class nHandler extends Handler{
        nHandler(Looper looper){
            super(looper);
        }
        public void handleMessage(Message msg) {
            Log.d("-------handler","핸들러");
//
//
//            File file;
//            String file_name = "test.jpg";
//
//            file = new File(getContext().getFilesDir(), file_name);
////            Uri uri = Uri.fromFile(new File(getContext().getFilesDir(),file_name));
//            Uri uri = Uri.parse(getContext().getFilesDir()+"/test.jpg");
//            Log.d("----------dir","dir ="+getContext().getFilesDir()+" path="+file.getAbsolutePath());
//
//                try {
//                    Log.w("check", "check1");
//
//                    //------------------------
//                    // 이미지 뿌리기
//                    //Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
////                    Bitmap myBitmap = BitmapFactory.decodeFile("test.jpg");
//                    Bitmap myBitmap = BitmapFactory.decodeFile(uri.getPath());
//                    Log.d("------bitmap","bitmapopen");
////                    openImage.setImageBitmap(myBitmap);
//                    openImage.setImageURI(uri);
//                    openImage.setImageBitmap(getResources(),uri.getPath());
//
////                    openImage.setVisibility(View.VISIBLE);
//
////                      Log.d("--------", file_name);
////                    if (!file_name.equals("")) {
////                    }else {
////
////                    }
//                    Log.w("check2", "Check2");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                //
//
//
//
            try {
                File f=new File("/data/data/com.example.testtest/files/test.jpg");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
//                Bitmap b = BitmapFactory.decodeFile("/data/data/com.example.testtest/files/test.jpg");
                openImage.setImageBitmap(b);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

        }


    };


}


