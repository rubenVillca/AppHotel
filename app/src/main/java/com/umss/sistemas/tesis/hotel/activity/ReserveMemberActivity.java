package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.MemberModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

public class ReserveMemberActivity extends ActivityParent {
    private MemberModel memberModel;

    private EditText reserveMemberName;
    private EditText reserveMemberLastName;
    private EditText reserveMemberDateBorn;
    private EditText reserveMemberPhone;
    private EditText reserveMemberDocument;
    private EditText reserveMemberEmail;
    private EditText reserveMemberAddress;
    private RadioButton reserveMemberRadioHombre;
    private RadioButton reserveMemberRadioMujer;
    private EditText reserveMemberCity;
    private EditText reserveMemberCountry;
    private Button btnMemberCancel;
    private Button btnMemberReserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_member);

        super.showToolBar("Huéspedes", true);

        updateBundle();
        initContent();
        updateContent();
    }

    private void initContent() {
        reserveMemberName = (EditText) findViewById(R.id.reserveMemberName);
        reserveMemberLastName = (EditText) findViewById(R.id.reserveMemberLastName);
        reserveMemberDateBorn = (EditText) findViewById(R.id.reserveMemberDateBorn);
        reserveMemberPhone = (EditText) findViewById(R.id.reserveMemberPhone);
        reserveMemberDocument = (EditText) findViewById(R.id.reserveMemberDocument);
        reserveMemberEmail = (EditText) findViewById(R.id.reserveMemberEmail);
        reserveMemberAddress = (EditText) findViewById(R.id.reserveMemberAddress);
        reserveMemberRadioHombre = (RadioButton) findViewById(R.id.reserveMemberRadioHombre);
        reserveMemberRadioMujer = (RadioButton) findViewById(R.id.reserveMemberRadioMujer);
        reserveMemberCity = (EditText) findViewById(R.id.reserveMemberCity);
        reserveMemberCountry = (EditText) findViewById(R.id.reserveMemberCountry);
        btnMemberCancel = (Button) findViewById(R.id.btnMemberCancel);
        btnMemberReserve = (Button) findViewById(R.id.btnMemberReserve);
    }

    private void updateBundle() {
        memberModel = (MemberModel) getIntent().getExtras().getSerializable("member");
    }

    private void updateContent() {
        if (!memberModel.getNamePerson().isEmpty()) {
            reserveMemberName.setText(memberModel.getNamePerson());
            reserveMemberLastName.setText(memberModel.getNameLastPerson());
            reserveMemberDateBorn.setText(memberModel.getDateBornPerson());
            reserveMemberPhone.setText(String.valueOf(memberModel.getNumberPhone()));
            reserveMemberDocument.setText(String.valueOf(memberModel.getNumberDocument()));
            reserveMemberEmail.setText(memberModel.getEmailPerson());
            reserveMemberAddress.setText(memberModel.getAddressPerson());
            reserveMemberRadioHombre.setChecked(memberModel.getSexPerson() == 1);
            reserveMemberRadioMujer.setChecked(memberModel.getSexPerson() == 0);
            reserveMemberCity.setText(memberModel.getCityPerson());
            reserveMemberCountry.setText(memberModel.getCountryPerson());
        }
    }

    public void goReserveVerifyActivity(View view) {
        Intent intent = new Intent(this, ReserveVerifyActivity.class);
        startActivity(intent);
        finish();
    }
}
