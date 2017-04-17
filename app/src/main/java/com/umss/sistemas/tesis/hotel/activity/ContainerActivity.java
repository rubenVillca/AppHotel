package com.umss.sistemas.tesis.hotel.activity;

import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.fragments.HomeFragment;
import com.umss.sistemas.tesis.hotel.fragments.ProfileFragment;
import com.umss.sistemas.tesis.hotel.fragments.ReserveFragment;
import com.umss.sistemas.tesis.hotel.fragments.SearchFragment;
import com.umss.sistemas.tesis.hotel.fragments.ServicesFragment;
import com.umss.sistemas.tesis.hotel.util.Activities;
import com.umss.sistemas.tesis.hotel.util.Fragments;

public class ContainerActivity extends Activities {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        BottomBar bottomBar=(BottomBar)findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.tabHome);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragments fragment = null;
                switch (tabId){
                    case R.id.tabHome:
                        fragment=new HomeFragment();
                        break;
                    case R.id.tabProfile:
                        fragment=new ProfileFragment();
                        break;
                    case R.id.tabSearch:
                        fragment=new SearchFragment();
                        break;
                    case R.id.tabReserve:
                        fragment=new ReserveFragment();
                        break;
                    case R.id.tabService:
                        fragment=new ServicesFragment();
                        break;
                }
                if (fragment!=null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {

    }
}
