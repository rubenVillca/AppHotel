package com.umss.sistemas.tesis.hotel.parent;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.helper.ServiceGet;
import com.umss.sistemas.tesis.hotel.helper.ServiceInsert;

import java.util.Objects;

@SuppressLint("Registered")
public class ActivityParent extends AppCompatActivity {
    protected ServiceInsert ServiceInsert;
    protected ServiceGet serviceGet;

    protected ProgressBar progressBar;
    protected View container;

    protected void showToolBar(String tittle, boolean upButton) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(tittle);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(upButton);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(upButton);
        }
    }

    /**
     * muestra el progress bar mientras carga el activity
     *
     * @param show:estado del progressBar
     */
    protected void showProgress(final boolean show) {
        progressBar = findViewById(R.id.progressBar);

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        if (show) {
            container.setVisibility(View.GONE);
            container.animate().setDuration(shortAnimTime).alpha(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    container.setVisibility(View.GONE);
                }
            });

            progressBar.setVisibility(View.VISIBLE);
            progressBar.animate().setDuration(shortAnimTime).alpha(1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(View.GONE);
                }
            });
            container.setVisibility(View.VISIBLE);
            container.animate().setDuration(shortAnimTime).alpha(1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    container.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    /**
     * mostrar mensajes en Toast
     *
     * @param m:mensaje
     */
    protected void showMessaje(String m) {
        Toast.makeText(getApplicationContext(), m, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ServiceInsert != null) {
            ServiceInsert.destroy();
        }
        if (serviceGet != null) {
            serviceGet.destroy();
        }
    }
}
