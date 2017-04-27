package com.umss.sistemas.tesis.hotel.activity;

import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLite;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;
import com.umss.sistemas.tesis.hotel.parent.Activities;

public class ServiceDetailActivity extends Activities {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        super.showToolBar("",true);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(new Fade());
        }


        int idService = getIdService();

        chargeContent(idService);
    }

    private int getIdService() {
        Bundle bundle=getIntent().getExtras();
        return bundle.getInt("idPosition");
    }

    private void chargeContent(int idService) {
        helperSQLite=new HelperSQLite(this);
        ServiceModel serviceModel=helperSQLite.getServiceModel().get(idService);

        ImageView imageService=(ImageView)findViewById(R.id.imageServiceHeaderCollapsing);
        Picasso.with(this).load(Conexion.urlServer + serviceModel.getServiceImage()).into(imageService);

        TextView nameService=(TextView)findViewById(R.id.nameServiceDetailTextView);
        nameService.setText(serviceModel.getServiceName());

        TextView typeService=(TextView)findViewById(R.id.typeNameServiceDetailTextView);
        typeService.setText(serviceModel.getServiceType());

        TextView descriptionService=(TextView)findViewById(R.id.contentServiceDetailTextView);
        descriptionService.setText(serviceModel.getServiceDescription());

    }

}
