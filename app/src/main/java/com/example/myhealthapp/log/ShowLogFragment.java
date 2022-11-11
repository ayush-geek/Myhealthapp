package com.example.myhealthapp.log;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myhealthapp.MainActivity;
import com.example.myhealthapp.R;

public class ShowLogFragment extends Fragment {
    String type;

    public ShowLogFragment(String type) {
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
        View thisView = inflater.inflate(R.layout.fragment_show_log, container, false);

        TextView tv = thisView.findViewById(R.id.logTypeHeading);
        switch (this.type) {
            case "breakfast":
                tv.setText(getResources().getString(R.string.bFast));
                break;
            case "lunch":
                tv.setText(getResources().getString(R.string.lun));
                break;
            case "dinner":
                tv.setText(getResources().getString(R.string.din));
                break;
            case "miscellaneous":
                tv.setText(getResources().getString(R.string.misc));
                break;
            default:
                tv.setText(getResources().getString(R.string.logTitle));
        }

        Button addFood = thisView.findViewById(R.id.addFood);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).addOptions(type);
            }
        });

        return thisView;
    }
}