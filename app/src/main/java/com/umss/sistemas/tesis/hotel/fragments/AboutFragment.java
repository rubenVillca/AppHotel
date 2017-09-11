package com.umss.sistemas.tesis.hotel.fragments;

import android.widget.ImageView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.parent.FragmentParent;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends FragmentParent {

    public AboutFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the image for this fragment
        View view=inflater.inflate(R.layout.fragment_about, container, false);

        super.showToolBar(getResources().getString(R.string.title_hotel), false);
        setActionIcon(view);
        return view;
    }

    /**
     * asigna una accion a cada imagen del home
     * @param view: activity principal que se esta mostrando
     */
    private void setActionIcon(View view) {
        ImageView imageHistory=(ImageView)view.findViewById(R.id.imageHistory);
        imageHistory.setOnClickListener(this);

        ImageView imageProfile=(ImageView)view.findViewById(R.id.imageProfile);
        imageProfile.setOnClickListener(this);

        ImageView imageAbout=(ImageView)view.findViewById(R.id.imageAbout);
        imageAbout.setOnClickListener(this);

        ImageView imageFrequently=(ImageView)view.findViewById(R.id.imageFrequently);
        imageFrequently.setOnClickListener(this);

        ImageView imageContact=(ImageView)view.findViewById(R.id.imageContact);
        imageContact.setOnClickListener(this);

        //layout
        LinearLayout layoutHistory=(LinearLayout)view.findViewById(R.id.layoutHistory);
        layoutHistory.setOnClickListener(this);

        LinearLayout layoutProfile=(LinearLayout)view.findViewById(R.id.layoutProfile);
        layoutProfile.setOnClickListener(this);

        LinearLayout layoutAbout=(LinearLayout)view.findViewById(R.id.layoutAbout);
        layoutAbout.setOnClickListener(this);

        LinearLayout layoutFrequently=(LinearLayout)view.findViewById(R.id.layoutFrequently);
        layoutFrequently.setOnClickListener(this);

        LinearLayout layoutContact=(LinearLayout)view.findViewById(R.id.layoutContact);
        layoutContact.setOnClickListener(this);
    }
}
