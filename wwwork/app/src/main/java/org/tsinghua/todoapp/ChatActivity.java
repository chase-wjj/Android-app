package org.tsinghua.todoapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ListView chatListView;
    private ChatAdapter adapter;
    private List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        AppController.getInstance().addActivity(this);
        String friendName = getIntent().getStringExtra("friendName");
        String friendPhoneNumber = getIntent().getStringExtra("friendPhoneNumber");

        getSupportActionBar().setTitle(friendName);

        chatListView = findViewById(R.id.chat_listview);
        EditText contentEditText = findViewById(R.id.content);
        Button sendButton = findViewById(R.id.send);

        messageList = new ArrayList<Message>();
        adapter = new ChatAdapter(ChatActivity.this, messageList);
        chatListView.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentEditText.getText().toString();

                if (!TextUtils.isEmpty(content)) {
                    Message message = new Message(content);
                    messageList.add(message);
                    adapter.notifyDataSetChanged();
                    chatListView.setSelection(chatListView.getAdapter().getCount()-1);

                    contentEditText.setText("");
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppController.getInstance().removeActivity(this);
    }

}