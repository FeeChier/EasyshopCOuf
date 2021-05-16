package com.example.auth.Model;

import androidx.lifecycle.LiveData;

import com.example.auth.Repository.ListRepo;

public class ListViewModel {
    ListRepo listRepo = new ListRepo();

    public LiveData<Double> getTotalPrice() {
        return listRepo.getTotalPrice();
    }
}
