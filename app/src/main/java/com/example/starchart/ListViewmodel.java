package com.example.starchart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ListViewmodel extends ViewModel {

    MutableLiveData<List<Star>> stars;

    ListRepository repository = ListRepository.getInstance();

    public void fetchList(){
        stars = repository.getApiStars();
    }

    public MutableLiveData<List<Star>> getStars() {
        fetchList();
        return stars;
    }
}
