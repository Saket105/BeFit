package com.example.befit.Animation;

import android.app.Activity;
import android.content.Context;

import com.example.befit.R;

public class Anim {
    public static void animateSlideUp(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.slide_up,R.anim.slide_up_exit);
    }
}
