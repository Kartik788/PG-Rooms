package com.ringolatechapps.pgrooms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isUserLoggedIn()){
            //dashboard
            startActivity(new Intent(MainActivity.this,Dashboard.class));
        }else{
            //phone authentication
            startActivity(new Intent(MainActivity.this,getPhoneNumber.class));
        }
    }
    private boolean isUserLoggedIn(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            return true;
        }
        return false;
    }
}