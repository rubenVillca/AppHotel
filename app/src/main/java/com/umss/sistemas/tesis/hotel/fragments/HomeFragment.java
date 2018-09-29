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
public class HomeFragment extends FragmentParent {

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the image for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        super.showToolBar(getResources().getString(R.string.title_hotel), false);
        setActionIcon(view);
        return view;
    }

    /**
     * asigna una accion a cada imagen del home
     * @param view: activity principal que se esta mostrando
     */
    private void setActionIcon(View view) {

        ImageView imageOffer= view.findViewById(R.id.imageOffer);
        imageOffer.setOnClickListener(this);

        ImageView imageSiteTour= view.findViewById(R.id.imageSiteTour);
        imageSiteTour.setOnClickListener(this);

        ImageView imageService= view.findViewById(R.id.imageService);
        imageService.setOnClickListener(this);

        ImageView imageLocation= view.findViewById(R.id.imageLocationMap);
        imageLocation.setOnClickListener(this);

        ImageView imageActivity= view.findViewById(R.id.imageActivity);
        imageActivity.setOnClickListener(this);

        LinearLayout layoutOffer= view.findViewById(R.id.layoutOffer);
        layoutOffer.setOnClickListener(this);

        LinearLayout layoutSiteTour= view.findViewById(R.id.layoutSiteTour);
        layoutSiteTour.setOnClickListener(this);

        LinearLayout layoutService= view.findViewById(R.id.layoutService);
        layoutService.setOnClickListener(this);

        LinearLayout layoutLocation= view.findViewById(R.id.layoutLocationMap);
        layoutLocation.setOnClickListener(this);

        LinearLayout layoutActivity= view.findViewById(R.id.layoutActivity);
        layoutActivity.setOnClickListener(this);
    }
}
