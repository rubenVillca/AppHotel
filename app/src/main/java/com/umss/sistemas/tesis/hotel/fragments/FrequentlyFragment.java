package com.umss.sistemas.tesis.hotel.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLite;
import com.umss.sistemas.tesis.hotel.parent.FragmentParent;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrequentlyFragment extends FragmentParent {


    public FrequentlyFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_frequently, container, false);
        super.showToolBar(getResources().getString(R.string.tab_frequently),false,view);
        super.showFloatingButtonMessage(view);

        helperSQLite=new HelperSQLite(getContext());

        return view;
    }

}
