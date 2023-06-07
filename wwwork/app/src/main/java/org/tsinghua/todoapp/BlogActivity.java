package org.tsinghua.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class BlogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        Intent intent = getIntent();
        String[] message = intent.getStringArrayExtra("CONTENT_MESSAGE");
        int[] message_res = intent.getIntArrayExtra("ARR_MESSAGE");
        TextView nameView;
        TextView TimeView;
        TextView TitleView;
        TextView ContentView;
        nameView = findViewById(R.id.name_textView);
        TimeView = findViewById(R.id.time_textView);
        TitleView = findViewById(R.id.title_textView);
        ContentView = findViewById(R.id.textView12);
        nameView.setText(message[0]);
        TimeView.setText(message[1]);
        TitleView.setText(message[2]);
        ContentView.setText(message[3]);

        ImageView image1View = findViewById(R.id.imageView2);
        ImageView image2View = findViewById(R.id.imageView3);
        ImageView image3View = findViewById(R.id.imageView4);

        image1View.setImageResource(message_res[0]);
        image2View.setImageResource(message_res[1]);
        image3View.setImageResource(message_res[2]);
    }
}
