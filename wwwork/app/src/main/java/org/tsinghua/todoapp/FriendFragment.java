package org.tsinghua.todoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class FriendFragment extends Fragment {
    private List<Friend> friendList = new ArrayList<Friend>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        initFriendList();
        FriendAdapter adapter = new FriendAdapter(getActivity(), friendList);
        ListView friendListView = (ListView) view.findViewById(R.id.friend_list);
        EditText addFriendEditText = view.findViewById(R.id.add_friend_edittext);
        Button addButton = view.findViewById(R.id.add_friend_button);
        friendListView.setAdapter(adapter);
        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend friend = friendList.get(position);
                String friendName = friend.getName();
                String friendPhoneNumber = friend.getPhoneNumber();
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("friendName", friendName);
                intent.putExtra("friendPhoneNumber", friendPhoneNumber);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = addFriendEditText.getText().toString().trim(); // 获取输入框中的文本
                if (!TextUtils.isEmpty(name)) { // 如果文本不为空
                    Friend friend = new Friend(name, "11223344556", R.drawable.person); // 创建新的好友对象
                    friendList.add(friend); // 将好友对象添加到列表末尾
                    adapter.notifyDataSetChanged(); // 通知适配器数据已更改
                }
                addFriendEditText.setText(""); // 清空输入框
            }
        });

        return view;

    }


    private void initFriendList() {
        boolean hasAssistant = false;
        for (Friend friend : friendList) {
            if (friend.getName().equals("助手")) {
                hasAssistant = true;
                break;
            }
        }
        if (!hasAssistant) {
            Friend friend1 = new Friend("助手", "12345678900", R.drawable.person);
            friendList.add(friend1);
        }
    }

}
