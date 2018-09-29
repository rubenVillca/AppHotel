package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;
import com.umss.sistemas.tesis.hotel.model.ServicePriceDetailModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;
import com.umss.sistemas.tesis.hotel.table.TablePriceService;

import java.util.ArrayList;
import java.util.Objects;

public class ServiceDetailActivity extends ActivityParent {
    private FloatingActionButton fab;
    private ServiceModel serviceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        super.showToolBar("",true);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(new Fade());
        }

        buildData();
        initContent();
    }

    private void buildData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            serviceModel=(ServiceModel) Objects.requireNonNull(getIntent().getExtras()).getSerializable("serviceModel");
        }

        fab= findViewById(R.id.fabOrderService);
        helperSQLiteObtain =new HelperSQLiteObtain(this);

        boolean isChecked=helperSQLiteObtain.getCheckModel(0,1,2).size()>0;
        if (!isChecked||serviceModel.getValueType()!=1)
            fab.setVisibility(View.GONE);

    }

    private void initContent() {
        ArrayList<ServicePriceDetailModel> servicePriceDetailModel =serviceModel.getServicePrice();

        ImageView imageService= findViewById(R.id.imageHeaderCollapsing);
        Picasso.with(this).load(Conexion.urlServer + serviceModel.getImage()).into(imageService);

        TextView nameService= findViewById(R.id.nameServiceDetailTextView);
        nameService.setText(serviceModel.getName());

        TextView typeService= findViewById(R.id.typeNameServiceDetailTextView);
        typeService.setText(serviceModel.getNameType());

        TextView descriptionService= findViewById(R.id.contentServiceDetailTextView);
        descriptionService.setText(android.text.Html.fromHtml(serviceModel.getDescription()));

        TablePriceService tabla = new TablePriceService(this, (TableLayout)findViewById(R.id.tablePriceService));

        tabla.agregarCabecera(R.array.header_table_price_services);

        for(ServicePriceDetailModel price: servicePriceDetailModel){
            ArrayList<String> elementos = new ArrayList<>();

            elementos.add("("+price.getServicePriceNameMoney()+") "+price.getServicePricePrice());
            elementos.add((price.getServicePriceDay()*24+price.getServicePriceHour())+ " Horas");
            elementos.add(String.valueOf(price.getServicePriceUnit()));
            elementos.add(String.valueOf(price.getServicePricePointObtain()));
            elementos.add(String.valueOf(price.getServicePricePointRequired()));

            tabla.agregarFilaTabla(elementos);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goConsumeService();
            }
        });
    }

    private void goConsumeService() {
        Intent intent=new Intent(this,ConsumeServiceActivity.class);
        intent.putExtra("serviceModel",serviceModel);
        startActivity(intent);
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
