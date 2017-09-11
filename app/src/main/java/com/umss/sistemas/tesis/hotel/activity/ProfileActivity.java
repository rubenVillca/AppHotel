package com.umss.sistemas.tesis.hotel.activity;

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
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.model.PersonModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        helperSQLiteObtain = new HelperSQLiteObtain(this);
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
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String imageFilename = timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFilename, ".jpg", storageDir);
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        urlImageProfile=image.getAbsolutePath();
        return image;
    }

    private void showContentProfile() {
        PersonModel profile = helperSQLiteObtain.getPersonModel(helperSQLiteObtain.getLoginModel().getIdPerson());
        showDataProfile(profile);
        imgProfile = (CircleImageView) findViewById(R.id.imgCircleProfile);
        if (!profile.getImgPerson().equals("")) {
            Picasso.with(this).load(Conexion.urlServer + profile.getImgPerson()).into(imgProfile);
        }
    }

    private void showImageCamera() {
        ImageView imgCamera = (ImageView) findViewById(R.id.imgProfileCamera);
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
        TextView nameUser = (TextView) findViewById(R.id.userNameProfile);
        nameUser.setText(profile.getNamePerson() + "\n" + profile.getNameLastPerson());

        TextView name = (TextView) findViewById(R.id.profileNamePerson);
        name.setText(profile.getNamePerson());

        TextView nameLast = (TextView) findViewById(R.id.profileLastNamePerson);
        nameLast.setText(profile.getNameLastPerson());

        TextView email = (TextView) findViewById(R.id.profileEmailPerson);
        email.setText(profile.getEmailPerson());

        TextView point = (TextView) findViewById(R.id.profilePointPerson);
        point.setText(String.valueOf(profile.getPointPerson()));

        TextView address = (TextView) findViewById(R.id.profileAddressPerson);
        address.setText(profile.getAddressPerson());

        TextView city = (TextView) findViewById(R.id.profileCityPerson);
        city.setText(profile.getCityPerson());

        TextView country = (TextView) findViewById(R.id.profileCountryPerson);
        country.setText(profile.getCountryPerson());

        TextView sex = (TextView) findViewById(R.id.profileSexPerson);
        sex.setText(profile.getSexPerson() == 1 ? "Hombre" : "Mujer");

        TextView dateBorn = (TextView) findViewById(R.id.profileDateBornPerson);
        dateBorn.setText(profile.getDateBornPerson());

        TextView dateRegister = (TextView) findViewById(R.id.profileDateRegisterPerson);
        dateRegister.setText(profile.getDateRegisterPerson());

        TextView typeDocument = (TextView) findViewById(R.id.profileTypeDocumentPerson);
        typeDocument.setText(profile.getTypeDocument());

        TextView numberDocument = (TextView) findViewById(R.id.profileNumberDocumentPerson);
        numberDocument.setText(String.valueOf(profile.getNumberDocument()));

        TextView numberPhone = (TextView) findViewById(R.id.profileNumberPhonePerson);
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
        helperSQLiteInsert=new HelperSQLiteInsert(this);
        PersonModel profile = helperSQLiteObtain.getPersonModel(helperSQLiteObtain.getLoginModel().getIdPerson());
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
                        helperSQLiteInsert.syncUpPerson(obj);
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
