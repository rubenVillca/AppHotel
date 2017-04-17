package com.umss.sistemas.tesis.hotel.util;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.umss.sistemas.tesis.hotel.R;

public class Activities extends AppCompatActivity {

    protected View progressView;
    protected View container;

    protected void showToolBar(String tittle, boolean upButton){
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar);
    }


    protected void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        progressView.setVisibility(show ? View.GONE : View.VISIBLE);
        progressView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        container.setVisibility(show ? View.VISIBLE : View.GONE);
        container.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                container.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
    protected void showMesaje(String m) {
        Toast.makeText(getApplicationContext(), m, Toast.LENGTH_LONG).show();
    }
}
