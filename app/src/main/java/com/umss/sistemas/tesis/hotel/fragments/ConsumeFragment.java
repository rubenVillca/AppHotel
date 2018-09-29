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
public class ConsumeFragment extends FragmentParent {

    public ConsumeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_consume, container, false);

        super.showToolBar(getResources().getString(R.string.title_hotel), false);
        setActionIcon(view);
        return view;
    }

    /**
     * asigna una accion a cada imagen del home
     * @param view: activity principal que se esta mostrando
     */
    private void setActionIcon(View view) {

        ImageView imgReserve= view.findViewById(R.id.imageReserve);
        imgReserve.setOnClickListener(this);

        ImageView imgFoodMenu= view.findViewById(R.id.imageServiceFood);
        imgFoodMenu.setOnClickListener(this);

        ImageView imgConsum= view.findViewById(R.id.imageConsum);
        imgConsum.setOnClickListener(this);

        ImageView imageHistory= view.findViewById(R.id.imageHistory);
        imageHistory.setOnClickListener(this);

        LinearLayout layoutHistory= view.findViewById(R.id.layoutHistory);
        layoutHistory.setOnClickListener(this);

        LinearLayout layoutReserve= view.findViewById(R.id.layoutReserve);
        layoutReserve.setOnClickListener(this);

        LinearLayout layoutFoodMenu= view.findViewById(R.id.layoutServiceFood);
        layoutFoodMenu.setOnClickListener(this);

        LinearLayout layoutConsum= view.findViewById(R.id.layoutConsum);
        layoutConsum.setOnClickListener(this);
    }
}
