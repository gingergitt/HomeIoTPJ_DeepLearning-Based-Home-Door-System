package com.example.testtest.ui.gallery;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testtest.R;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    Button btnLED;
    Button btnDB;
    TextView textView;
    private GalleryViewModel galleryViewModel;
   // ImageView imageView2;
    ListView list1;
    //
//    MyListAdapter myListAdapter;
//    ArrayList<ListViewitem> ListArrayList;

    //Data[] Index_data;

    private String html = "";
    private Handler mHandler, myHandler;

    private Socket socket;

    private BufferedReader networkReader;
    private PrintWriter networkWriter;

    private DataOutputStream dos;
    private DataInputStream dis;
    private InputStream in;

    private String ip = "192.168.0.146";            // IP 번호
   // private String ip = "192.168.200.146"; //ip - house
    private int port = 9999;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
               new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);



        list1= (ListView) root.findViewById(R.id.list1);

        final TextView textgallery = root.findViewById(R.id.text_gallery);
        TextView textView = (TextView) root.findViewById(R.id.textView);
        btnDB =(Button) root.findViewById(R.id.btnDB);
        //imageView2 = (ImageView) root.findViewById(R.id.imageView2);

//        ListArrayList = new ArrayList<ListViewitem>();
        //myListAdapter = new MyListAdapter();




        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textgallery.setText(s);
                //imageView2.setVisibility(View.INVISIBLE);



            }
        });

        btnDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect_DB();
                //db연결 후 조회 스레드 만들기? db에서가져오기?
            // imageView2.setVisibility(View.VISIBLE);


            }
        });





        return root;



    }

    void connect_DB() {

        Log.w("connect","연결 하는중");
        // 받아오는거
        Thread checkUpdate = new Thread() {
            public void run() {
                BufferedReader socketIn=null;

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
                    //dis = new DataInputStream(socket.getInputStream());     // input에 받을꺼 넣어짐
                    socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    socketIn = new BufferedInputStream(socket.getInputStream());

                    dos.writeUTF("DB");

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }

                while(true) {
                    // 서버에서 받아옴
                    try {
//                        File file;
//                        String file_name = "test";
//                        int cnt = 0;
                        String line = "";
                        String printLine = "";

                        while (true) {
//                            file_name = file_name + cnt + ".jpg";
//                            file = new File(getContext().getFilesDir(), file_name);
//                            cnt++;
//                            in = socket.getInputStream();
// ------------------------------------------------------------------------------------------
//                            BufferedInputStream bis = new BufferedInputStream(in);
//                            file.createNewFile();
//                            FileOutputStream fos = new FileOutputStream(file);
                            int ch;
                            Log.w("check", "check1");

                            //----------------------------------------------------------------------------------
                            line = socketIn.readLine();
                            Log.d("---------",line);
                            if (!line.contains("EOF")) {
                                Log.d("----------!EOF", printLine);
                                if (!line.contains("EOL")) {
                                    printLine += " " + line;
                                } else {

                                    printLine += "\n";
                                }
                            } else {
                                Log.d("----------EOF", printLine);
                                //문자 표시
                                myHandler = new MyHandler(Looper.getMainLooper());
                                Message msg = myHandler.obtainMessage();
                                msg.obj = printLine;
                                myHandler.sendMessage(msg);
                                Log.d("----------msg", msg.obj.toString());
                                Log.d("----------printline", printLine);
//                                textView.setText(printLine);
                                // id, date, content

                                // listview로 가져오기?

//                                ListArrayList.add();
//                                myListAdapter.notifyDataSetChanged();

//

                                break;
                            }
                            //----------------------------------------------------------------------------------


//                            while ((ch = bis.read()) != -1) {
//                                //문자열 받기
//
//                                fos.write(ch);
//                                fos.flush();
////                                sb.append(ch);
//                                //열린 파일시스템에 BufferedInputStream으로 외부로 부터 읽어들여온 파일을 FileOutputStream에 바로 써준다.
//                                Log.w("check while", "check while");
//
//                            }
//                            // 이미지 뿌리기
//                            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                            if (!file_name.equals("")) {
//                                for (int i = 0; i < file_name.length(); i++) {
//                                    imageView2.setImageBitmap(myBitmap);
//
//                                    Log.d("--------", file_name);
//                                }
//
//                            }
                            Log.w("check2", "Check2");

//                            //문자받기
//                            line = socketIn.readLine();
//                            Log.d("--------2-",line);
//                            if (!line.contains("EOF")) {
//                                if (!line.contains("EOL")) {
//                                    printLine += " " + line;
//                                } else {
//                                    printLine += "\n";
//                                }
//                            } else {
//                                //문자 표시
//                                myHandler = new MyHandler(Looper.getMainLooper());
//                                Message msg = myHandler.obtainMessage();
//                                msg.obj = printLine;
//                                myHandler.sendMessage(msg);
//                                textView.setText(printLine);
//                                break;
//                            }




//                            fos.close();
//                            in.close();



                        }
                    }
                     catch (Exception e) {
                        Log.w("리드", "리드 오류");
                        e.printStackTrace();
                    }
                    try {
                       dos.writeUTF("END");
                        socket.close();
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        };
        checkUpdate.start();

    }

    class MyHandler extends Handler {

        MyHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            //System.out.println("Hadle message !!!");


//         ArrayList<String> message = new ArrayList<String>();
//         message.add(msg.obj.toString());

            String mess= msg.obj.toString();
            String [] message = mess.split("\n");
            //String [] message2 = mess.split(" ");

            ArrayAdapter adapter =
                    new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, message);

            Log.d("handler",mess);
            list1.setAdapter(adapter);
//            textView.setText(msg.obj.toString());


            //

        }
    }


}
