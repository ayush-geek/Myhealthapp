package com.example.myhealthapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GetData extends ViewModel {
    private MutableLiveData<ArrayList<ArrayList<String>>> data;
    private String userID;

    public void setUserID(String name) {
        userID = name;
    }

    private void getData() {
        ArrayList<String> d1 = new ArrayList<>(Collections.singletonList("Add new"));
        d1.add("a");
        d1.add("a");
        d1.add("a");
        d1.add("a");
        d1.add("a");
        d1.add("a");
        d1.add("a");
        d1.add("a");
        d1.add("a");
        d1.add("a");
        d1.add("a");


        ArrayList<String> d2 = new ArrayList<>(Collections.singletonList("Add new"));
        ArrayList<String> d3 = new ArrayList<>(Collections.singletonList("Add new"));
        ArrayList<String> d4 = new ArrayList<>(Collections.singletonList("Add new"));
        if (userID != null) {
            // make API calls to get the Data
            data = new MutableLiveData<>();
            data.setValue(new  ArrayList<>(Arrays.asList(d1, d2, d3, d4)));
        }
    }

    public LiveData<ArrayList<ArrayList<String>>> getUserData() {
        getData();

        return data;
    }
}
