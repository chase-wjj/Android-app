package org.tsinghua.todoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewActivity extends AppCompatActivity {

    private EditText titleEdit;
    private EditText contentEdit;
    private Button button;
    public String name;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        titleEdit = findViewById(R.id.titleedit);
        contentEdit = findViewById(R.id.contentedit);
        button = findViewById(R.id.button);
        SharedPreferences sharedPreferences = getSharedPreferences("loggeduser", MODE_PRIVATE);
        name = sharedPreferences.getString("username", ""); // 从SharedPreferences中获取用户名
        Log.v("newact get username",name);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishBlog();
            }
        });

    }
    public void publishBlog(){
        String title = titleEdit.getText().toString();
        String content = contentEdit.getText().toString();
        String name = this.name;
        String requestUrl = "http://10.0.2.2:5000/blog/publishBlog/";
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username",name).put("blog_title",title).put("blog_content",content);
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(NewActivity.this,"发表Blog成功！",Toast.LENGTH_LONG).show();
                            }
                        });
                        Intent intent = new Intent(NewActivity.this,MainActivity.class);
                        startActivity(intent);
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
