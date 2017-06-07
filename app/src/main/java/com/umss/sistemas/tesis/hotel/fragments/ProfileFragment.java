package com.umss.sistemas.tesis.hotel.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.model.PersonModel;
import com.umss.sistemas.tesis.hotel.parent.FragmentParent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends FragmentParent {
    private CircleImageView imgProfile;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        super.showToolBar("", false, view);

        helperSQLiteObtain =new HelperSQLiteObtain(getContext());
        showContentProfile(view);
        showImageCamera(view);

        return view;
    }

    /**
     * permite cambiar imagen de perfil
     */
    public void goCameraActivity(View view) {
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
     * guardar las imagenes de perfil capturadas
     *
     * @return File: imagen guardada en el smarthphone
     * @throws IOException:control de errores
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFilename = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFilename, ".jpg", storageDir);
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void showContentProfile(View view) {
        PersonModel profile = helperSQLiteObtain.getPersonModel(helperSQLiteObtain.getLoginModel().getIdPerson());
        showDataProfile(profile, view);
        imgProfile = (CircleImageView) view.findViewById(R.id.imgCircleProfile);
        if (!profile.getImgPerson().equals("")) {
            Picasso.with(getActivity()).load(Conexion.urlServer + profile.getImgPerson()).into(imgProfile);
        }
    }

    private void showImageCamera(View view) {
        ImageView imgCamera = (ImageView) view.findViewById(R.id.imgProfileCamera);
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCameraActivity(v);
            }
        });
    }

    /**
     * mostrar en la app los datos del perfil de usuario
     *
     * @param profile: perfil del usuario
     */
    private void showDataProfile(PersonModel profile, View view) {
        TextView nameUser = (TextView) view.findViewById(R.id.userNameProfile);
        nameUser.setText(profile.getNamePerson()+"\n"+profile.getNameLastPerson());

        TextView name = (TextView) view.findViewById(R.id.profileNamePerson);
        name.setText(profile.getNamePerson());

        TextView nameLast = (TextView) view.findViewById(R.id.profileLastNamePerson);
        nameLast.setText(profile.getNameLastPerson());

        TextView email = (TextView) view.findViewById(R.id.profileEmailPerson);
        email.setText(profile.getEmailPerson());

        TextView point = (TextView) view.findViewById(R.id.profilePointPerson);
        point.setText(String.valueOf(profile.getPointPerson()));

        TextView address = (TextView) view.findViewById(R.id.profileAddressPerson);
        address.setText(profile.getAddressPerson());

        TextView city = (TextView) view.findViewById(R.id.profileCityPerson);
        city.setText(profile.getCityPerson());

        TextView country = (TextView) view.findViewById(R.id.profileCountryPerson);
        country.setText(profile.getCountryPerson());

        TextView sex = (TextView) view.findViewById(R.id.profileSexPerson);
        sex.setText(profile.getSexPerson() == 1 ? "Hombre" : "Mujer");

        TextView dateBorn = (TextView) view.findViewById(R.id.profileDateBornPerson);
        dateBorn.setText(profile.getDateBornPerson());

        TextView dateRegister = (TextView) view.findViewById(R.id.profileDateRegisterPerson);
        dateRegister.setText(profile.getDateRegisterPerson());

        TextView typeDocument = (TextView) view.findViewById(R.id.profileTypeDocumentPerson);
        typeDocument.setText(profile.getTypeDocument());

        TextView numberDocument = (TextView) view.findViewById(R.id.profileNumberDocumentPerson);
        numberDocument.setText(String.valueOf(profile.getNumberDocument()));

        TextView numberPhone = (TextView) view.findViewById(R.id.profileNumberPhonePerson);
        numberPhone.setText(String.valueOf(profile.getNumberPhone()));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getActivity();
        if (requestCode == FragmentParent.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            Picasso.with(getActivity()).load(mCurrentPhotoPath).into(imgProfile);
            addPictureToGalery();
            Toast.makeText(getActivity(), "Guradado en: " + mCurrentPhotoPath, Toast.LENGTH_LONG).show();
        }
    }

    private void addPictureToGalery() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File newFile = new File(mCurrentPhotoPath);

        Uri contentUri = Uri.fromFile(newFile);
        intent.setData(contentUri);
        getActivity().sendBroadcast(intent);
    }
}
