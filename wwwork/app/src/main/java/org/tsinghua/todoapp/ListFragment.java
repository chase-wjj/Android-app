package org.tsinghua.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

public class ListFragment extends Fragment {
    private final TodoList todoList = new TodoList();
    private RecyclerView recyclerView;
    private TodoListAdapter adapter;
    private Button addButton;
    private TextInputLayout textInputLayout;

    private View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Put initial data into the word list.
        int[] arr1 = {R.drawable.image1,R.drawable.image2,R.drawable.image3};
        int[] arr2 = {R.drawable.image4,R.drawable.image5,R.drawable.image6};
        int[] arr3 = {R.drawable.image7,R.drawable.image8,R.drawable.image9};

        String text1 = "熙春园分为东西两园，工字厅以西部分称近春园。\n近春园园志上写着：“水木清华，为一时之繁囿胜地。”";
        String text2 = "广义上的紫荆操场还包括附近的网球场、篮球场和足球场.紫操是同学们课外锻炼的重要场地,\n也是清华大学”无体育,不清华“传统的见证.";
        String text3 = "动感的音乐火速快来！";

        todoList.insert("蛇皮卡丘怪","18:24:44","近春园春游",text1,arr1);
        todoList.insert("阿月","16:45:14","紫荆操场",text2,arr2);
        todoList.insert("Julian","9:21:11","河边公园音乐节",text3,arr3);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        addButton = view.findViewById(R.id.add_button);
        recyclerView = view.findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        adapter = new TodoListAdapter(getContext(), todoList);
        // Connect the adapter with the recycler view.
        recyclerView.setAdapter(adapter);
        // Give the recycler view a default layout manager.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String content = textInputLayout.getEditText().getText().toString();
                adapter.getTodoList().insert(content);
                textInputLayout.getEditText().setText("");*/
                Intent intent = new Intent(view.getContext(),NewActivity.class);
                view.getContext().startActivity(intent);
                String[] mess = {"不吃紫菜炒饭","19:53:44","鼓楼大街一日游","人真的好多 \\n 但是为什么麦当劳没卖杨枝甘露麦旋风"};
                int[] res = {R.drawable.image10,R.drawable.image11,R.drawable.image12};
                adapter.getTodoList().insert(mess[0],mess[1],mess[2],mess[3],res);
                adapter.notifyDataSetChanged();

            }
        });
        return view;
    }
}