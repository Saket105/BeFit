package com.example.befit.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends AppCompatActivity {

    Button signup;
    FirebaseAuth mAuth;
    String gender1;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    EditText edt_names, edt_emails, edt_phones, edt_password, edt_age;
    TextView tv;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_register);

        signup = findViewById(R.id.register_user);
        edt_names = findViewById(R.id.name);
        edt_emails = findViewById(R.id.email);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        edt_phones = findViewById(R.id.phone);
        edt_password = findViewById(R.id.password);
        edt_age = findViewById(R.id.text_age);
        progressBar = findViewById(R.id.progress_bar);
        tv = findViewById(R.id.reg_tv);
        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//                Toast.makeText(RegisterActivity.this, "Register SuccessFull", Toast.LENGTH_SHORT).show();
                registerUser();
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        finish();
                        Anim.animateFade(RegisterActivity.this);
                    }
                },1000);
            }
        });

    }

    private void registerUser() {
        final String email = edt_emails.getText().toString().trim();
        final String name = edt_names.getText().toString().trim();
        final String phone = edt_phones.getText().toString().trim();
        String pwd = edt_password.getText().toString().trim();
        final String age = edt_age.getText().toString().trim();

        if (email.isEmpty()) {
            edt_emails.setError("Required!");
            edt_emails.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_emails.setError("Please provide valid email address!");
            edt_emails.requestFocus();
            return;
        } else if (name.isEmpty()) {
            edt_names.setError("Required!");
            edt_names.requestFocus();
            return;
        } else if (phone.isEmpty()) {
            edt_phones.setError("Required!");
            edt_phones.requestFocus();
            return;
        } else if (phone.length() < 10) {
            edt_phones.setError("Invalid Number");
            edt_phones.requestFocus();
            return;
        } else if (pwd.isEmpty()) {
            edt_password.setError("Can't be empty");
            edt_password.requestFocus();
            return;
        } else if (pwd.length() < 6) {
            edt_password.setError("Minimum 6 character");
            edt_password.requestFocus();
            return;
        } else if (age.isEmpty()) {
            edt_age.setError("Required!");
            edt_age.requestFocus();
            return;
        } else {
            progressBar.setVisibility(View.VISIBLE);
            registeringUser(email,pwd);
        }

    }

    private void registeringUser(String email, String pwd) {
        mAuth.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String userId = firebaseUser.getUid();

                            databaseReference = FirebaseDatabase
                                    .getInstance()
                                    .getReference("Users")
                                    .child(userId)
                                    .child("personal_data");

                            User user = new User(
                                    edt_names.getText().toString(),
                                    edt_emails.getText().toString(),
                                    edt_phones.getText().toString(),
                                    edt_age.getText().toString(),
                                    "",
                                    "",
                                    ""
                            );

                            databaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(RegisterActivity.this,BodyMeasureActivity.class)
                                                .putExtra("name",edt_names.getText().toString())
                                                .putExtra("email",edt_emails.getText().toString())
                                                .putExtra("phone",edt_phones.getText().toString())
                                                .putExtra("age",edt_age.getText().toString()));
                                        finish();
                                        Toast.makeText(RegisterActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}