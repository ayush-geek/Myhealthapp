package com.example.myhealthapp.log;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myhealthapp.MainActivity;
import com.example.myhealthapp.R;

public class AddFoodOptionFragment extends Fragment {
    String type;

    public AddFoodOptionFragment(String type) {
        // Required empty public constructor
        this.type = type;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View thisView = inflater.inflate(R.layout.fragment_add_food_option, container, false);

        Button man = thisView.findViewById(R.id.manually);
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).searchFood(type);
            }
        });

        return thisView;
    }
}