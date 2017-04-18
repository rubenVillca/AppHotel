package com.umss.sistemas.tesis.hotel.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.HomeAdapterRecycler;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.DataBaseSQLiteHelper;
import com.umss.sistemas.tesis.hotel.model.PersonModel;
import com.umss.sistemas.tesis.hotel.model.PictureModel;
import com.umss.sistemas.tesis.hotel.util.Fragments;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragments {
    private CircleImageView imgProfile;
    private DataBaseSQLiteHelper sync;
    private SQLiteDatabase db;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        super.showToolBar("",false,view);

        showContentProfile(view);
        showImageCamera(view);

        return view;
    }



    private void showContentProfile(View view) {
        List<PersonModel> listProfile=getContent();
        if (!listProfile.isEmpty()) {
            PersonModel profile=listProfile.get(0);
            showDataProfile(profile, view);
            if (!profile.getImgPerson().equals("")) {
                showImageProfile(view, profile.getImgPerson());
            }
        }
    }

    private void showImageCamera(View view) {
        ImageView imgCamera = (ImageView) view.findViewById(R.id.imgProfileCamera);
        imgCamera.setOnClickListener(this);
    }

    private void showImageProfile(View view,String nameImage) {
        imgProfile=(CircleImageView)view.findViewById(R.id.imgCircleProfile);
        imgProfile.setImageURI(Uri.parse(Conexion.urlServer+nameImage));
    }


    /**
     * mostrar en la app los datos del perfil de usuario
     * @param profile: perfil del usuario
     */
    private void showDataProfile(PersonModel profile,View view) {
        TextView nameUser=(TextView)view.findViewById(R.id.userNameProfile);
        nameUser.setText(profile.getNamePerson()+" "+profile.getNameLastPerson());

        TextView name=(TextView) view.findViewById(R.id.profileNamePerson);
        name.setText(profile.getNamePerson());

        TextView nameLast=(TextView) view.findViewById(R.id.profileLastNamePerson);
        nameLast.setText(profile.getNameLastPerson());

        TextView email=(TextView) view.findViewById(R.id.profileEmailPerson);
        email.setText(profile.getEmailPerson());

        TextView point=(TextView) view.findViewById(R.id.profilePointPerson);
        point.setText(String.valueOf(profile.getPointPerson()));

        TextView address=(TextView) view.findViewById(R.id.profileAddressPerson);
        address.setText(profile.getAddressPerson());

        TextView city=(TextView) view.findViewById(R.id.profileCityPerson);
        city.setText(profile.getCityPerson());

        TextView country=(TextView) view.findViewById(R.id.profileCountryPerson);
        country.setText(profile.getCountryPerson());

        TextView sex=(TextView) view.findViewById(R.id.profileSexPerson);
        sex.setText(profile.getSexPerson()==1?"Hombre":"Mujer");

        TextView dateBorn=(TextView) view.findViewById(R.id.profileDateBornPerson);
        dateBorn.setText(profile.getDateBornPerson());

        TextView dateRegister=(TextView) view.findViewById(R.id.profileDateRegisterPerson);
        dateRegister.setText(profile.getDateRegisterPerson());

        TextView typeDocument=(TextView) view.findViewById(R.id.profileTypeDocumentPerson);
        typeDocument.setText(profile.getTypeDocument());

        TextView numberDocument=(TextView) view.findViewById(R.id.profileNumberDocumentPerson);
        numberDocument.setText(String.valueOf(profile.getNumberDocument()));

        TextView numberPhone=(TextView) view.findViewById(R.id.profileNumberPhonePerson);
        numberPhone.setText(String.valueOf(profile.getNumberPhone()));

    }

    /**
     * Lee de la base de datos de sqlite los datos de los perfiles
     * @return: lista de perfiles
     */
    private List<PersonModel> getContent() {
        List<PersonModel> listPerson=new ArrayList<>();
        sync=new DataBaseSQLiteHelper(getContext(), DataBaseSQLiteHelper.DATABASE_NAME,null, DataBaseSQLiteHelper.DATABASE_VERSION);
        db=sync.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+ DataBaseSQLiteHelper.TABLE_PERSON,null);
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                PersonModel personModel=new PersonModel();
                personModel.setIdPerson(cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_ID)));
                personModel.setNamePerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_NAME)));
                personModel.setNameLastPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_NAME_LAST)));
                personModel.setCityPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_CITY)));
                personModel.setAddressPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_ADDRESS)));
                personModel.setDateBornPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_DATE_BORN)));
                personModel.setDateRegisterPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_DATE_REGISTER)));
                personModel.setEmailPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_EMAIL)));
                personModel.setPointPerson(cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_POINT)));
                personModel.setCountryPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_COUNTRY)));
                personModel.setSexPerson((byte) cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_SEX)));
                personModel.setImgPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_IMG_PERSON)));

                listPerson.add(personModel);

                cursor.moveToNext();
            }
        }
        return listPerson;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==Fragments.REQUEST_IMAGE_CAPTURE && resultCode==getActivity().RESULT_OK){

            Picasso.with(getActivity()).load(mCurrentPhotoPath).into(imgProfile);
            addPictureToGalery();
            Toast.makeText(getActivity(),"Guradado en: "+mCurrentPhotoPath,Toast.LENGTH_LONG).show();
        }
    }
}
