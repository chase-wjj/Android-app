package org.tsinghua.todoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class MyBlogFragment extends Fragment {
    View view;
    private final TodoList todoList = new TodoList();
    private RecyclerView recyclerView;
    private TodoListAdapter adapter;

    public Button addButton;

    public String username;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", ""); // 从SharedPreferences中获取用户名
        getMyBlog();

    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        addButton = view.findViewById(R.id.add_button);
        addButton.setVisibility(View.INVISIBLE);
        recyclerView = view.findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        adapter = new TodoListAdapter(getContext(), todoList);
        // Connect the adapter with the recycler view.
        recyclerView.setAdapter(adapter);
        // Give the recycler view a default layout manager.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }
    public void getMyBlog(){
        String requestUrl = "http://10.0.2.2:5000/blog/getMyBlogs/";
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

                    Gson gson = new Gson();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = jsonObject.getJSONArray("blogList");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    List<Blog> blogList = gson.fromJson(jsonArray.toString(), new TypeToken<List<Blog>>(){}.getType());
                    for (Blog blog : blogList){
                        todoList.insert(blog.getUsername(),blog.getTime(), blog.getBlogContent(), blog.getBlogTitle(),blog.getLike(),Integer.parseInt(blog.getId()));
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public class Blog{
        private String id;
        private String username;
        private String blog_title;
        private String blog_content;

        private String time;
        private String like;

        public String getUsername() {
            return username;
        }

        public String getTime() {
            return time;
        }

        public String getBlogContent() {
            return blog_content;
        }

        public String getBlogTitle() {
            return blog_title;
        }

        public String getId() {
            return id;
        }
        public String getLike(){return like;}


    }
}
