package com.umss.sistemas.tesis.hotel.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.util.Fragments;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragments {

    ImageView imageMessage;
    Button buttonMessage;
    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_messages, container, false);
        super.showToolBar(getActivity().getString(R.string.btn_message),false,view);

        imageMessage=(ImageView)view.findViewById(R.id.idPictureMessage);
        buttonMessage=(Button)view.findViewById(R.id.btnMessage);
        buttonMessage.setOnClickListener(this);

        return view;
    }
}
