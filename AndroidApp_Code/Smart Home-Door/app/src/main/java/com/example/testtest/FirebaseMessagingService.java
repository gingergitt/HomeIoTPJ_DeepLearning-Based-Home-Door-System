package com.example.testtest;

import android.content.Intent;
import android.util.Log;

import com.example.testtest.MainActivity;
import com.example.testtest.ui.home.HomeFragment;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService
        extends com.google.firebase.messaging.FirebaseMessagingService{

    private static final String TAG = "FirebaseMsgService";

    private String msg, title;

        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {

            Log.e(TAG,"onMessageReceived");


            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            //앱이 포어그라운드 상태일 때 준 데이터 값을 받아오는 부분
            String data = remoteMessage.getData().get("body");
            Log.e(TAG, "Body: " + data);

            //data 값 저장 후 메인 액티비티 실행
            Intent intent = new Intent(this, MainActivity.class);
//            Intent intent = new Intent(getApplication(), HomeFragment.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("body", data);
            startActivity(intent);

        }
}
