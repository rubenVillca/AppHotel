package com.umss.sistemas.tesis.hotel.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.activity.AboutActivity;
import com.umss.sistemas.tesis.hotel.activity.CalendarActivity;
import com.umss.sistemas.tesis.hotel.activity.MenuFoodActivity;
import com.umss.sistemas.tesis.hotel.activity.MessagesActivity;
import com.umss.sistemas.tesis.hotel.activity.OffersActivity;
import com.umss.sistemas.tesis.hotel.activity.ReserveActivity;
import com.umss.sistemas.tesis.hotel.activity.ServicesActivity;
import com.umss.sistemas.tesis.hotel.activity.SitesTourActivity;

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
        Intent intent =null;
        switch (v.getId()) {
            case R.id.imgProfileCamera:
                showActivityCamera();
                break;
            case R.id.fab:
                intent = new Intent(getActivity(), MessagesActivity.class);
                break;
            case R.id.imageOffer:
                intent = new Intent(getActivity(), OffersActivity.class);
                break;
            case R.id.imageSiteTour:
                intent = new Intent(getActivity(), SitesTourActivity.class);
                break;
            case R.id.imageService:
                intent = new Intent(getActivity(), ServicesActivity.class);
                break;
            case R.id.imageAboutHotel:
                intent = new Intent(getActivity(), AboutActivity.class);
                break;
            case R.id.imageReserve:
                intent = new Intent(getActivity(), ReserveActivity.class);
                break;
            case R.id.imageLocationMap:
                Toast.makeText(getContext(), "Localizanos", Toast.LENGTH_LONG).show();
                break;
            case R.id.imageActivity:
                intent = new Intent(getActivity(), CalendarActivity.class);
                break;
            case R.id.imageServiceFood:
                intent = new Intent(getActivity(), MenuFoodActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }


    /**
     * permite cambiar imagen de perfil
     */
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


    /**
     * @return File: imagen guardada en el smarthphone
     * @throws IOException:control de errores
     */
    //guardar las imagenes de perfil capturadas[ error]
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFilename = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFilename, ".jpg", storageDir);
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    protected void addPictureToGalery() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File newFile = new File(mCurrentPhotoPath);

        Uri contentUri = Uri.fromFile(newFile);
        intent.setData(contentUri);
        getActivity().sendBroadcast(intent);
    }

    protected void showFloatingButtonMessage(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }
}
