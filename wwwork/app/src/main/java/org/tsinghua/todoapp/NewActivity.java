package org.tsinghua.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        TextView name = findViewById(R.id.name_textView);
        TextView time = findViewById(R.id.time_textView);
        TextView title = findViewById(R.id.title_textView);
        TextView content = findViewById(R.id.textView12);
        ImageView image1 = findViewById(R.id.imageView2);
        ImageView image2 = findViewById(R.id.imageView4);
        ImageView image3 = findViewById(R.id.imageView3);
        image1.setImageResource(R.drawable.image10);
        image2.setImageResource(R.drawable.image11);
        image3.setImageResource(R.drawable.image12);
        image1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Button button = findViewById(R.id.button);
        /*button.setOnClickListener(view -> {
            Intent ReplyIntent = new Intent(view.getContext(),MainActivity.class);
            String[] mess = {name.getText().toString(),time.getText().toString(),title.getText().toString(),content.getText().toString()};
            ReplyIntent.putExtra("mess",mess);
            int[] res = {R.drawable.image10,R.drawable.image11,R.drawable.image12};
            ReplyIntent.putExtra("res",res);
            view.getContext().startActivity(ReplyIntent);
        });*/

    }
    public void returnReply(View view) {
        // Get the reply message from the edit text.


        // Create a new intent for the reply, add the reply message to it
        // as an extra, set the intent result, and close the activity.
        Intent replyIntent = new Intent();
        String mes = "1";
        replyIntent.putExtra("reply", mes);
        setResult(RESULT_OK, replyIntent);
        Log.d(LOG_TAG,"return value");
        finish();
    }
}
