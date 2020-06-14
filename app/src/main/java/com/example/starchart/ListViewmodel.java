package com.example.starchart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ListViewmodel extends ViewModel {

    MutableLiveData<List<Star>> stars;
    MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    ListRepository repository = ListRepository.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
}
