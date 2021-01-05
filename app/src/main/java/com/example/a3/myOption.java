package com.example.a3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class myOption extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        createRminesButtons();
        createRsizesButtons();
        createEraseButton();
    }

    private void createEraseButton() {
        final Button game=findViewById(R.id.Erase);
        game.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int erase=1;
                eraseNumGames(erase);
            }
        });
    }

    private void eraseNumGames(int erase) {
        SharedPreferences prefs=this.getSharedPreferences("AppPref5",MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putInt("Erase",erase);
        editor.apply();
    }
    static public int getErase(Context context){
        SharedPreferences prefs=context.getSharedPreferences("AppPref5",MODE_PRIVATE);
        return prefs.getInt("Erase",1);
    }

    private void createRsizesButtons() {
        RadioGroup group=(RadioGroup) findViewById(R.id.Rsizes);
        final String[] mysize=getResources().getStringArray(R.array.gameboard_size);
        for(int i=0;i<mysize.length;i++){
            final String curSize=mysize[i];
            RadioButton button=new RadioButton(this);
            button.setText(curSize);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Toast.makeText(myOption.this,"Choose "+curSize,Toast.LENGTH_SHORT).show();
                    saveSize(curSize);
                }
            });
            group.addView(button);
            if(curSize.equals(getSize(this))) {
                button.setChecked(true);
            }
        }
    }

    public static String getSize(Context context) {
        SharedPreferences prefs=context.getSharedPreferences("AppPref1",MODE_PRIVATE);
        return prefs.getString("Gameboard size","4 rows 6 cols");
    }

    private void saveSize(String curSize) {
        SharedPreferences prefs=this.getSharedPreferences("AppPref1",MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("Gameboard size",curSize);
        editor.apply();
    }


    private void createRminesButtons() {
        RadioGroup group=(RadioGroup) findViewById(R.id.Rmines);
        int[] num_mines=getResources().getIntArray(R.array.mines_size);
        for(int i=0;i<num_mines.length;i++){
            final int curMines=num_mines[i];
            RadioButton button=new RadioButton(this);
            button.setText(curMines+" mines");
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Toast.makeText(myOption.this,"Choose "+curMines,Toast.LENGTH_SHORT).show();
                    saveNummines(curMines);
                }
            });
            group.addView(button);
            if(curMines==getNummines(this)){
                button.setChecked(true);
            }
        }
    }

    private void saveNummines(int curMines) {
        SharedPreferences prefs=this.getSharedPreferences("AppPref2",MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putInt("Num mines",curMines);
        editor.apply();
    }

    static public int getNummines(Context context){
        SharedPreferences prefs=context.getSharedPreferences("AppPref2",MODE_PRIVATE);
        return prefs.getInt("Num mines",6);
    }


}
