package com.example.starchart;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class Main {
    //Used for testing only.
    public static void main(String[] args) {
        Webservices wb = new Webservices();
        MutableLiveData<List<Star>> l = new MutableLiveData<>();
        wb.getStarList();
    }
}
