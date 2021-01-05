/* Singleton stores configuration data for each game, etc. gameboard, cells Info*/

package com.example.a3.model;
import java.util.Random;

public class Singleton {
    //1 represents mine, 0 otherwise
    private int numRows=4;
    private int numCols=7;
    private int[] gameboard;
    private int numMines=8;
    //constructor
    public Singleton(){
        numRows=4;numCols=7;gameboard= new int[numRows*numCols];numMines=8;
        setGameboard(this.numMines);
    }
    public Singleton(int row,int col,int mines){
        this.numRows=row;
        this.numCols=col;
        this.gameboard= new int[numRows*numCols];
        this.numMines=mines;
        setGameboard(this.numMines);
    }
    //setter
    public void setGameboard(int Mines){
        Random rand=new Random();
        int Nmines=Mines;
        int randNum;
        for(int i=0;i<numRows*numCols;i++){
            this.gameboard[i]=0;
        }
        // random generate mines
        while(Nmines>0){
            randNum=rand.nextInt(numRows*numCols);
            if(this.gameboard[randNum]!=1){
                this.gameboard[randNum]=1;
                Nmines--;
            }
        }
    }

    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }
    //getter
    public int[] getGameboard(){
        return gameboard;
    }
    public int getNumRows(){
        return numRows;
    }
    public int getNumCols(){
        return numCols;
    }
    public int getNumMines(){
        return numMines;
    }
}
