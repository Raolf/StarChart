package com.example.starchart.WebSources;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.starchart.ApiResponse.ResponseObject;
import com.example.starchart.ApiResponse.Star;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    String URL = "https://en.wikipedia.org/w/";
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
        auth = FirebaseAuth.getInstance();
        //Stardb = database.getReference();
    }

    public void createNewUser(String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        System.out.println("createUserWithEmail:success");
                    } else {
                        System.out.println("createUserWithEmail:failure"+task.getException());

                    }
                }
            });
    }
    public void signIn(String email, String password, MutableLiveData<FirebaseUser> ldUser){

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    System.out.println("signInWithEmail:success");
                    FirebaseUser user = auth.getCurrentUser();
                    ldUser.setValue(user);
                } else {
                    System.out.println("signInWithEmail:failure"+task.getException());
                    currentUser = null;
                }
            }
        });
    }

    /*public void dataWrite(MutableLiveData<List<Star>> s){

        Stardb.child("star").setValue(s.getValue());

    }

    public void dataRead(){
        Stardb.child("star").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stars.postValue((List<Star>) dataSnapshot.getValue(List.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("A dataread was cancelled, Reason: "+databaseError.toException());
            }
        });
    }*/

    public MutableLiveData<List<Star>> getStarList(){
        if(stars.getValue() == null){
            getStars();
            //dataRead();
        }
        return stars;
    }

    public void getStars(){
        Call<ResponseObject> c = starService.getStarList();
        c.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if(response.isSuccessful()){
                    stars.setValue(response.body().getQuery().getStarList());
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

        @GET("")
        Call<ResponseObject> getWeirdList();

        @GET()
        Call<Star> getStar(@Path("") int pageid);

    }
}
