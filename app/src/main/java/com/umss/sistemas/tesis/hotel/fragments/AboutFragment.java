package com.umss.sistemas.tesis.hotel.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.parent.FragmentParent;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends FragmentParent {

    public AboutFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_about, container, false);

        super.showToolBar(getResources().getString(R.string.title_hotel), false);
        super.showFloatingButtonMessage(view);
        setActionIcon(view);
        return view;
    }

    /**
     * asigna una accion a cada imagen del home
     * @param view: activity principal que se esta mostrando
     */
    private void setActionIcon(View view) {
        ImageView imgHistory=(ImageView)view.findViewById(R.id.imageHistory);
        imgHistory.setOnClickListener(this);

        ImageView imgProfile=(ImageView)view.findViewById(R.id.imageProfile);
        imgProfile.setOnClickListener(this);

        ImageView imgAbout=(ImageView)view.findViewById(R.id.imageAbout);
        imgAbout.setOnClickListener(this);

        ImageView imgFrequently=(ImageView)view.findViewById(R.id.imageFrequently);
        imgFrequently.setOnClickListener(this);

        ImageView imgContact=(ImageView)view.findViewById(R.id.imageContact);
        imgContact.setOnClickListener(this);
    }
}
