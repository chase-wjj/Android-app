package org.tsinghua.todoapp;

import java.util.ArrayList;
import java.util.Date;

public class TodoList {
    private ArrayList<Todo> data = new ArrayList<>();
    private int count = 0;


    public TodoList() {
    }

    public void insert(String the_name,String the_time,String the_title,String the_content,String the_like,int the_id) {

        ArrayList<Todo> new_data = new ArrayList<Todo>();
        new_data.add(new Todo(the_name,the_time,the_title,the_content,the_like,the_id));   //new
        for (int i = 0;i < count;i++){
            new_data.add(data.get(i));
        }
        data = new_data;
        count++;
        /*Date now_time = new Date(System.currentTimeMillis()); //new
        ArrayList<Todo> new_data = new ArrayList<Todo>();
        new_data.add(new Todo(count, content, now_time));   //new
        for (int i = 0;i < count;i++){
            new_data.add(data.get(i));
        }
        data = new_data;
        count++;

        for(int i = 0;i<count;i++){
            for(int j=i+1;j<count;j++){
                if(data.get(j-1).getCreateAT().compareTo(data.get(j).getCreateAT()) == -1 ){
                    Todo the_todo = data.get(j-1);


                }
            }
        }*/
    }

    public void delete(int number) {
        data.remove(number);
        count--;
    }

    public Todo get(int index) {
        return data.get(index);
    }

    public int size() {
        return data.size();
    }
}
