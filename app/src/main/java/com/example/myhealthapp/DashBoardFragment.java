package com.example.myhealthapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class DashBoardFragment extends Fragment {
    private PieChart pieChart;

    public DashBoardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myV = inflater.inflate(R.layout.fragment_dash_board, container, false);

        pieChart = myV.findViewById(R.id.pie_chart);
        drawPC();

        return myV;
    }

    private void drawPC() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        pieEntries.add(new PieEntry(1000, ""));
        pieEntries.add(new PieEntry(500, ""));

        Description desc = new Description();
        desc.setText("");

        pieChart.setDescription(desc);

        PieDataSet pds = new PieDataSet(pieEntries, "");

        ArrayList<Integer> cols = new ArrayList<>();
        cols.add(Color.parseColor("#FFAB00"));
        cols.add(Color.parseColor("#151724"));

        pds.setColors(cols);

        PieData pieData = new PieData(pds);
        pieChart.setData(pieData);
        pieChart.getLegend().setEnabled(false);
        pieChart.setHoleRadius(90);
        pieChart.setCenterText(String.format("%d", 1500));
        pieChart.setCenterTextColor(getResources().getColor(R.color.white));
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
        pieChart.setCenterTextSize(32);
        pieChart.setHoleColor(Color.parseColor("#242833"));

        pieChart.setHighlightPerTapEnabled(false);
        pds.setDrawValues(false);
        pieChart.invalidate();

        pieChart.animateXY(1000, 1000);
    }
}