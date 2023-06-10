package org.tsinghua.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private static final int VIEW_TYPE_RECEIVE = 0;
    private static final int VIEW_TYPE_SEND = 1;

    private Context context;
    private MessageList messageList;

    public ChatAdapter(Context context, MessageList messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = messageList.get(position);
        int viewType = getItemViewType(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            viewHolder = new ViewHolder();
            switch (viewType) {
                case VIEW_TYPE_RECEIVE:
                    convertView = inflater.inflate(R.layout.chat_receive_item, parent, false);
                    viewHolder.content = convertView.findViewById(R.id.chat_content_receive);
                    break;
                case VIEW_TYPE_SEND:
                    convertView = inflater.inflate(R.layout.chat_send_item, parent, false);
                    viewHolder.content = convertView.findViewById(R.id.chat_content_send);
                    break;
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.content.setText(message.getContent());

        return convertView;
    }

    private static class ViewHolder {
        TextView content;
    }
}