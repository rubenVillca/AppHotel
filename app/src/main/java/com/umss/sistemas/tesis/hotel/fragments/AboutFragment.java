package com.umss.sistemas.tesis.hotel.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        ImageView imageProfile= view.findViewById(R.id.imageProfile);
        imageProfile.setOnClickListener(this);

        ImageView imageAbout= view.findViewById(R.id.imageAbout);
        imageAbout.setOnClickListener(this);

        ImageView imageFrequently= view.findViewById(R.id.imageFrequently);
        imageFrequently.setOnClickListener(this);

        ImageView imageContact= view.findViewById(R.id.imageContact);
        imageContact.setOnClickListener(this);

        //layout
        LinearLayout layoutProfile= view.findViewById(R.id.layoutProfile);
        layoutProfile.setOnClickListener(this);

        LinearLayout layoutAbout= view.findViewById(R.id.layoutAbout);
        layoutAbout.setOnClickListener(this);

        LinearLayout layoutFrequently= view.findViewById(R.id.layoutFrequently);
        layoutFrequently.setOnClickListener(this);

        LinearLayout layoutContact= view.findViewById(R.id.layoutContact);
        layoutContact.setOnClickListener(this);
    }
}
