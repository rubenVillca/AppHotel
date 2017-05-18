package com.umss.sistemas.tesis.hotel.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.FrequentlyAdapterRecycler;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.model.FrequentlyModel;
import com.umss.sistemas.tesis.hotel.parent.FragmentParent;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrequentlyFragment extends FragmentParent {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frequently, container, false);
        super.showToolBar(getResources().getString(R.string.tab_frequently), false, view);
        super.showFloatingButtonMessage(view);

        adapterRecyclerView(view);

        return view;
    }

    /**
     * cargar lista de preguntas frecuentes a la vista de la app
     *
     * @param view:actividad actual
     */
    private void adapterRecyclerView(View view) {
        RecyclerView frequenltyRecycler = (RecyclerView)view.findViewById(R.id.frequentlyRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        frequenltyRecycler.setLayoutManager(linearLayoutManager);

        FrequentlyAdapterRecycler activityAdapter = new FrequentlyAdapterRecycler(buildFrequently(), R.layout.cardview_frequently, getActivity());
        frequenltyRecycler.setAdapter(activityAdapter);
    }

    public ArrayList<FrequentlyModel> buildFrequently(){
        helperSQLiteObtain =new HelperSQLiteObtain(getContext());

        return helperSQLiteObtain.getFrequentlyModel(0);
    }

}
