package com.umss.sistemas.tesis.hotel.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.ServiceHelper;
import com.umss.sistemas.tesis.hotel.model.PersonModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends ActivityParent {
    protected String mCurrentPhotoPath;
    protected String urlImageProfile;
    protected static final int REQUEST_IMAGE_CAPTURE = 1;

    private CircleImageView imgProfile;

    public ProfileActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        serviceHelper = new ServiceHelper(this);
        showToolBar("", true);

        showImageCamera();
        showContentProfile();
    }

    /**
     * permite cambiar imagen de perfil
     */
    public void goCameraActivity(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(this.getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this, "com.umss.sistemas.tesis.hotel", photoFile);

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
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault()).format(new Date());
        String imageFilename = timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFilename, ".jpg", storageDir);
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        urlImageProfile=image.getAbsolutePath();
        return image;
    }

    private void showContentProfile() {
        PersonModel profile = serviceHelper.getPersonModel(serviceHelper.getLoginModel().getIdPerson());
        showDataProfile(profile);
        imgProfile = findViewById(R.id.imgCircleProfile);
        if (!profile.getImgPerson().equals("")) {
            Picasso.with(this).load(Conexion.urlServer + profile.getImgPerson()).into(imgProfile);
        }
    }

    private void showImageCamera() {
        ImageView imgCamera = findViewById(R.id.imgProfileCamera);
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
    private void showDataProfile(PersonModel profile) {
        TextView nameUser = findViewById(R.id.userNameProfile);
        nameUser.setText(profile.getNamePerson() + "\n" + profile.getNameLastPerson());

        TextView name = findViewById(R.id.profileNamePerson);
        name.setText(profile.getNamePerson());

        TextView nameLast = findViewById(R.id.profileLastNamePerson);
        nameLast.setText(profile.getNameLastPerson());

        TextView email = findViewById(R.id.profileEmailPerson);
        email.setText(profile.getEmailPerson());

        TextView point = findViewById(R.id.profilePointPerson);
        point.setText(String.valueOf(profile.getPointPerson()));

        TextView address = findViewById(R.id.profileAddressPerson);
        address.setText(profile.getAddressPerson());

        TextView city = findViewById(R.id.profileCityPerson);
        city.setText(profile.getCityPerson());

        TextView country = findViewById(R.id.profileCountryPerson);
        country.setText(profile.getCountryPerson());

        TextView sex = findViewById(R.id.profileSexPerson);
        sex.setText(profile.getSexPerson() == 1 ? "Hombre" : "Mujer");

        TextView dateBorn = findViewById(R.id.profileDateBornPerson);
        dateBorn.setText(profile.getDateBornPerson());

        TextView dateRegister = findViewById(R.id.profileDateRegisterPerson);
        dateRegister.setText(profile.getDateRegisterPerson());

        TextView typeDocument = findViewById(R.id.profileTypeDocumentPerson);
        typeDocument.setText(profile.getTypeDocument());

        TextView numberDocument = findViewById(R.id.profileNumberDocumentPerson);
        numberDocument.setText(String.valueOf(profile.getNumberDocument()));

        TextView numberPhone = findViewById(R.id.profileNumberPhonePerson);
        numberPhone.setText(String.valueOf(profile.getNumberPhone()));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            Picasso.with(this).load(mCurrentPhotoPath).into(imgProfile);
            tomarFoto();
            subirFoto();
            Toast.makeText(this, "Guradado en: " + mCurrentPhotoPath, Toast.LENGTH_LONG).show();
        }
    }

    private void tomarFoto() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File newFile = new File(mCurrentPhotoPath);

        Uri contentUri = Uri.fromFile(newFile);
        intent.setData(contentUri);
        sendBroadcast(intent);
    }

    /**
     * Conectar con el webServer y sincronizar la tabla profile
     */
    private void subirFoto() {
        serviceHelper =new ServiceHelper(this);
        PersonModel profile = serviceHelper.getPersonModel(serviceHelper.getLoginModel().getIdPerson());
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("idPerson", profile.getIdPerson());
        try {
            params.put("imgAddress", new File(urlImageProfile));
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo enviar la imagen");
        }
        params.put("android", "android");
        client.post(Conexion.PROFILE_UPLOAD, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //System.out.println("ruta imagen: " + new String(responseBody));
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        serviceHelper.syncUpPerson(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Servidor no disponible");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no esta disponible");
            }
        });
    }
}
