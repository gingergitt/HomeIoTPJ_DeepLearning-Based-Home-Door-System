package com.example.testtest.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("등록된 사용자가 방문하였습니다. 문을 엽니다.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}