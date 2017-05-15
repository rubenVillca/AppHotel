package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.MessageModel;

import java.util.ArrayList;

public class MessageAdapterRecycler extends RecyclerView.Adapter<MessageAdapterRecycler.MessageViewHolder>{

    private ArrayList<MessageModel> messages;
    private int resource;
    private Activity activity;

    public MessageAdapterRecycler(ArrayList<MessageModel> messages, int resource, Activity activity) {
        this.messages=messages;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        MessageModel messageModel=messages.get(position);

        holder.senderCardView.setText(messageModel.getEmailSender());
        holder.dateCardView.setText(messageModel.getDateRecived());
        holder.titleCardView.setText(messageModel.getTittle());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{
        private TextView senderCardView;
        private TextView dateCardView;
        private TextView titleCardView;

        private MessageViewHolder(View itemView) {
            super(itemView);

            senderCardView=(TextView)itemView.findViewById(R.id.remitentMessageCardViewText);
            dateCardView=(TextView)itemView.findViewById(R.id.dateMessageCardViewText);
            titleCardView=(TextView)itemView.findViewById(R.id.titleMessageCardViewText);
        }
    }
}
