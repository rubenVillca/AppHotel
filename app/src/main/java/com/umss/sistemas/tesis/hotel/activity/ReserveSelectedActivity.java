package com.umss.sistemas.tesis.hotel.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.ReserveSelectPriceAdapterRecycler;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.model.ReserveSearchModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.Objects;

public class ReserveSelectedActivity extends ActivityParent {

    private ReserveSearchModel reserveSearchModel;
    private boolean isMember;
    private int idCheck;
    private int nAdult;
    private int nBoy;
    private String dateIn;
    private String dateOut;
    private String timeIn;
    private String timeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_selected);

        super.showToolBar("", true);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(new Fade());
        }

        buildBundle();
        initContent();
        setRecyclerView();
    }
    private void buildBundle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            idCheck= Objects.requireNonNull(getIntent().getExtras()).getInt("idCheck");
            nAdult = getIntent().getExtras().getInt("nAdult");
            nBoy = getIntent().getExtras().getInt("nBoy");
            isMember = getIntent().getExtras().getBoolean("isMember");
            dateIn = getIntent().getExtras().getString("dateIn");
            dateOut = getIntent().getExtras().getString("dateOut");
            timeIn = getIntent().getExtras().getString("timeIn");
            timeOut = getIntent().getExtras().getString("timeOut");
            reserveSearchModel = (ReserveSearchModel) getIntent().getExtras().getSerializable("reserveSearchModel");
        }
    }

    public void initContent() {
        TextView nAdultTextView = findViewById(R.id.nPersonAdultReserve);
        nAdultTextView.setText(String.valueOf(nAdult + " Adultos"));

        TextView nBoytTextView = findViewById(R.id.nPersonBoyReserve);
        nBoytTextView.setText(String.valueOf(nBoy + " Niños"));

        TextView dateInTextView = findViewById(R.id.dateTimeInReserve);
        dateInTextView.setText(String.valueOf(dateIn + "\n" + timeIn + " Horas"));

        TextView dateOutTextView = findViewById(R.id.dateTimeOutReserve);
        dateOutTextView.setText(String.valueOf(dateOut + "\n" + timeOut + " Horas"));

        ImageView imageView = findViewById(R.id.imageHeaderCollapsing);
        Picasso.with(this).load(Conexion.urlServer+ reserveSearchModel.getImageTypeRoom()).into(imageView);
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.priceRoomRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        ReserveSelectPriceAdapterRecycler homeAdapter = new ReserveSelectPriceAdapterRecycler(
                reserveSearchModel.getServicePriceConsumeModels(),
                R.layout.cardview_reserve_price,
                this,
                nAdult,
                nBoy,
                dateIn,
                timeIn,
                dateOut,
                timeOut,
                reserveSearchModel.getUnitRoom(),
                reserveSearchModel.getIdTypeRoom(),
                isMember,
                idCheck
        );
        recyclerView.setAdapter(homeAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
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
