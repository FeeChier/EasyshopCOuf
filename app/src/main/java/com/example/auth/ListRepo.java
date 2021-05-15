package com.example.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ListRepo {

    private MutableLiveData<List<ListModel>> mutableCart = new MutableLiveData<>();
    private MutableLiveData<Double> mutableTotalPrice = new MutableLiveData<>();

    public LiveData<Double> getTotalPrice() {
        if (mutableTotalPrice.getValue() == null) {
            mutableTotalPrice.setValue(0.0);
        }
        return mutableTotalPrice;
    }
}
