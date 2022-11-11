package com.example.myhealthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.myhealthapp.add.AddFoodFragment;
import com.example.myhealthapp.log.AddFoodOptionFragment;
import com.example.myhealthapp.log.LogFragment;
import com.example.myhealthapp.log.ShowLogFragment;
import com.example.myhealthapp.network.model.Food;
import com.example.myhealthapp.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnv;

    DashBoardFragment dsbF = new DashBoardFragment();
    LogFragment logF = new LogFragment();
    TargetFragment tarF = new TargetFragment();

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startLogin();
        }

        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.mainFrag, dsbF)
                .addToBackStack("dashB").commit();

        bnv = findViewById(R.id.bottomNavigationView);

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.dash): {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.mainFrag, dsbF)
                                .addToBackStack("dashB").commit();
                        return true;
                    }
                    case (R.id.log): {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.mainFrag, logF)
                                .addToBackStack("logF").commit();
                        return true;
                    }
                    case (R.id.tar): {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.mainFrag, tarF)
                                .addToBackStack("tarF").commit();
                        return true;
                    }
                }

                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        int len = fm.getBackStackEntryCount();
        if (len <= 1) {
            super.onBackPressed();
            finish();
        } else {
            fm.popBackStack();
            FragmentManager.BackStackEntry backStackEntry = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 2);

            if (backStackEntry.getName() == null) {
                return;
            }

            String frag = backStackEntry.getName();

            switch (frag) {
                case "showLogF":
                case "searchF":
                case "logF":
                    bnv.getMenu().findItem(R.id.log).setChecked(true);
                    break;
                case "tarF":
                    bnv.getMenu().findItem(R.id.tar).setChecked(true);
                    break;
                default:
                    bnv.getMenu().findItem(R.id.dash).setChecked(true);
                    break;
            }
        }
    }

    void startLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        this.finish();
    }

    public void addOptions(String type) {
        fm.beginTransaction()
                .replace(R.id.mainFrag, new AddFoodOptionFragment(type))
                .addToBackStack("addOptions").commit();
    }

    public void goToLog(String type) {
        fm.beginTransaction()
                .replace(R.id.mainFrag, new ShowLogFragment(type))
                .addToBackStack("showLogF").commit();
    }

    public void searchFood(String type) {
        fm.beginTransaction()
                .replace(R.id.mainFrag, new SearchFragment(type))
                .addToBackStack("searchF").commit();

    }

    public void addFood(Food f, String type) {
        fm.beginTransaction()
                .replace(R.id.mainFrag, new AddFoodFragment(f, type))
                .addToBackStack("addF").commit();
    }
}