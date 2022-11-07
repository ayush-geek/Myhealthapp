package com.example.myhealthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnv;

    DashBoardFragment dsbF = new DashBoardFragment();
    LogFragment logF;
    TargetFragment tarF = new TargetFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            this.finish();
        }

        GetData g = new ViewModelProvider(this).get(GetData.class);
        String curUser = "1234"; // Get User ID
        g.setUserID(curUser);
        g.getUserData().observe(this, data -> {
            logF = new LogFragment(data);
            Log.d("IMAD", data.get(1).get(0));
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, dsbF).commit();
        bnv = findViewById(R.id.bottomNavigationView);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.dash): {
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, dsbF).commit();
                        return true;
                    } case (R.id.log): {
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, logF).commit();
                        return true;
                    } case (R.id.tar): {
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, tarF).commit();
                        return true;
                    }
                }

                return false;
            }
        });
    }

    void startLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        this.finish();
    }
}