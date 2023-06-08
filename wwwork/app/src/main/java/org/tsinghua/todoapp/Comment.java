package org.tsinghua.todoapp;

public class Comment {



    private String content;

    private String username;

    private String time;

    private String id;

    Comment(String scontent,String susername,String stime,String sid){
        this.content = scontent;
        this.time = stime;
        this.username = susername;
        this.id = sid;

    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }



    public String getUsername() {
        return username;
    }
}
