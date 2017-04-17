package com.umss.sistemas.tesis.hotel.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.util.Fragments;

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
}
