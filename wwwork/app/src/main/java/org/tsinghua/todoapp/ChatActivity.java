package org.tsinghua.todoapp;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {

    private MessageList messagelist = new MessageList();
    private ListView chatListView;
    private ChatAdapter adapter;

    private EditText contentEditText;
    
    private String friend_name;

    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        SharedPreferences sharedPreferences = getSharedPreferences("loggeduser", MODE_PRIVATE);
        friend_name = getIntent().getStringExtra("friendName");
        user_name = sharedPreferences.getString("username", "");
        getSupportActionBar().setTitle(friend_name);

        getMessage();

        chatListView = findViewById(R.id.chat_listview);
        contentEditText = findViewById(R.id.content);
        Button sendButton = findViewById(R.id.send);

        adapter = new ChatAdapter(this, messagelist);
        chatListView.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
    
    public void sendMessage(){
        String requestUrl = "http://10.0.2.2:5000/chat/sendMessage/";
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_name",user_name).put("friend_name",friend_name).put("content",contentEditText.getText());
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
                        Log.v("Send Message","success!");
                        contentEditText.setText("");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChatActivity.this,"发送消息成功！",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getMessage(){
        String requestUrl = "http://10.0.2.2:5000/chat/getMessage/";
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_name",user_name);
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
                        Log.v("Get Message",mes);
                        contentEditText.setText("");
                        Gson gson = new Gson();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(mes);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = jsonObject.getJSONArray("message_list");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        List<theMessage> rmessageList = gson.fromJson(jsonArray.toString(), new TypeToken<List<ChatActivity.theMessage>>(){}.getType());
                        for (ChatActivity.theMessage message1 : rmessageList){
                            messagelist.insert(message1.getContent(),message1.getUsername(),message1.getTime(),message1.getId());
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }


                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public class theMessage{
        String username;
        String content;
        String id;
        String time;
        public String getUsername() {
            return username;
        }
        public String getContent() {
            return content;
        }
        public String getTime() { return time; }
        public String getId() { return id; }
    }

}