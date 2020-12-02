package com.example.testtest.ui.home;

import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    public static MutableLiveData<String> mText;
    public static MutableLiveData<String> mText2;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("SMART HOME DOOR SYSTEM - YEREO");

//        mText2 = new MutableLiveData<>();
//        mText2.setValue("현재 방문자가 없습니다.");

    }

    public LiveData<String> getText() {
        return mText;

    }

//    public MutableLiveData<String> getmText2() {
//        return mText2;
//    }
}