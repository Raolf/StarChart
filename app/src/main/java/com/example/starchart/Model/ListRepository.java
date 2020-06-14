package com.example.starchart.Model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.starchart.LocalDB.User;
import com.example.starchart.LocalDB.UserDao;
import com.example.starchart.LocalDB.UserDatabase;
import com.example.starchart.Model.ApiResponse.Star;
import com.example.starchart.WebSources.Webservices;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ListRepository {

    private static ListRepository repo;
    UserDao userDao;
    User user;

    public static ListRepository getInstance(Application application){
        if(repo == null){
            repo = new ListRepository(application);
        }
        return repo;
    }
    private ListRepository(Application application){
        UserDatabase db = UserDatabase.getInstance(application.getApplicationContext());
        userDao = db.userDao();

    }

    MutableLiveData<List<Star>> stars;
    Webservices ws = new Webservices();

    public void login(String email, String password, MutableLiveData<FirebaseUser> user){
        ws.signIn(email, password,user);
    }
    public void signUp(String email, String password){
        ws.createNewUser(email, password);
    }
    public MutableLiveData<List<Star>> getApiStars(){
        if(stars == null){
            //stars = ws.getStarList();
        }
        stars = ws.getStarList();

        ws.getStars();
        return stars;
    }
    public void saveUser(User user){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                userDao.delete(user);
                userDao.insertUser(user);
            }
        });
    }
    public void getUser(MutableLiveData<User> u){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<User> users = userDao.getAll();
                if(users.isEmpty()){
                    u.postValue(new User("","",false));
                } else{
                    u.postValue(users.get(0));
                }

            }
        });
    }

    class InsertUserAsync extends AsyncTask<User,Void,Void>{
        private UserDao userDao;

        private void InsertUserAsync(UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUser(users[0]);
            return null;
        }
    }
    class ReadUserAsync extends AsyncTask<Void,Void,User>{
        private UserDao userDao;

        private void ReadUserAsync(UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected User doInBackground(Void... voids) {
            return userDao.getAll().get(0);
        }
    }
}
