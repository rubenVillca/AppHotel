package com.umss.sistemas.tesis.hotel.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.umss.sistemas.tesis.hotel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraProfileFragment extends Fragments {
    private ImageView imgCamera;
    public CameraProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_camera_profile, container, false);

        imgCamera = (ImageView) view.findViewById(R.id.idPictureCamera);
        Button btnCamera = (Button) view.findViewById(R.id.btnCameraCapture);
        btnCamera.setOnClickListener(this);
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode==getActivity().RESULT_OK){
            Bundle extras=data.getExtras();
            Bitmap imgBitmap=(Bitmap)extras.get("data");
            imgCamera.setImageBitmap(imgBitmap);
        }else{
            Toast.makeText(getContext(),"No funciona la captura",Toast.LENGTH_LONG).show();
        }
    }
}
