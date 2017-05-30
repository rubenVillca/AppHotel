package com.umss.sistemas.tesis.hotel.activity;

import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;
import com.umss.sistemas.tesis.hotel.model.ServicePriceModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;
import com.umss.sistemas.tesis.hotel.table.TablePriceService;

import java.util.ArrayList;

public class ServiceDetailActivity extends ActivityParent {

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
        return bundle.getInt("idService");
    }

    private void chargeContent(int idService) {
        helperSQLiteObtain =new HelperSQLiteObtain(this);
        ServiceModel serviceModel= helperSQLiteObtain.getServiceModel(idService).get(0);
        ArrayList<ServicePriceModel> servicePriceModel=serviceModel.getServicePrice();

        ImageView imageService=(ImageView)findViewById(R.id.imageHeaderCollapsing);
        Picasso.with(this).load(Conexion.urlServer + serviceModel.getServiceImage()).into(imageService);

        TextView nameService=(TextView)findViewById(R.id.nameServiceDetailTextView);
        nameService.setText(serviceModel.getServiceName());

        TextView typeService=(TextView)findViewById(R.id.typeNameServiceDetailTextView);
        typeService.setText(serviceModel.getServiceType());

        TextView descriptionService=(TextView)findViewById(R.id.contentServiceDetailTextView);
        descriptionService.setText(serviceModel.getServiceDescription());

        TablePriceService tabla = new TablePriceService(this, (TableLayout)findViewById(R.id.tablePriceService));

        tabla.agregarCabecera(R.array.header_table_price_services);

        for(ServicePriceModel price: servicePriceModel){
            ArrayList<String> elementos = new ArrayList<>();

            elementos.add("("+price.getServicePriceNameMoney()+") "+price.getServicePricePrice());
            elementos.add((price.getServicePriceDay()*24+price.getServicePriceHour())+ " Horas");
            elementos.add(String.valueOf(price.getServicePriceUnit()));
            elementos.add(String.valueOf(price.getServicePricePointObtain()));
            elementos.add(String.valueOf(price.getServicePricePointRequired()));

            tabla.agregarFilaTabla(elementos);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
