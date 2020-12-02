package com.example.testtest.ui.guard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import androidx.lifecycle.ViewModelProvider;


import com.example.testtest.MainActivity;
import com.example.testtest.R;
import com.example.testtest.ui.slideshow.SlideshowFragment;

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

// 모르는 손님 방문시 - 버튼
public class guardFragment extends Fragment {

    ImageView guestImage;
    Button btnIgnore, btnOpen;
    LinearLayout linear1, linear2;
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


    public static guardFragment newInstance() {
        return new guardFragment();
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        guardViewModel =
                new ViewModelProvider(this).get(GuardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_guard, container, false);

        guestImage = (ImageView) root.findViewById(R.id.guestImage);
        btnIgnore = (Button) root.findViewById(R.id.btnIgnore);
        btnOpen = (Button) root.findViewById(R.id.btnOpen);
        linear1 = (LinearLayout) root.findViewById(R.id.linear1);
        linear2 = (LinearLayout) root.findViewById(R.id.linear2);

        final TextView textView = root.findViewById(R.id.text_guard);
        guardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect_LEDON();
            }
        });

        btnIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect_LEDOFF();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view_picture();
        linear1.setVisibility(View.VISIBLE);
    }

    //guest방문일경우의 메서드
    void view_picture() {
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

//                        while ((ch = bis.read()) != -1) {
//
//
//                            Log.d("------temp", "temp=" + temp);
//
//                            fos.write(ch);
//
//                        }


                    //----------------
                    int offset = 0;
                    for (int j = 0; j < length; j++) {//파일 길이 만큼 읽습니다.
                        fos.write(bis.read());

                        fos.flush();
                    }
//                        while ((ch = bis.read(buffer, offset, 1024)) != -1) {
//                            temp += 1024;
//                            offset = temp;
//
//                            Log.d("------temp", "temp=" + temp);
//
//                            fos.write(ch);
//                            if (temp >= length) {
//                                break;
//                            }
//
//                            //열린 파일시스템에 BufferedInputStream으로 외부로 부터 읽어들여온 파일을 FileOutputStream에 바로 써준다.
//                            //Log.w("check while", "check while");
//                        }
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
    }





    void connect_LEDOFF() {


        Log.w("connect","연결 하는중");
        // 받아오는거
        Thread checkUpdate = new Thread() {
            public void run() {
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
                    dis = new DataInputStream(socket.getInputStream());     // input에 받을꺼 넣어짐
                    dos.writeUTF("2");

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                try {
                    dos.writeUTF("END");
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        checkUpdate.start();

    }

    //
    void connect_LEDON() {


        Log.w("connect","연결 하는중");
        // 받아오는거
        Thread checkUpdate = new Thread() {
            public void run() {
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
                    Log.w("전송", "전송성공?");
                    dos = new DataOutputStream(socket.getOutputStream());   // output에 보낼꺼 넣음
                    dis = new DataInputStream(socket.getInputStream());     // input에 받을꺼 넣어짐
                    dos.writeUTF("open");
                    Log.w("전송", "전송성공!");

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                try {
                    dos.writeUTF("END");
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        checkUpdate.start();

    }

    class nHandler extends Handler {

        nHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            //System.out.println("Hadle message !!!");
           // textView.setText(msg.obj.toString());
           // guestImage.setImageBitmap(mybitmap);
            try {
                File f=new File("/data/data/com.example.testtest/files/test.jpg");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                guestImage.setImageBitmap(b);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }


        }
    }

}
