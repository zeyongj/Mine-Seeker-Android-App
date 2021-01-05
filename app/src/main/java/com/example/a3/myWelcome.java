package com.example.a3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

// Codes of animation of rotate are inspired from the website: https://www.youtube.com/watch?v=goVoYf2qie0&ab_channel=CoderScionMedia.
// Codes of animation of move are inspired from the website: https://www.youtube.com/watch?v=JLIFqqnSNmg&ab_channel=CodingWithTea.
// Codes of splash screen are inspired from the website: https://www.youtube.com/watch?v=JLIFqqnSNmg&ab_channel=CodingWithTea.
// Codes of avoiding splash screen jumping again to the main menu on the welcome page are modified from the website: https://blog.csdn.net/qq_40116418/article/details/78796338 (Chinese Version).

public class myWelcome extends AppCompatActivity {
    private static int SPLASH_SCREEN = 8000; // 8 seconds

    Animation topAnim, bottomAnim, rotateAnim;
    ImageView imageView;
    TextView textView1, textView2;
    boolean notClicked = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomescreen);
        // Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        rotateAnim = AnimationUtils.loadAnimation(this,R.anim.rotate);
        imageView = findViewById(R.id.welcome_iv1);
        textView1 = findViewById(R.id.welcome_tv1);
        textView2 = findViewById(R.id.welcome_tv2);
        imageView.setAnimation(rotateAnim);
        textView1.setAnimation(topAnim);
        textView2.setAnimation(bottomAnim);
        Button game=findViewById(R.id.Skip);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(myWelcome.this,MainActivity.class);
                startActivity(intent);
                notClicked = false;
            }
        });
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(notClicked == true) {
                    Intent intent = new Intent(myWelcome.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_SCREEN);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notClicked = false;
    }

    // The following codes are copied from https://readyandroid.wordpress.com/exit-android-app-on-back-pressed/#:~:text=For%20that%20you%20need%20to,top%20activity%20in%20the%20stack.&text=in%20back%20button%20pressed%20you,method%20inside%20onBackPressed()%20method.
    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


}
