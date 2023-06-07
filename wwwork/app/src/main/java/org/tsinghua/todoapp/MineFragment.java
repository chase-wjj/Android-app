package org.tsinghua.todoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class MineFragment extends Fragment {

    public String username;

    EditText mobileEdit;
    EditText emailEdit;
    EditText homeEdit;
    EditText jobEdit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", ""); // 从SharedPreferences中获取用户名
        if(username != null){
            Log.v("Minefragment中获取username",username);
        }


    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        emailEdit = view.findViewById(R.id.editText);
        mobileEdit = view.findViewById(R.id.editText4);
        homeEdit = view.findViewById(R.id.editText2);
        jobEdit = view.findViewById(R.id.editText3);
        getAccountInfo();
        Button button = view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeAccountInfo(v);
            }
        });



        return view;
    }


    //获取账号信息
    public void getAccountInfo(){
        String requestUrl = "http://10.0.2.2:5000/account/getAccountInfo/";

        try {
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username",username);
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
                    Gson gson = new Gson();
                    AccountInfo info = gson.fromJson(mes,AccountInfo.class);
                    getActivity().runOnUiThread(() -> {
                        emailEdit.setText(info.getEdit());
                        homeEdit.setText(info.getHome());
                        mobileEdit.setText(info.getMobile());
                        jobEdit.setText(info.getJob());
                    });

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void ChangeAccountInfo(View view) {
        Log.v("changeaccount", "yes");
        String requestUrl = "http://10.0.2.2:5000/account/changeAccountInfo/";
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username",username)
                    .put("mobile", mobileEdit.getText())
                    .put("home", homeEdit.getText())
                    .put("job", jobEdit.getText())
                    .put("edit", emailEdit.getText());
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
                    if (mes.equals("success")){
                        Log.v("change success","Change AccountInfo Success!");
                    }

                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }

    }


    //将string转换为json的自定义类；
    public class AccountInfo{
        private String email;
        private String mobile;
        private String home;
        private String job;
        private String edit;
        private String photo;
        private String username;

        public String getEmail(){return email;}
        public String getMobile(){return mobile;}
        public String getHome(){return home;}
        public String getJob(){return job;}

        public String getEdit() {
            return edit;
        }

        public String getPhoto() {
            return photo;
        }

        public String getUsername() {
            return username;
        }
    }

}
