package com.umss.sistemas.tesis.hotel.fragments;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.activity.CreateAccountActivity;

public class Fragments extends Fragment implements View.OnClickListener{

    static final int REQUEST_IMAGE_CAPTURE=1;

    protected void showToolBar(String tittle,boolean upButton,View view){
        Toolbar toolbar =(Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.fab:
                MessagesFragment messagesFragment=new MessagesFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container,messagesFragment)
                        .addToBackStack(null).commit();
                break;
            case R.id.btnMessage:
                Toast.makeText(getContext(),"Mensajes",Toast.LENGTH_LONG).show();
                break;
            case R.id.btnCameraCapture:
                intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getActivity().getPackageManager())!=null){
                    startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
                }
                break;
            case R.id.btnCamera:
                CameraProfileFragment cameraProfileFragment=new CameraProfileFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container,cameraProfileFragment)
                        .addToBackStack(null).commit();
                break;
        }
    }
}
