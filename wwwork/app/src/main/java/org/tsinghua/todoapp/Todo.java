package org.tsinghua.todoapp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Todo {

    private String content = "";


    private String name = "";
    private String time = "";

    private String title = "";

    private String like = "";
    private int id = 0 ;





    public Todo(String the_name,String the_time,String the_title,String the_content,String the_like,int the_id) {//new
        name = the_name;
        time = the_time;
        title = the_title;
        content = the_content;
        like = the_like;
        id = the_id;

    }




    public String getContent() {
        return content;
    }
    public int getId(){return id;}



    public String getName(){ return name; }
    public String getTime(){ return time; }

    public String getTitle(){ return title; }

    public String getLike() {
        return like;
    }
}
