package com.tourassistant.coderoids.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tourassistant.coderoids.R;
import com.tourassistant.coderoids.home.DashboardActivity;

public class LoginProcessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_process);
        FirebaseUser users= FirebaseAuth.getInstance().getCurrentUser();
        if(users!= null &&users.getEmail() != null && !users.getEmail().matches("")){
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }


    @Override
    public void onBackPressed() {

    }
}