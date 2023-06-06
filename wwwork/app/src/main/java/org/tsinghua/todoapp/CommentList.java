package org.tsinghua.todoapp;

import java.util.ArrayList;

public class CommentList {

    private ArrayList<Comment> data = new ArrayList<>();

    public CommentList(ArrayList<String> content,ArrayList<String> time,ArrayList<String> username){

    }

    public void insert(String scontent,String susername,String stime){
        data.add(new Comment(scontent,susername,stime));
    }

    public int size(){
        return data.size();
    }
    public void delete(int number) {
        data.remove(number);
    }
    public Comment get(int index){
        return data.get(index);
    }
}
