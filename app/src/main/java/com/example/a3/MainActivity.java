package com.example.a3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int numGames=0;
    int isErase=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInfo();
        Gamebutton();
        Optionbutton();
        Helpbutton();
    }

    private void setInfo() {

        isErase=myOption.getErase(this);
    }

    private void Gamebutton() {
        final Button game=findViewById(R.id.Game);
        game.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isErase==1){
                    numGames=0;
                    isErase=0;
                }
                numGames++;
                saveNumGames(numGames);
                Intent intent=new Intent(MainActivity.this,myGame.class);
                startActivity(intent);
            }
        });
    }

    private void Optionbutton() {
        final Button game=findViewById(R.id.Options);
        game.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,myOption.class);
                startActivity(intent);
            }
        });
    }

    private void Helpbutton() {
        final Button game=findViewById(R.id.Help);
        game.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,myHelp.class);
                startActivity(intent);
            }
        });
    }
    private void saveNumGames(int numGames) {
        SharedPreferences prefs=this.getSharedPreferences("AppPref3",MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putInt("Num games",numGames);
        editor.apply();
    }

    static public int getNumGames(Context context){
        SharedPreferences prefs=context.getSharedPreferences("AppPref3",MODE_PRIVATE);
        return prefs.getInt("Num games",0);
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