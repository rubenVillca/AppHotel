package com.umss.sistemas.tesis.hotel.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.HomeAdapterRecycler;
import com.umss.sistemas.tesis.hotel.model.PictureModel;
import com.umss.sistemas.tesis.hotel.util.Fragments;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragments {
    private CircleImageView imgProfile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        super.showToolBar("",false,view);
        //view=setRecyclerView(view);
        imgProfile=(CircleImageView)view.findViewById(R.id.imgCircleProfile);
        ImageView imgCamera = (ImageView) view.findViewById(R.id.imgProfileCamera);
        imgCamera.setOnClickListener(this);

        getContent();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==Fragments.REQUEST_IMAGE_CAPTURE && resultCode==getActivity().RESULT_OK){

            Picasso.with(getActivity()).load(mCurrentPhotoPath).into(imgProfile);
            addPictureToGalery();
            Toast.makeText(getActivity(),"Guradado en: "+mCurrentPhotoPath,Toast.LENGTH_LONG).show();
        }
    }

    public void getContent() {

    }

    private View setRecyclerView(View view) {
        ScrollView pictureRecycler=(ScrollView)view.findViewById(R.id.homeScrollView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //pictureRecycler.setLayoutManager(linearLayoutManager);

        HomeAdapterRecycler homeAdapter=new HomeAdapterRecycler(buidPicture(),R.layout.cardview_service,getActivity());
        //pictureRecycler.setAdapter(homeAdapter);
        return view;
    }
    public ArrayList<PictureModel> buidPicture(){
        ArrayList<PictureModel> pictures= new ArrayList<>();

        pictures.add(new PictureModel("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Sasso_lungo_da_passo_pordoi.jpg/270px-Sasso_lungo_da_passo_pordoi.jpg","Mario","4 dias","3  me gusta"));
        pictures.add(new PictureModel("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Sasso_lungo_da_passo_pordoi.jpg/270px-Sasso_lungo_da_passo_pordoi.jpg","Marioas","7 dias","4"));
        pictures.add(new PictureModel("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Sasso_lungo_da_passo_pordoi.jpg/270px-Sasso_lungo_da_passo_pordoi.jpg","Marioas","7 dias","5 me gusta"));
        pictures.add(new PictureModel("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Sasso_lungo_da_passo_pordoi.jpg/270px-Sasso_lungo_da_passo_pordoi.jpg","Marioas","7 dias","6  me gusta"));
        pictures.add(new PictureModel("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Sasso_lungo_da_passo_pordoi.jpg/270px-Sasso_lungo_da_passo_pordoi.jpg","Marioas","7 dias","7  me gusta"));

        return pictures;
    }
}
