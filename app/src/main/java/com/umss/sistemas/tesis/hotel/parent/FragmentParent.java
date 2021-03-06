package com.umss.sistemas.tesis.hotel.parent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.activity.AboutActivity;
import com.umss.sistemas.tesis.hotel.activity.CalendarActivity;
import com.umss.sistemas.tesis.hotel.activity.ConsumeActivity;
import com.umss.sistemas.tesis.hotel.activity.ContactActivity;
import com.umss.sistemas.tesis.hotel.activity.FrequentlyActivity;
import com.umss.sistemas.tesis.hotel.activity.HistoryActivity;
import com.umss.sistemas.tesis.hotel.activity.LocationActivity;
import com.umss.sistemas.tesis.hotel.activity.MenuFoodActivity;
import com.umss.sistemas.tesis.hotel.activity.OffersActivity;
import com.umss.sistemas.tesis.hotel.activity.ProfileActivity;
import com.umss.sistemas.tesis.hotel.activity.ReserveVerifyActivity;
import com.umss.sistemas.tesis.hotel.activity.ServicesActivity;
import com.umss.sistemas.tesis.hotel.activity.SitesTourActivity;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.ServiceHelper;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FragmentParent extends Fragment implements View.OnClickListener {
    protected ServiceHelper serviceHelper;

    protected ActivityParent containerActivity;
    private AsyncHttpClient client;
    private RequestParams params;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * barra superior de la activity en la q esta el boton de atras y el nombre de la misma
     *
     * @param tittle:   nombre de la actividad
     * @param upButton: estado del boton true se se ve
     */
    @SuppressWarnings("ConstantConditions")
    protected void showToolBar(String tittle, boolean upButton) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @Override
    public void onDestroy() {
        if (serviceHelper != null)
            serviceHelper.destroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        containerActivity = (ActivityParent) getActivity();
        containerActivity.container = getActivity().findViewById(R.id.viewpager);

        serviceHelper = new ServiceHelper(getContext());

        switch (v.getId()) {
            case R.id.imageOffer:
            case R.id.layoutOffer:
                goOfferActivity();
                break;
            case R.id.imageSiteTour:
            case R.id.layoutSiteTour:
                goSiteTourActivity();
                break;
            case R.id.imageService:
            case R.id.layoutService:
                goServiceActivity();
                break;
            case R.id.imageReserve:
            case R.id.layoutReserve:
                goReserveVerifyActivity();
                break;
            case R.id.imageLocationMap:
            case R.id.layoutLocationMap:
                goLocationActivity();
                break;
            case R.id.imageActivity:
            case R.id.layoutActivity:
                goCalendarActivity();
                break;
            case R.id.imageServiceFood:
            case R.id.layoutServiceFood:
                goServiceFoodActivity();
                break;
            case R.id.imageHistory:
            case R.id.layoutHistory:
                goHistoryActivity();
                break;
            case R.id.imageConsum:
            case R.id.layoutConsum:
                goConsumeActivity();
                break;
            case R.id.imageProfile:
            case R.id.layoutProfile:
                goProfileActivity();
                break;
            case R.id.imageAbout:
            case R.id.layoutAbout:
                goAboutActivity();
                break;
            case R.id.imageFrequently:
            case R.id.layoutFrequently:
                goFrequentlyActivity();
                break;
            case R.id.imageContact:
            case R.id.layoutContact:
                goContactActivity();
                break;
        }
    }

    private void goContactActivity() {
        Intent intent = new Intent(getActivity(), ContactActivity.class);
        startActivity(intent);
    }

    private void goFrequentlyActivity() {
        Intent intent = new Intent(getActivity(), FrequentlyActivity.class);
        startActivity(intent);
    }

    private void goAboutActivity() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }

    private void goProfileActivity() {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
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
        containerActivity.showProgressUnit(true);
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
        containerActivity.showProgressUnit(true);
        int idPerson = serviceHelper.getLoginModel().getIdPerson();

        client = new AsyncHttpClient();
        params = new RequestParams();

        params.put("android", "android");
        params.put("idPerson", idPerson);

        client.post(Conexion.CHECK, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        serviceHelper.syncUpCheck(obj);
                    } catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                Intent intent = new Intent(getActivity(), ConsumeActivity.class);
                startActivity(intent);
                containerActivity.showProgressUnit(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                Intent intent = new Intent(getActivity(), ConsumeActivity.class);
                startActivity(intent);
                containerActivity.showProgressUnit(false);
            }
        });
    }

    /**
     *
     * cambiar de activity a ReserveSearchActivity
     */
    private void goReserveVerifyActivity() {
        Intent intent = new Intent(getActivity(), ReserveVerifyActivity.class);
        startActivity(intent);
    }
}
