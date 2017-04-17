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
            case R.id.imageOffer:
                showFragmentOffer();    break;
            case R.id.imageSiteTour:
                showFragmentSiteTour();    break;
            case R.id.imageService:
                showFragmentService();    break;
            case R.id.imageAboutHotel:
                showFragmentAboutHotel();    break;
            case R.id.imageReserve:
                showFragmentReserve();    break;
            case R.id.imageLocationMap:
                showFragmentLocationMap();    break;
            case R.id.imageActivity:
                showFragmentActivity();    break;
            case R.id.imageServiceFood:
                showFragmentServiceFood();    break;
        }
    }

    /**
     * mostrar lista de reservas realizadas
     */
    private void showFragmentReserve() {
        Toast.makeText(getContext(), "reervas", Toast.LENGTH_LONG).show();
    }

    /**
     * mostrar en mapa googlemaps la ubicacion del establcimiento
     */
    private void showFragmentLocationMap() {
        Toast.makeText(getContext(), "ubiquemos", Toast.LENGTH_LONG).show();
    }

    /**
     * mostrar losta de actividades publicas del hotel
     */
    private void showFragmentActivity() {
        Toast.makeText(getContext(), "Actividades", Toast.LENGTH_LONG).show();
    }

    /**
     * informacion basica del hotel
     */
    private void showFragmentAboutHotel() {
        MessagesFragment messagesFragment = new MessagesFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, messagesFragment)
                .addToBackStack(null).commit();

    }

    /**
     * menu de comidas del bar
     */
    private void showFragmentServiceFood() {
        Toast.makeText(getContext(), "comidas", Toast.LENGTH_LONG).show();
    }

    /**
     * lista de servicios ofrecidos por el hotel
     */
    private void showFragmentService() {
        Toast.makeText(getContext(), "servicios", Toast.LENGTH_LONG).show();
    }

    /**
     * lista d sitios turisticos ceranos al hotel
     */
    private void showFragmentSiteTour() {
        Toast.makeText(getContext(), "sitios turisticos", Toast.LENGTH_LONG).show();
    }

    /**
     * lista de ofertas disponibles del hotel
     */
    private void showFragmentOffer() {
        Toast.makeText(getContext(), "acerca de nosotros", Toast.LENGTH_LONG).show();
    }

    /**
     * mostrar mensaje seleccionado
     */
    private void showFragmentMessage() {
        Toast.makeText(getContext(), "Mensajes", Toast.LENGTH_LONG).show();
    }


    /**
     * mostrar fragmento con lista de mensajes recibidos
     */
    private void showFragmentMessages() {
        MessagesFragment messagesFragment = new MessagesFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, messagesFragment)
                .addToBackStack(null).commit();
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

    protected void showFloatingButtonMessage(View view){
        FloatingActionButton fab=(FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }
}
