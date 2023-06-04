package org.tsinghua.todoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


public class FriendFragment extends Fragment {

    private List<Friend> friendList;
    private ListView friendListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friend, container, false);

        friendListView = rootView.findViewById(R.id.friend_list);

        friendList = new ArrayList<Friend>();
        friendList.add(new Friend("小明", "123456789", R.drawable.person));
        friendList.add(new Friend("小红", "987654321", R.drawable.person));
        friendList.add(new Friend("小华", "111111111", R.drawable.person));

        FriendAdapter adapter = new FriendAdapter(getContext(), friendList);
        friendListView.setAdapter(adapter);

        return rootView;
    }
}