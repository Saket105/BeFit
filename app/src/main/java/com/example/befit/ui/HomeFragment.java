package com.example.befit.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.befit.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    LineChart activity_chart;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        activity_chart = root.findViewById(R.id.activity_line_chart);

        activity_chart.setDragEnabled(true);
        activity_chart.setScaleEnabled(false);
        ArrayList<Entry> yValues = new ArrayList<>();

        yValues.add(new Entry(0,30f));
        yValues.add(new Entry(2,120f));
        yValues.add(new Entry(4,50f));
        yValues.add(new Entry(6,150f));
        LineDataSet lineDataSet = new LineDataSet(yValues,"Data set 1");
        lineDataSet.setFillAlpha(200);
        lineDataSet.setColor(Color.parseColor("#ffffff"));
        lineDataSet.setLineWidth(1f);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);
        activity_chart.setData(data);
        return root;
    }
}