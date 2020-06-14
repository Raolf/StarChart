package com.example.starchart;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.tasks.OnCompleteListener;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.multidex.MultiDex;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context base){
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListViewmodel lvm = new ListViewmodel();

        Button loginBut = findViewById(R.id.loginButton);
        Button signBut = findViewById(R.id.signButton);
        EditText nameText = findViewById(R.id.nameText);
        EditText passText = findViewById(R.id.passText);
        Switch remSwitch = findViewById(R.id.rememberSwitch);

        loginBut.setOnClickListener(view -> {
            System.out.println("Text her: "+nameText.getText().toString());
            System.out.println("Text her: "+passText.getText().toString());
            if(!nameText.getText().toString().isEmpty() && !passText.getText().toString().isEmpty()){
                if(lvm.getAuth().getCurrentUser() != null){
                    Intent intent = new Intent(getBaseContext(), ListView.class);
                    startActivity(intent);
                }

                lvm.getAuth().signInWithEmailAndPassword(nameText.getText().toString(),passText.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user;
                            user = lvm.getAuth().getCurrentUser();
                            if(user.isEmailVerified()){
                                Intent intent = new Intent(getBaseContext(), ListView.class);
                                startActivity(intent);
                            } else if(!user.isEmailVerified()){
                                Toast.makeText(getBaseContext(),"Login failed. Have you verified your email?",Toast.LENGTH_SHORT).show();
                                user.sendEmailVerification();
                                passText.setText("");
                            } else {
                                Toast.makeText(getBaseContext(),"Login failed. User could not be found.",Toast.LENGTH_SHORT).show();
                                passText.setText("");
                            }
                        } else {
                            Toast.makeText(getBaseContext(),"Login failed. Request incomplete.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(getBaseContext(),"Empty fields lead nowhere",Toast.LENGTH_SHORT).show();
            }

        });
        signBut.setOnClickListener(view -> {
            if(!nameText.getText().toString().isEmpty() && !passText.getText().toString().isEmpty()){
                lvm.getAuth().createUserWithEmailAndPassword(nameText.getText().toString(),passText.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getBaseContext(),"User created",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getBaseContext(),"User could not be created",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }else{
                Toast.makeText(getBaseContext(),"Empty fields lead nowhere",Toast.LENGTH_SHORT).show();
            }
        });

        lvm.getUser().observe(this, firebaseUser -> {
            if(firebaseUser.isEmailVerified()){
                if(remSwitch.isChecked()){
                    lvm.
                }
                Intent intent = new Intent(getBaseContext(), ListView.class);
                startActivity(intent);
            } else if(!firebaseUser.isEmailVerified()){
                firebaseUser.sendEmailVerification();
            } else {
                Toast.makeText(getBaseContext(),"Login failed. Have you verified your email?",Toast.LENGTH_SHORT).show();
                passText.setText("");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
