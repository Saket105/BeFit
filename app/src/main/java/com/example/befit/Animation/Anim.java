package com.example.befit.Animation;

import android.app.Activity;
import android.content.Context;

import com.example.befit.R;

public class Anim {
    public static void animateSlideUp(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.slide_up,R.anim.slide_up_exit);
    }

    public static void animateZoom(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.animate_zoom_enter, R.anim.animate_zoom_exit);
    }

    public static void animateFade(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.animate_fade_enter, R.anim.animate_fade_exit);
    }

    public static void animateCard(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.animate_card_enter, R.anim.animate_card_exit);
    }


}
