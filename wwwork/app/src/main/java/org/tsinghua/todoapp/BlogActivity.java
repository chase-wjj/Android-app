package org.tsinghua.todoapp;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class BlogActivity extends AppCompatActivity {
    private CommentList commentList = new CommentList();

    Button comment;
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        Intent intent = getIntent();
        String[] message = intent.getStringArrayExtra("CONTENT_MESSAGE");
        int blogid = intent.getIntExtra("Blog_ID",-1);
        Log.v("get blog_id",String.valueOf(blogid));
        TextView nameView = findViewById(R.id.name_textView);
        TextView timeView = findViewById(R.id.time_textView);
        TextView titleView = findViewById(R.id.title_textView);
        TextView contentView = findViewById(R.id.textView12);
        nameView.setText(message[0]);
        timeView.setText(message[1]);
        titleView.setText(message[2]);
        contentView.setText(message[3]);

        comment = findViewById(R.id.button3);

        recyclerView = findViewById(R.id.recyclerView);
        commentList.insert("大佬666","LiHua","18:51:12");
        commentList.insert("大佬777","WangLei","15:41:11");
        commentAdapter = new CommentAdapter(this,commentList);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spacing = -20;
        recyclerView.addItemDecoration(new MyItemDecoration(spacing));



    }
    public class MyItemDecoration extends RecyclerView.ItemDecoration {
        private final int verticalSpaceHeight;

        public MyItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
        }
    }

}
