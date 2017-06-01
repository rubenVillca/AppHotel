package com.umss.sistemas.tesis.hotel.parent;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;

public class ActivityParent extends AppCompatActivity {
    protected HelperSQLiteInsert helperSQLiteInsert;
    protected HelperSQLiteObtain helperSQLiteObtain;

    protected ProgressBar progressBar;
    protected View container;

    protected void showToolBar(String tittle, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        getSupportActionBar().setDisplayShowHomeEnabled(upButton);
    }

    /**
     * muestra el progress bar mientras carga el activity
     * @param show:estado del progressBar
     */
    protected void showProgress(final boolean show) {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        container.setVisibility(show ? View.GONE : View.VISIBLE);
        container.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                container.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        progressBar.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    /**
     * mostrar mensajes en Toast
     * @param m:mensaje
     */
    protected void showMesaje(String m) {
        Toast.makeText(getApplicationContext(), m, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (helperSQLiteInsert !=null) {
            helperSQLiteInsert.destroy();
        }
        if (helperSQLiteObtain!=null) {
            helperSQLiteObtain.destroy();
        }
    }
}
