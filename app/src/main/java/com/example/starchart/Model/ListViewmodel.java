package com.example.starchart.Model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.starchart.LocalDB.User;
import com.example.starchart.Model.ApiResponse.Star;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ListViewmodel extends AndroidViewModel {

    MutableLiveData<List<Star>> stars;
    MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    ListRepository repository;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public ListViewmodel(Application app){
        super(app);
        repository = ListRepository.getInstance(app);
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public void fetchList(){
        stars = repository.getApiStars();
    }

    public MutableLiveData<List<Star>> getStars() {
        fetchList();
        return stars;
    }

    public void login(String email, String password){
        repository.login(email, password, user);
    }
    public void signUp(String email, String password){
        repository.signUp(email, password);
    }

    public MutableLiveData<FirebaseUser> getUser(){
        return user;
    }

    public void saveUser(User user){
        repository.saveUser(user);
    }
    public MutableLiveData<User> getDUser(){
        MutableLiveData<User> user = new MutableLiveData<>();
        repository.getUser(user);
        return user;
    }
}
