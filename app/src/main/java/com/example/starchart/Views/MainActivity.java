package com.example.starchart.Views;

import android.content.Context;
import android.content.Intent;

import com.example.starchart.ListViewmodel;
import com.example.starchart.LocalDB.User;
import com.example.starchart.R;
import com.google.android.gms.tasks.OnCompleteListener;

import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.multidex.MultiDex;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
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

        ListViewmodel lvm = new ListViewmodel(getApplication());

        Button loginBut = findViewById(R.id.loginButton);
        Button signBut = findViewById(R.id.signButton);
        EditText nameText = findViewById(R.id.nameText);
        EditText passText = findViewById(R.id.passText);
        Switch remSwitch = findViewById(R.id.rememberSwitch);

        lvm.getDUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user.isRemember()){
                    nameText.setText(user.getUsername());
                    passText.setText(user.getPassword());
                }
            }
        }
        );


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
                                if(remSwitch.isChecked()){
                                    lvm.saveUser(new User(nameText.getText().toString(),passText.getText().toString(),remSwitch.isChecked()));
                                }
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

        remSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    lvm.saveUser(new User("","",false));
                }
            }
        });

        /*lvm.getUser().observe(this, firebaseUser -> {
            if(firebaseUser.isEmailVerified()){
                Intent intent = new Intent(getBaseContext(), ListView.class);
                startActivity(intent);
            } else if(!firebaseUser.isEmailVerified()){
                firebaseUser.sendEmailVerification();
            } else {
                Toast.makeText(getBaseContext(),"Login failed. Have you verified your email?",Toast.LENGTH_SHORT).show();
                passText.setText("");
            }
        });*/


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
