package org.tsinghua.todoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    EditText uname_edit;
    EditText pass_edit;
    Button login_button;
    Button register_button;

    TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppController.getInstance().addActivity(this);
        uname_edit = findViewById(R.id.username);
        pass_edit = findViewById(R.id.password);
        login_button = findViewById(R.id.login);
        register_button = findViewById(R.id.register);
        result = findViewById(R.id.result);

    }

    public void onLoginClick(View v){
        String usr = uname_edit.getText().toString();
        String pwd = pass_edit.getText().toString();
        if(usr.isEmpty()||pwd.isEmpty()){
            return;
        }
        String requestUrl = "http://10.0.2.2:5000/account/login/";
        // TO DO
        try{
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username",usr);
            jsonObject.put("password",pwd);
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
                    String mes = response.body().string();
                    if(mes.equals("success")){
                        SharedPreferences sharedPreferences = getSharedPreferences("loggeduser", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", usr); // 将用户名存储到SharedPreferences中
                        editor.apply();
                        result.clearComposingText();
                        result.setText("login");
                        Intent intent = new Intent(v.getContext(),MainActivity.class);
                        v.getContext().startActivity(intent);
                    }
                }
            });

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onRegisterClick(View v){
        String usr = uname_edit.getText().toString();
        String pwd = pass_edit.getText().toString();
        if(usr.isEmpty()||pwd.isEmpty()){
            return;
        }
        String requestUrl = "http://10.0.2.2:5000/account/register/";
        // TO DO
        try{
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，

            JSONObject jsonBody = new JSONObject()
                    .put("username", usr)
                    .put("password", pwd);
            RequestBody requestBody = RequestBody.create(JSON, jsonBody.toString());
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {}
                @Override
                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                    assert response.body() != null;
                    String mes = response.body().string();
                    Log.d("register",mes);
                    if(mes.equals("success")){

                        result.setText("register success!");
                    }else{
                        result.setText("register fail(maybe username was registered!!!)");
                    }
                }
            });

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppController.getInstance().removeActivity(this);
    }

}
