package com.example.befit.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.befit.Animation.Anim;
import com.example.befit.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    Button signIn, signUp;
    private static int SPLASH_SCREEN = 4000;
    RelativeLayout splashImage;
    Animation bottom;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_splash_screen);

//        signIn = findViewById(R.id.splash_signin1);
//        signUp = findViewById(R.id.splash_signup1);
//        bottom = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                Anim.animateSlideUp(SplashScreen.this);
                finish();
            }
        },2500);

//        signIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SplashScreen.this,LoginActivity.class));
//            }
//        });
//
//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SplashScreen.this,RegisterActivity.class));
//            }
//        });
    }
}