package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.MessageModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

public class MessageActivity extends ActivityParent {
    private MessageModel messageModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        super.showToolBar(getResources().getString(R.string.toolbar_tittle_message),true);
        initBundle();
        initContent();
    }

    private void initContent() {
        TextView remitentMessageActivityText= findViewById(R.id.remitentMessageActivityText);
        remitentMessageActivityText.setText(messageModel.getEmailSender());

        TextView dateMessageActivityText= findViewById(R.id.dateMessageActivityText);
        dateMessageActivityText.setText(messageModel.getDateRecived()+" "+messageModel.getTimeRecived());

        TextView titleMessageActivityText= findViewById(R.id.titleMessageActivityText);
        titleMessageActivityText.setText(messageModel.getTittle());

        TextView typeMessageActivityText= findViewById(R.id.typeMessageActivityText);
        typeMessageActivityText.setText(messageModel.getType());

        TextView contentMessageActivityText= findViewById(R.id.contentMessageActivityText);
        contentMessageActivityText.setText(android.text.Html.fromHtml(messageModel.getContent()).toString());
    }

    private void initBundle() {
        Bundle bundle=getIntent().getExtras();
        if (bundle != null) {
            messageModel= (MessageModel) bundle.getSerializable("messageModel");
        }
    }
}
