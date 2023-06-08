package org.tsinghua.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

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

        return view;
    }

    private void initFriendList() {
        Friend friend1 = new Friend("张三", "13812345678", R.drawable.person);
        friendList.add(friend1);
        Friend friend2 = new Friend("李四", "13912345678", R.drawable.person);
        friendList.add(friend2);
        Friend friend3 = new Friend("王五", "13712345678", R.drawable.person);
        friendList.add(friend3);
    }
}