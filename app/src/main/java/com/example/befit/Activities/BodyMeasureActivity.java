package com.example.befit.Activities;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.befit.Animation.Anim;
import com.example.befit.R;

import java.util.ArrayList;
import java.util.List;

public class BodyMeasureActivity extends AppCompatActivity {

    Spinner spinner;
    SeekBar weight_bar;
    String gender1;
    CircleImageView boy, girl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_body_measure);

        spinner = findViewById(R.id.spinner);
        boy = findViewById(R.id.profile_boy);
        girl = findViewById(R.id.profile_girl);

        final List<String> gender = new ArrayList<>();
        gender.add("Male");
        gender.add("Female");
        gender.add("Other");
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,gender);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
                switch (position) {
                    case 0:
                        gender1 = "Male";
                        boy.setVisibility(View.VISIBLE);
                        girl.setVisibility(View.GONE);
                        break;
                    case 1:
                        gender1 = "Female";
                        boy.setVisibility(View.GONE);
                        girl.setVisibility(View.VISIBLE);

                        break;
                    case 2:
                        gender1 = "Others";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(BodyMeasureActivity.this, "Please Provide your gender", Toast.LENGTH_SHORT).show();
            }
        });

    }
}