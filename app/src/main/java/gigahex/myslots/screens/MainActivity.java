package gigahex.myslots.screens;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import gigahex.myslots.R;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EasySplashScreen config = new EasySplashScreen(MainActivity.this)
                .withFullScreen()
                .withTargetActivity(TargetActivity.class)
                .withSplashTimeOut(4000)
                .withFooterText("Copyright 2018")
                .withLogo(R.drawable.padlock_main)
                .withHeaderText("Слоты замков");


        //set your own animations
        myCustomTextViewAnimation(config.getFooterTextView());

        //customize all TextViews
        Typeface pacificoFont = Typeface.createFromAsset(getAssets(), "Pacifico.ttf");
        Typeface proItalicFont = Typeface.createFromAsset(getAssets(), "Guardian Pro Italic.ttf");
        config.getHeaderTextView().setTypeface(pacificoFont);
        config.getFooterTextView().setTypeface(proItalicFont);

        config.getHeaderTextView().setTextColor(Color.WHITE);
        config.getHeaderTextView().setTextSize(30f);
        config.getFooterTextView().setTextColor(Color.WHITE);
        //create the view
        View easySplashScreenView = config.create();

        setContentView(easySplashScreenView);
    }

    private void myCustomTextViewAnimation(TextView tv){
        Animation animation=new TranslateAnimation(0,0,480,0);
        animation.setDuration(1200);
        tv.startAnimation(animation);
    }
}
