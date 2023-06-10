package org.tsinghua.todoapp;

import java.util.ArrayList;

public class MessageList {

    private ArrayList<Message> data = new ArrayList<>();

    public MessageList(){

    }

    public void insert(String scontent,String susername, String stime,String sid){
        data.add(new Message(scontent,susername,stime,sid));
    }

    public int size(){
        return data.size();
    }
    public Message get(int index){
        return data.get(index);
    }

}