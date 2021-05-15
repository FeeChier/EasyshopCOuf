package com.example.auth;

import androidx.lifecycle.LiveData;

public class ListViewModel {
    ListRepo listRepo = new ListRepo();

    public LiveData<Double> getTotalPrice() {
        return listRepo.getTotalPrice();
    }
}
