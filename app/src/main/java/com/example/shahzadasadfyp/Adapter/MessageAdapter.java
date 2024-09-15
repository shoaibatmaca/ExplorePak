//package com.example.shahzadasadfyp.Adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.shahzadasadfyp.Model.Message;
//import com.example.shahzadasadfyp.R;
//
//import java.util.List;
//
//public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
//
//    private final List<Message> messageList;
//    private final String currentUserId;
//
//    private static final int VIEW_TYPE_SENT = 1;
//    private static final int VIEW_TYPE_RECEIVED = 2;
//
//    public MessageAdapter(List<Message> messageList, String currentUserId) {
//        this.messageList = messageList;
//        this.currentUserId = currentUserId;
//    }
//
//    @NonNull
//    @Override
//    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view;
//        if (viewType == VIEW_TYPE_SENT) {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
//        } else {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
//        }
//        return new MessageViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
//        Message message = messageList.get(position);
//        holder.messageTextView.setText(message.getMessageText());
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        Message message = messageList.get(position);
//        return message.getSenderId().equals(currentUserId) ? VIEW_TYPE_SENT : VIEW_TYPE_RECEIVED;
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return messageList.size();
//    }
//
//    public static class MessageViewHolder extends RecyclerView.ViewHolder {
//        TextView messageTextView;
//
//        public MessageViewHolder(@NonNull View itemView) {
//            super(itemView);
//            messageTextView = itemView.findViewById(R.id.messageTextView);
//        }
//    }
//}

package com.example.shahzadasadfyp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shahzadasadfyp.Model.Message;
import com.example.shahzadasadfyp.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private final List<Message> messageList;
    private final String currentUserId;

    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;

    public MessageAdapter(List<Message> messageList, String currentUserId) {
        this.messageList = messageList;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);

        if (getItemViewType(position) == VIEW_TYPE_SENT) {
            holder.textMessageOutgoing.setVisibility(View.VISIBLE);
            holder.textMessageOutgoing.setText(message.getMessageText());
            holder.textMessageIncoming.setVisibility(View.GONE);
        } else {
            holder.textMessageIncoming.setVisibility(View.VISIBLE);
            holder.textMessageIncoming.setText(message.getMessageText());
            holder.textMessageOutgoing.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        return message.getSenderId().equals(currentUserId) ? VIEW_TYPE_SENT : VIEW_TYPE_RECEIVED;
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textMessageIncoming, textMessageOutgoing;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessageIncoming = itemView.findViewById(R.id.text_message_incoming);
            textMessageOutgoing = itemView.findViewById(R.id.text_message_outgoing);
        }
    }
}