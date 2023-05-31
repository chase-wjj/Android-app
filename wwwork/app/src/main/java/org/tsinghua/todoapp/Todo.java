package org.tsinghua.todoapp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Todo {
    private int number = 0;
    private String content = "";
    private Date createAT = new Date();//new

    private String name = "";
    private String time = "";

    private String title = "";

    private int[] arr = {0,0,0};



    public Todo(String the_name,String the_time,String the_title,String the_content,int[] the_arr) {//new
        name = the_name;
        time = the_time;
        title = the_title;
        arr = the_arr;
        content = the_content;
    }


    public int getNumber() {
        return number;
    }

    public String getContent() {
        return content;
    }

    public Date getCreateAT(){ return createAT; }//new

    public String getName(){ return name; }
    public String getTime(){ return time; }

    public String getTitle(){ return title; }
    public int[] getArr(){ return arr; }
}
