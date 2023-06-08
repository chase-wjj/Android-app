package org.tsinghua.todoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class BlogActivity extends AppCompatActivity {
    private CommentList commentList = new CommentList();

    Button comment;
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    EditText editText;

    public String username;

    public String blogid;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        SharedPreferences sharedPreferences = getSharedPreferences("loggeduser", MODE_PRIVATE);
        username = sharedPreferences.getString("username", ""); // 从SharedPreferences中获取用户名


        Intent intent = getIntent();
        String[] message = intent.getStringArrayExtra("CONTENT_MESSAGE");
        blogid = String.valueOf(intent.getIntExtra("Blog_ID",-1));

        TextView nameView = findViewById(R.id.name_textView);
        TextView timeView = findViewById(R.id.time_textView);
        TextView titleView = findViewById(R.id.title_textView);
        TextView contentView = findViewById(R.id.textView12);
        nameView.setText(message[0]);
        timeView.setText(message[1]);
        titleView.setText(message[2]);
        contentView.setText(message[3]);

        GetComment();

        comment = findViewById(R.id.button3);
        editText = findViewById(R.id.editText5);

        recyclerView = findViewById(R.id.recyclerView);


        commentAdapter = new CommentAdapter(this,commentList);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spacing = -20;
        recyclerView.addItemDecoration(new MyItemDecoration(spacing));
        Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddComment();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commentAdapter.notifyDataSetChanged();
                    }
                });
            }
        });



    }
    public void AddComment(){
        String requestUrl = "http://10.0.2.2:5000/blog/publishComment/";
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username",username).put("blog_id",blogid).put("content",editText.getText());
            @SuppressWarnings("deprecation") RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {}
                @Override
                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                    assert response.body() != null;
                    if (response.code() == 201){
                        Log.v("Add Comment","success!");
                        editText.setText("");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BlogActivity.this,"发表Blog成功！",Toast.LENGTH_LONG).show();
                            }
                        });
                    }


                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void GetComment(){
        String requestUrl = "http://10.0.2.2:5000/blog/getComments/";
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("blog_id",blogid);
            @SuppressWarnings("deprecation") RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {}
                @Override
                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                    assert response.body() != null;
                    if (response.code() == 200){
                        String mes = response.body().string();
                        Log.v("Get Comment",mes);
                        editText.setText("");
                        Gson gson = new Gson();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(mes);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = jsonObject.getJSONArray("commentList");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        List<theComment> rcommentList = gson.fromJson(jsonArray.toString(), new TypeToken<List<theComment>>(){}.getType());
                        for (theComment comment1 : rcommentList){
                            commentList.insert(comment1.getContent(),comment1.getUsername(),comment1.getTime(),comment1.getId());
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                commentAdapter.notifyDataSetChanged();
                            }
                        });
                    }


                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public class theComment{
        String id;
        String blog_id;
        String username;
        String content;
        String time;



        public String getId() {
            return id;
        }

        public String getTime() {
            return time;
        }

        public String getUsername() {
            return username;
        }

        public String getContent() {
            return content;
        }

        public String getBlog_id() {
            return blog_id;
        }
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
