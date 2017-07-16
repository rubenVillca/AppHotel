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
public class HomeFragment extends FragmentParent {

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        super.showToolBar(getResources().getString(R.string.tab_home),false,view);
        super.showFloatingButtonMessage(view);
        setActionIcon(view);
        return view;
    }

    /**
     * asigna una accion a cada imagen del home
     * @param view: activity principal que se esta mostrando
     */
    private void setActionIcon(View view) {
        ImageView imgOffer=(ImageView)view.findViewById(R.id.imageOffer);
        imgOffer.setOnClickListener(this);

        ImageView imgSiteTour=(ImageView)view.findViewById(R.id.imageSiteTour);
        imgSiteTour.setOnClickListener(this);

        ImageView imgService=(ImageView)view.findViewById(R.id.imageService);
        imgService.setOnClickListener(this);

        ImageView imgReserve=(ImageView)view.findViewById(R.id.imageReserve);
        imgReserve.setOnClickListener(this);

        ImageView imgLocation=(ImageView)view.findViewById(R.id.imageLocationMap);
        imgLocation.setOnClickListener(this);

        ImageView imgActivity=(ImageView)view.findViewById(R.id.imageActivity);
        imgActivity.setOnClickListener(this);

        ImageView imgFoodMenu=(ImageView)view.findViewById(R.id.imageServiceFood);
        imgFoodMenu.setOnClickListener(this);

        ImageView imgHistory=(ImageView)view.findViewById(R.id.imageHistory);
        imgHistory.setOnClickListener(this);

        ImageView imgConsum=(ImageView)view.findViewById(R.id.imageConsum);
        imgConsum.setOnClickListener(this);
    }
}
