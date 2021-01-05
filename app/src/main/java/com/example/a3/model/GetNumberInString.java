/* For input 4 rows 6 cols (size of gameboard), return number of rows and cols*/

package com.example.a3.model;

public class GetNumberInString {
    public GetNumberInString(){}
    public int getRow(String arg){
        String bdsize=arg;
        String[] temp= bdsize.split(" ");
        return Integer.parseInt(temp[0]);
    }
    public int getCol(String arg){
        String bdsize=arg;
        String[] temp= bdsize.split(" ");
        return Integer.parseInt(temp[2]);
    }
}
