package org.tsinghua.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private final CommentList commentList;
    private final LayoutInflater inflater;

    public CommentAdapter(Context context, CommentList List) {
        inflater = LayoutInflater.from(context);
        this.commentList = List;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate an item view.
        View mItemView = inflater.inflate(
                R.layout.comment_item, parent, false);
        return new CommentViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position){
        String name = commentList.get(position).getUsername();
        String time = commentList.get(position).getTime();
        String content = commentList.get(position).getContent();
        holder.timeText.setText(time);
        holder.commentText.setText(content);
        holder.name.setText(name);

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}

class CommentViewHolder extends RecyclerView.ViewHolder {
    TextView commentText;

    TextView name;

    TextView timeText;



    public CommentViewHolder(@NonNull View itemView,CommentAdapter adapter) {
        super(itemView);
        commentText = itemView.findViewById(R.id.textView17);
        name = itemView.findViewById(R.id.textView16);
        timeText = itemView.findViewById(R.id.textView8);
    }

}