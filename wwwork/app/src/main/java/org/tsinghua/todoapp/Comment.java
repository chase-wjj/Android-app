package org.tsinghua.todoapp;

public class Comment {



    private String content;

    private String username;

    private String time;

    Comment(String scontent,String susername,String stime){
        this.content = scontent;
        this.time = stime;
        this.username = susername;

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
