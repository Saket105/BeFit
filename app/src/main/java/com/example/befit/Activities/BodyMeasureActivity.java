package com.example.befit.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.befit.Animation.Anim;
import com.example.befit.MainActivity;
import com.example.befit.R;
import com.example.befit.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class BodyMeasureActivity extends AppCompatActivity {

    Spinner spinner;
    SeekBar seekBar;
    String gender1;
    CircleImageView boy, girl;
    EditText edt_height;
    TextView txt_weight;
    FloatingActionButton floatingActionButton;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_body_measure);

        spinner = findViewById(R.id.spinner);
        boy = findViewById(R.id.profile_boy);
        girl = findViewById(R.id.profile_girl);
        edt_height = findViewById(R.id.give_height);
        seekBar = findViewById(R.id.weight_bar);
        txt_weight = findViewById(R.id.weight_text);
        floatingActionButton = findViewById(R.id.fab);
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");

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

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt_weight.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUser = mAuth.getCurrentUser();
                update_profile(firebaseUser);
            }
        });

    }

    private void update_profile(final FirebaseUser firebaseUser) {
        final String sex = gender1;
        final String weight = txt_weight.getText().toString();
        final String height = edt_height.getText().toString();
        mAuth.updateCurrentUser(firebaseUser)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BodyMeasureActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    String userId = firebaseUser.getUid();
                    String userName = getIntent().getStringExtra("name");
                    String userEmail = getIntent().getStringExtra("email");
                    String userPhone = getIntent().getStringExtra("phone");
                    String userAge = getIntent().getStringExtra("age");
                    reference = FirebaseDatabase
                            .getInstance()
                            .getReference("Users")
                            .child(userId)
                            .child("personal_data");

                    User user = new User(
                            userName,
                            userEmail,
                            userPhone,
                            userAge,
                            sex,
                            weight,
                            height
                    );
                    reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(BodyMeasureActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(BodyMeasureActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

}