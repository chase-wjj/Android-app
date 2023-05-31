package org.tsinghua.todoapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class TodoListAdapter extends RecyclerView.Adapter<TodoViewHolder> {
    private final TodoList todoList;
    private final LayoutInflater inflater;

    public TodoListAdapter(Context context, TodoList todoList) {
        inflater = LayoutInflater.from(context);
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate an item view.
        View mItemView = inflater.inflate(
                R.layout.todolist_item, parent, false);
        return new TodoViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {//new
        // Retrieve the data for that position.
        /*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String current = todoList.get(position).getContent();
        Date date = todoList.get(position).getCreateAT();
        // Add the data to the view holder.
        holder.todoItemView.setText(current);
        holder.todoTimeView.setText(simpleDateFormat.format(date));*/
        String name = todoList.get(position).getName();
        String time = todoList.get(position).getTime();
        String title = todoList.get(position).getTitle();
        String current = todoList.get(position).getContent();
        holder.contentView.setText(current);
        holder.nameItemView.setText(name);
        holder.timeTimeView.setText(time);
        holder.titleView.setText(title);
        holder.titleView.setTextSize(24);
        int[] arr = todoList.get(position).getArr();
        holder.image1View.setImageResource(arr[0]);
        holder.image1View.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.image2View.setImageResource(arr[1]);
        holder.image2View.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.image3View.setImageResource(arr[2]);
        holder.image3View.setScaleType(ImageView.ScaleType.CENTER_CROP);

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public TodoList getTodoList() {
        return todoList;
    }
}

class TodoViewHolder extends RecyclerView.ViewHolder{
    public final TextView nameItemView;
    public final TextView timeTimeView;
    public final TextView titleView;
    public final TextView contentView;
    public final ImageView image1View;
    public final ImageView image2View;
    public final ImageView image3View;



    public TodoViewHolder(@NonNull View itemView, TodoListAdapter adapter) {
        super(itemView);
        nameItemView = itemView.findViewById(R.id.name_textView);
        timeTimeView = itemView.findViewById(R.id.time_textView);
        titleView = itemView.findViewById(R.id.title_textView);
        contentView = itemView.findViewById(R.id.textView12);
        image1View = itemView.findViewById(R.id.imageView2);
        image2View = itemView.findViewById(R.id.imageView3);
        image3View = itemView.findViewById(R.id.imageView4);
        /*todoItemView = itemView.findViewById(R.id.todo);
        todoTimeView = itemView.findViewById(R.id.totime);
        ImageButton imageButton = itemView.findViewById(R.id.imageButton);*/
        titleView.setOnClickListener(view -> {
            // Get the position of the item that was clicked.
            int position = getLayoutPosition();
            Intent intent = new Intent(view.getContext(),SecondActivity.class);
            String[] message = {"","","",""};
            message[0] = adapter.getTodoList().get(position).getName();
            message[1] = adapter.getTodoList().get(position).getTime();
            message[2] = adapter.getTodoList().get(position).getTitle();
            message[3] = adapter.getTodoList().get(position).getContent();
            int[] message_res = adapter.getTodoList().get(position).getArr();

            intent.putExtra("CONTENT_MESSAGE", message);
            intent.putExtra("ARR_MESSAGE", message_res);
            view.getContext().startActivity(intent);
            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.

        });

    }
}