package com.example.befit.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.befit.Animation.Anim;
import com.example.befit.MainActivity;
import com.example.befit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button signIn;
    TextView reg_page;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    Timer timer;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.sign_email);
        password = findViewById(R.id.sign_password);
        signIn = findViewById(R.id.signIn_user);
        reg_page = findViewById(R.id.tv);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.signInProcessBar);
        reference = FirebaseDatabase.getInstance().getReference();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        reg_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                        finish();
                        Anim.animateFade(LoginActivity.this);
                    }
                },1000);
            }
        });
    }

    private void loginUser() {
        String emails, pwd;
        emails = email.getText().toString();
        pwd = password.getText().toString();

        if (emails.isEmpty()){

            email.setError("Required!");
            email.requestFocus();
            return;

        }else if (!Patterns.EMAIL_ADDRESS.matcher(emails).matches()){

            email.setError("Please provide valid email address");
            email.requestFocus();
            return;

        }else if (pwd.length() < 6){

            password.setError("Please provide valid password");
            password.requestFocus();
            return;

        }else if (pwd.isEmpty()){
            password.setError("Required!");
            password.requestFocus();
            return;

        }else {
            progressBar.setVisibility(View.VISIBLE);
            logUser(emails,pwd);
        }
    }

    private void logUser(String emails, String pwd) {
        mAuth.signInWithEmailAndPassword(emails, pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //FirebaseUser user = mAuth.getCurrentUser();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this,"Ok! You are Logged In", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, BodyMeasureActivity.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}