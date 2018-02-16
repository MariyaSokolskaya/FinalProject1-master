package com.example.pasha.finalproject1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView logo = (ImageView) findViewById(R.id.Logo);
        Animation starAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo.startAnimation(starAnim);

        intent = new Intent(SplashActivity.this, MainActivity.class);



        starAnim.setAnimationListener(new Animation.AnimationListener() {


            public void onAnimationStart(Animation animation) {}


            public void onAnimationRepeat(Animation animation) {}


            public void onAnimationEnd(Animation animation) {


// TODO Auto-generated method stub


                startActivity(intent);


/* Чтобы по нажатию на кнопку «Назад» нельзя было снова попасть на экран
с заставкой приложения, вызовем метод finish()*/


                SplashActivity.this.finish();





            }


        });


    }
}

