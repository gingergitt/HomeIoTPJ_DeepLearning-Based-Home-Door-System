package com.example.testtest;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private final String TAG = "MyFirebaseIDService";

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "token: " + token);

        sendRegistrationToServer(token);

    }

    private void sendRegistrationToServer(String token) {

        //서버로 토큰 전송

    }
}
