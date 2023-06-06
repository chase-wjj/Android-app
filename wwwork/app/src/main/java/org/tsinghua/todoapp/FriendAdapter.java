package org.tsinghua.todoapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FriendAdapter extends BaseAdapter {
    private Context context;
    private List<Friend> friendList;

    public FriendAdapter(Context context, List<Friend> friendList) {
        this.context = context;
        this.friendList = friendList;
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Friend friend = friendList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View friendView = inflater.inflate(R.layout.friend_item, null);

        ImageView pictureView = friendView.findViewById(R.id.friend_picture);
        pictureView.setImageResource(friend.picture());

        TextView nameView = friendView.findViewById(R.id.friend_name);
        nameView.setText(friend.getName());

        TextView phoneView = friendView.findViewById(R.id.friend_phone_number);
        phoneView.setText(friend.getPhoneNumber());

        return friendView;
    }
}