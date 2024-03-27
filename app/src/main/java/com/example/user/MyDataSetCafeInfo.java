package com.example.user;

public class MyDataSetCafeInfo{
    public String text;
    public int image;
    public MyDataSetCafeInfo(String text, int image){
        this.text = text;
        this.image = image;
    }

    public int getImage(){
        return  image;
    }
    public String getText(){
        return text;
    }
}
