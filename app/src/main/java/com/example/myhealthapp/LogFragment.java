package com.example.myhealthapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class LogFragment extends Fragment {
    ArrayList<String> bList, lList, dList, mList;

    public LogFragment(ArrayList<ArrayList<String>> data) {
        bList = data.get(0);
        lList = data.get(1);
        dList = data.get(2);
        mList = data.get(3);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_log, container, false);
//
//        ListView bLis = myView.findViewById(R.id.bFastList);
//        ListView lLis = myView.findViewById(R.id.lunList);
//        ListView dLis = myView.findViewById(R.id.dinList);
//        ListView mLis = myView.findViewById(R.id.miscList);
//
//        ArrayAdapter<String> ap;
//        ap = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, bList);
//        bLis.setAdapter(ap);
//
//        ap = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lList);
//        lLis.setAdapter(ap);
//
//        ap = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dList);
//        dLis.setAdapter(ap);
//
//        ap = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mList);
//        mLis.setAdapter(ap);
        return myView;
    }
}