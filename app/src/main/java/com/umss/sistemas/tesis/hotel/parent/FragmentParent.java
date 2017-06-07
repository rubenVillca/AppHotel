package com.umss.sistemas.tesis.hotel.parent;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.activity.AboutActivity;
import com.umss.sistemas.tesis.hotel.activity.CalendarActivity;
import com.umss.sistemas.tesis.hotel.activity.ConsumeActivity;
import com.umss.sistemas.tesis.hotel.activity.HistoryActivity;
import com.umss.sistemas.tesis.hotel.activity.LocationActivity;
import com.umss.sistemas.tesis.hotel.activity.MenuFoodActivity;
import com.umss.sistemas.tesis.hotel.activity.MessagesActivity;
import com.umss.sistemas.tesis.hotel.activity.OffersActivity;
import com.umss.sistemas.tesis.hotel.activity.ReserveVerifyActivity;
import com.umss.sistemas.tesis.hotel.activity.ServicesActivity;
import com.umss.sistemas.tesis.hotel.activity.SitesTourActivity;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;

public class FragmentParent extends Fragment implements View.OnClickListener {

    protected HelperSQLiteInsert helperSQLiteInsert;
    protected HelperSQLiteObtain helperSQLiteObtain;
    protected String mCurrentPhotoPath;
    protected static final int REQUEST_IMAGE_CAPTURE = 1;
    protected ActivityParent containerActivity;

    /**
     * barra superior de la activity en la q esta el boton de atras y el nombre de la misma
     *
     * @param tittle:   nombre de la actividad
     * @param upButton: estado del boton true se se ve
     * @param view:     Activity en la q se muestra el boton
     */
    @SuppressWarnings("ConstantConditions")
    protected void showToolBar(String tittle, boolean upButton, View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    /**
     * boton flotante para acceder a los mensajes recibidos
     *
     * @param view: actividad en la estar el boton de mensaje
     */
    protected void showFloatingButtonMessage(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        if (helperSQLiteInsert != null)
            helperSQLiteInsert.destroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        containerActivity = (ActivityParent) getActivity();
        containerActivity.container = getActivity().findViewById(R.id.container);

        helperSQLiteInsert = new HelperSQLiteInsert(getContext());
        helperSQLiteObtain = new HelperSQLiteObtain(getContext());

        switch (v.getId()) {
            case R.id.fab:
                goMessageActivity();
                break;
            case R.id.imageOffer:
                goOfferActivity();
                break;
            case R.id.imageSiteTour:
                goSiteTourActivity();
                break;
            case R.id.imageService:
                goServiceActivity();
                break;
            case R.id.imageAboutHotel:
                goAboutActivity();
                break;
            case R.id.imageReserve:
                goReserveVerifyActivity();
                break;
            case R.id.imageLocationMap:
                goLocationActivity();
                break;
            case R.id.imageActivity:
                goCalendarActivity();
                break;
            case R.id.imageServiceFood:
                goServiceFoodActivity();
                break;
            case R.id.imageHistory:
                goHistoryActivity();
                break;
            case R.id.imageConsum:
                goConsumeActivity();
                break;
        }
    }

    //*************************************GO_ACTIVITY**********************************************

    /**
     * Cambiar de activity a AboutActivity
     */
    private void goAboutActivity() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a serviceActivity
     */
    private void goServiceActivity() {
        Intent intent = new Intent(getActivity(), ServicesActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a locationActivity
     */
    private void goLocationActivity() {
        Intent intent = new Intent(getActivity(), LocationActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a siteTourActivity
     */
    private void goSiteTourActivity() {
        Intent intent = new Intent(getActivity(), SitesTourActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a OffersActivity
     */
    private void goOfferActivity() {
        Intent intent = new Intent(getActivity(), OffersActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a messageActivity
     */
    private void goMessageActivity() {
        Intent intent = new Intent(getActivity(), MessagesActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a MenuFoodActivity
     */
    private void goServiceFoodActivity() {
        Intent intent = new Intent(getActivity(), MenuFoodActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a CalendarActivity
     */
    private void goCalendarActivity() {
        Intent intent = new Intent(getActivity(), CalendarActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a HistoryActivity
     */
    private void goHistoryActivity() {
        Intent intent = new Intent(getActivity(), HistoryActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a ConsumeActivity
     */
    private void goConsumeActivity() {
        Intent intent = new Intent(getActivity(), ConsumeActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a ReserveSearchActivity
     */
    private void goReserveVerifyActivity() {
        Intent intent = new Intent(getActivity(), ReserveVerifyActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}
