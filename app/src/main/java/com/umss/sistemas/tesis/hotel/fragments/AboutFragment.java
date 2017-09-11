package com.umss.sistemas.tesis.hotel.fragments;

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
        // Inflate the layout for this fragment
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
        LinearLayout imgHistory=(LinearLayout)view.findViewById(R.id.layoutHistory);
        imgHistory.setOnClickListener(this);

        LinearLayout imgProfile=(LinearLayout)view.findViewById(R.id.layoutProfile);
        imgProfile.setOnClickListener(this);

        LinearLayout imgAbout=(LinearLayout)view.findViewById(R.id.layoutAbout);
        imgAbout.setOnClickListener(this);

        LinearLayout imgFrequently=(LinearLayout)view.findViewById(R.id.layoutFrequently);
        imgFrequently.setOnClickListener(this);

        LinearLayout imgContact=(LinearLayout)view.findViewById(R.id.layoutContact);
        imgContact.setOnClickListener(this);
    }
}
