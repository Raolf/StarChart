package com.example.starchart;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.starchart.ApiResponse.ResponseObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class Webservices {

    String URL = "http://en.wikipedia.org/w/";
    Retrofit retrofit;
    StarInterface starService;
    FirebaseDatabase database;
    DatabaseReference Stardb;
    FirebaseAuth auth;
    FirebaseUser currentUser;

    final MutableLiveData<List<Star>> stars = new MutableLiveData<List<Star>>();

    public Webservices(){
        retrofit = new retrofit2.Retrofit.Builder() // Opretter retrofit klasse
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        starService = retrofit.create(StarInterface.class);
        database = FirebaseDatabase.getInstance();
        Stardb = database.getReference("Stars");

    }

    public void createNewUser(String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        System.out.println("createUserWithEmail:success");
                        //FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        System.out.println("createUserWithEmail:failure"+task.getException());

                    }
                }
            });
    }
    public void signIn(String email, String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        System.out.println("signInWithEmail:success");
                        FirebaseUser user = auth.getCurrentUser();
                    } else {
                        System.out.println("signInWithEmail:failure"+task.getException());
                        currentUser = null;
                    }
                }
            });

    }

    public void dataWrite(MutableLiveData<List<Star>> s){

        Stardb.child("stars").setValue(s.getValue());

    }
    public void dataRead(){
        Stardb.child("stars").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stars.postValue((List<Star>) dataSnapshot.getValue(List.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("A dataread was cancelled, Reason: "+databaseError.toException());
            }
        });
    }

    public void getStarList(MutableLiveData<List<Star>> starList){
        if(stars.getValue() == null){
            getStars();
            dataRead();
        }
        starList = stars;
    }

    public void getStars(){
        Call<ResponseObject> c = starService.getStarList();
        c.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if(response.isSuccessful()){
                    //System.out.println(response.code()+"\n"+response.message());
                    stars.postValue(response.body().getQuery().getStarList());
                    System.out.println(stars.getValue().get(1).name);
                }else {
                    System.out.println("Call Succeded, but response failed.");
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                System.out.println("Call failed, recieved: " + t);
            }
        });
    }

    interface StarInterface{

        @GET("api.php?action=query&list=categorymembers&cmtitle=Category:Stars_with_proper_names&format=json")
        Call<ResponseObject> getStarList();

        @GET()
        Call<Star> getStar(@Path("") int pageid);

    }
}
