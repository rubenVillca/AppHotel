package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.MessageAdapterRecycler;
import com.umss.sistemas.tesis.hotel.helper.ServiceGet;
import com.umss.sistemas.tesis.hotel.model.MessageModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.ArrayList;

public class MessagesActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        super.showToolBar(getResources().getString(R.string.toolbar_tittle_messages),true);

        adapterRecycler();
    }

    private void adapterRecycler() {
        RecyclerView pictureRecycler=(RecyclerView)findViewById(R.id.messagesRecyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        pictureRecycler.setLayoutManager(linearLayoutManager);

        MessageAdapterRecycler messageAdapter=new MessageAdapterRecycler(buildSiteTour(),R.layout.cardview_message,this);
        pictureRecycler.setAdapter(messageAdapter);
    }

    public ArrayList<MessageModel> buildSiteTour(){
        serviceGet =new ServiceGet(this);

        return serviceGet.getMessageModel(0);
    }
}
