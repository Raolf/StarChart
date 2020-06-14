package com.example.starchart;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ListRepository {

    private static ListRepository repo;

    public static ListRepository getInstance(){
        if(repo == null){
            repo = new ListRepository();
        }
        return repo;
    }
    private ListRepository(){

    }

    MutableLiveData<List<Star>> stars;
    Webservices ws = new Webservices();

    public void login(String email, String password){

    }
    public void signUp(String email, String password){

    }
    public MutableLiveData<List<Star>> getApiStars(){
        if(stars == null){
            //stars = ws.getStarList();
        }
        stars = ws.getStarList();

        ws.getStars();
        return stars;
    }
}
