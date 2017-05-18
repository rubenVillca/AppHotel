package com.umss.sistemas.tesis.hotel.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.activity.ContainerActivity;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.parent.FragmentParent;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends FragmentParent {

    public ContactFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message_send, container, false);
        super.showToolBar(getResources().getString(R.string.tab_message_new), false, view);
        super.showFloatingButtonMessage(view);

        setSpinner(view);
        showButtonMessage(view);

        return view;
    }

    /**
     * cargar lista de tipos de usuarios en el spinner
     * @param view:actividad en ejecucion
     */
    private void setSpinner(View view) {
        String[] valores = {"Administrador", "Recepcionista"};

        Spinner spinner = (Spinner) view.findViewById(R.id.contactSpinner);
        spinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, valores));
    }

    private void showButtonMessage(final View view) {
        Button button = (Button) view.findViewById(R.id.btnContactSend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText=(EditText)view.findViewById(R.id.contactEditText);
                String messsage=editText.getText().toString();

                EditText titleMessage=(EditText)view.findViewById(R.id.contactTitle);
                String title=titleMessage.getText().toString();

                Spinner spin = (Spinner) view.findViewById(R.id.contactSpinner);
                String receiver = spin.getSelectedItem().toString();
                receiver=receiver.equals("Administrador")?"admin":"recepcion";

                if (!title.isEmpty()&&!messsage.isEmpty())
                    sendMessage(title,messsage,receiver);
                else{
                    editText.setError("Ingrese un titulo y un contenido");
                    titleMessage.setError("Ingrese un titulo y un contenido");
                }

            }
        });
    }

    /**
     * enviar mensaje de sugerencia al webserver
     *  @param title :titulo del mensaje
     * @param messsage :mensaje a enviar
     * @param receiver: usuaraio/s a los q se esta enviando el mensaje
     */
    private void sendMessage(String title, String messsage, String receiver) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        helperSQLiteObtain=new HelperSQLiteObtain(getContext());
        int idPerson=helperSQLiteObtain.getLoginModel().getIdPerson();

        params.put("message",messsage);
        params.put("tittle",title);
        params.put("android","android");
        params.put("receiver",receiver);
        params.put("idPerson",idPerson);

        client.post(Conexion.getUrlServer(Conexion.CONTACT), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    int idMessage=0;
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        idMessage=obj.getInt("isSave");
                    } catch (JSONException e) {
                        showMessage("Error de consulta");
                        //showProgress(false);
                    }

                    if (idMessage>0) {
                        Intent intent = new Intent(getActivity(), ContainerActivity.class);
                        startActivity(intent);
                        showMessage("Mensaje Enviado");
                    }
                    if (idMessage==0) {
                        showMessage("No se envio su mensaje intente nuevamente");
                    }
                    if (idMessage<0) {
                        showMessage("Error al insertar a la BD del webserver");
                    }
                    //showProgress(false);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //showProgress(false);
                showMessage("No se ha podido establecer conecion con el servidor");
            }
        });
    }
    private void showMessage(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }
}
