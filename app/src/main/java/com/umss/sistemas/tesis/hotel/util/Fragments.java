package com.umss.sistemas.tesis.hotel.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.fragments.MessagesFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fragments extends Fragment implements View.OnClickListener {

    protected String mCurrentPhotoPath;
    protected static final int REQUEST_IMAGE_CAPTURE = 1;

    protected void showToolBar(String tittle, boolean upButton, View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                showFragmentMessages(); break;
            case R.id.btnMessage:
                showFragmentMessage();  break;
            case R.id.imgProfileCamera:
                showActivityCamera();   break;
        }
    }

    private void showFragmentMessage() {
        Toast.makeText(getContext(), "Mensajes", Toast.LENGTH_LONG).show();
    }

    private void showFragmentMessages() {
        MessagesFragment messagesFragment = new MessagesFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, messagesFragment)
                .addToBackStack(null).commit();
    }

    private void showActivityCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(getActivity(), "com.umss.sistemas.tesis.hotel", photoFile);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    //guardar las imagenes de perfil capturadas[ error]
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFilename = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFilename, ".jpg", storageDir);
        mCurrentPhotoPath="file:"+image.getAbsolutePath();
        return image;
    }

    protected void addPictureToGalery() {
        Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File newFile=new File(mCurrentPhotoPath);

        Uri contentUri=Uri.fromFile(newFile);
        intent.setData(contentUri);
        getActivity().sendBroadcast(intent);
    }
}
