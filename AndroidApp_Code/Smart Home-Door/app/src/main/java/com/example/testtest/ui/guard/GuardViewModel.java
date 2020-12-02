package com.example.testtest.ui.guard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GuardViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<String> mText;

    public GuardViewModel() {

        mText = new MutableLiveData<>();
        mText.setValue("방문자를 확인하세요!");


    }

    public MutableLiveData<String> getText() {

        return mText;
    }


}
