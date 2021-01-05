package com.example.a3;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.a3.model.GetNumberInString;
import com.example.a3.model.myDialogue;
import com.example.a3.model.Singleton;

public class myGame extends AppCompatActivity {
    int numRows=4;
    int numCols=7;
    int numMines=8;
    Animation topAnim, bottomAnim, rotateAnim,shakeAnime;

    private SoundPool soundPool;
    private int s_mine;
    private int s_scan;
    private MediaPlayer music;
    Singleton newRound=new Singleton(4,7,8);
    int Score=0;
    int numScan=0;
    int remainMines=8;
    int numGames=1;
    int[][] gameBoard;//1 is mines,0 otherwise
    int[][] tappedCells;//1 is tapped,0 otherwise
    int[][] hiddenMines;//number of hidden mines in its row and col
    int[][] tappedMines;//1 is tapped,0 otherwise
    Button[][] gameButton;// store all dynamic buttons
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        shakeAnime = AnimationUtils.loadAnimation(this,R.anim.shake);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameboard);
        myInit();
        populateButtons();
    }
    @SuppressLint("NewApi")
    private void initSound() {
        soundPool = new SoundPool.Builder().build();
        s_mine = soundPool.load(this, R.raw.findmine, 1);
        s_scan = soundPool.load(this, R.raw.scan, 1);
    }
    private void playMusic(int id){
        music=MediaPlayer.create(this,id);
        music.start();
    }

    private void myInit() {
        initSound();
        numGames=MainActivity.getNumGames(this);
        TextView numG=findViewById(R.id.numGame);

        numG.setText("Times played "+numGames);

        GetNumberInString temp=new GetNumberInString();
        numCols=temp.getCol(myOption.getSize(this));
        numRows=temp.getRow(myOption.getSize(this));
        numMines=myOption.getNummines(this);
        gameBoard=new int[numRows][numCols];
        tappedCells=new int[numRows][numCols];
        tappedMines=new int[numRows][numCols];
        hiddenMines=new int[numRows][numCols];
        newRound=new Singleton(numRows,numCols,numMines);
        gameButton=new Button[numRows][numCols];
        numScan=0;
        Score=numRows*numCols;
        remainMines=numMines;
        for(int i=0;i<numRows;i++){
            for(int j=0;j<numCols;j++){
                gameBoard[i][j]=(newRound.getGameboard())[i*numCols+j];
                tappedCells[i][j]=0;
                tappedMines[i][j]=0;
            }
        }
        for(int i=0;i<numRows;i++){
            for(int j=0;j<numCols;j++){
                hiddenMines[i][j]=getNummines(i,j);
            }
        }
    }

    private int getNummines(int row, int col) {
        int numM=0;
        int verti=row;
        int hori=col;
        //four direction
        //up
        verti=row;
        while(verti>0){
            verti--;
            if(isMine(verti,col)==true&&isTapped(verti,col)==false){
                numM++;
            }
        }
        //down
        verti=row;
        while(verti<numRows-1){
            verti++;
            if(isMine(verti,col)==true&&isTapped(verti,col)==false){
                numM++;
            }
        }
        //left
        hori=col;
        while(hori>0){
            hori--;
            if(isMine(row,hori)==true&&isTapped(row,hori)==false){
                numM++;
            }
        }
        //right
        hori=col;
        while(hori<numCols-1){
            hori++;
            if(isMine(row,hori)==true&&isTapped(row,hori)==false){
                numM++;
            }
        }
        return numM;
    }

    private void populateButtons() {
        TextView remain_m=findViewById(R.id.numMine);
        remain_m.setText("Found "+(numMines - remainMines)+ " of "+numMines+" mines");

        TextView numSc=findViewById(R.id.numScan);
        numSc.setText("# Scans used: "+numScan);
        TableLayout table=findViewById(R.id.tableForbuttons);
        for(int row=0;row<numRows;row++){
            TableRow tableRow=new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);
            for(int col=0;col<numCols;col++){
                final int curCol=col;
                final int curRow=row;
                Button button=new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refreshscreen(curRow,curCol);


                    }
                });
                tableRow.addView(button);
                gameButton[row][col]=button;// store current button
            }
        }
    }

    private void refreshscreen(int row,int col) {
        lockButtonSizes();
        //4 cases: Mines or not mines, is tapped or not
        rotateAnim = AnimationUtils.loadAnimation(this,R.anim.rotate1);
        for(int i = 0; i < numCols;i++){
            gameButton[row][i].setAnimation(rotateAnim);
        }
        rotateAnim = AnimationUtils.loadAnimation(this,R.anim.rotate1);
        for(int i = 0; i< numRows;i++)
        {
            gameButton[i][col].setAnimation(rotateAnim);
        }
        if(isMine(row,col)==true&&isTapped(row,col)==true){
            playscan();

            tappedMines[row][col]=1;
            gameButton[row][col].setText(""+hiddenMines[row][col]);
            numScan++;
            TextView numSc=findViewById(R.id.numScan);
            numSc.setText("# Scans used: "+numScan);
        }
        else if(isMine(row,col)==true&&isTapped(row,col)==false){
            playmine();
            tappedCells[row][col]=1;
            remainMines--;
            refreshrelativeButton(row,col);
            TextView remain_m=findViewById(R.id.numMine);
            remain_m.setText("Find "+remainMines+ " of "+numMines+" mines");
            //https://developer.android.com/topic/performance/graphics/load-bitmap
            Bitmap sampleSize= BitmapFactory.decodeResource(getResources(),R.drawable.mine);
            int imageHeight=gameButton[row][col].getHeight();
            int imageWidth=gameButton[row][col].getWidth();
            Bitmap requiredSize=Bitmap.createScaledBitmap(sampleSize,imageWidth,imageHeight,true);
            Resources resource=getResources();
            gameButton[row][col].setBackground(new BitmapDrawable(resource,requiredSize));
        }
        else if(isMine(row,col)==false&&isTapped(row,col)==true){

        }
        else if(isMine(row,col)==false&&isTapped(row,col)==false){
            playscan();

            numScan++;
            TextView numSc=findViewById(R.id.numScan);
            numSc.setText("# Scans used: "+numScan);
            tappedCells[row][col]=1;
            gameButton[row][col].setText(""+hiddenMines[row][col]);
        }
        if (remainMines == 0) {
            FragmentManager manager=getSupportFragmentManager();
            myDialogue dialog=new myDialogue();
            dialog.show(manager,"End Game");
            //Log.i("Tag","Just show the dialog");

        }
    }

    private void refreshrelativeButton(int row, int col) {
        int verti=row;
        int hori=col;
        //four direction
        //up
        verti=row;
        while(verti>0){
            verti--;
            hiddenMines[verti][col]--;
            if((isTapped(verti,col)==true&&isTappedMine(verti,col)==true)||(isTapped(verti,col)==true&&isMine(verti,col)==false)){gameButton[verti][col].setText(""+hiddenMines[verti][col]);}
        }
        //down
        verti=row;
        while(verti<numRows-1){
            verti++;
            hiddenMines[verti][col]--;
            if((isTapped(verti,col)==true&&isTappedMine(verti,col)==true)||(isTapped(verti,col)==true&&isMine(verti,col)==false)){gameButton[verti][col].setText(""+hiddenMines[verti][col]);}
        }
        //left
        hori=col;
        while(hori>0){
            hori--;
            hiddenMines[row][hori]--;
            if((isTapped(row,hori)==true&&isTappedMine(row,hori)==true)||(isTapped(row,hori)==true&&isMine(row,hori)==false)){gameButton[row][hori].setText(""+hiddenMines[row][hori]);}
        }
        //right
        hori=col;
        while(hori<numCols-1){
            hori++;
            hiddenMines[row][hori]--;
            if((isTapped(row,hori)==true&&isTappedMine(row,hori)==true)||(isTapped(row,hori)==true&&isMine(row,hori)==false)){gameButton[row][hori].setText(""+hiddenMines[row][hori]);}
        }
    }

    private boolean isTapped(int row, int col) {
        if(tappedCells[row][col]==1){
            return true;
        }
        return false;
    }
    private boolean isTappedMine(int row, int col) {
        if(tappedMines[row][col]==1){
            return true;
        }
        return false;
    }

    private boolean isMine(int row, int col) {
        if(gameBoard[row][col]==1){
            return true;
        }
        return false;
    }

    private void lockButtonSizes() {
        for(int row=0;row<numRows;row++){
            for(int col=0;col<numCols;col++){
                Button button=gameButton[row][col];

                int width=button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height=button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }
    private void playmine() {
        soundPool.play(
                s_mine,
                1f,
                1f,
                0,
                0,
                1

        );
    }
    private void playscan() {
        soundPool.play(
                s_scan,
                1f,
                1f,
                0,
                0,
                1

        );
    }

    /*
    private void saveScore(int score) {
        SharedPreferences prefs=this.getSharedPreferences("AppPref4",MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putInt("Score",Score);
        editor.apply();
    }

    static public int getScore(View.OnClickListener context){
        SharedPreferences prefs=context.getSharedPreferences("AppPref4",MODE_PRIVATE);
        return prefs.getInt("Score",0);
    }*/
}
