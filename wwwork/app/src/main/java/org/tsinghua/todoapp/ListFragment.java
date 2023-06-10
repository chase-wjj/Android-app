package org.tsinghua.todoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
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

public class ListFragment extends Fragment {
    private final TodoList todoList = new TodoList();
    private RecyclerView recyclerView;
    private TodoListAdapter adapter;
    private Button addButton;

    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GetBlog();
    }

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        addButton = view.findViewById(R.id.add_button);
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
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),NewActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }
    public void GetBlog(){
        String requestUrl = "http://10.0.2.2:5000/blog/getBlogs/";
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(requestUrl)
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