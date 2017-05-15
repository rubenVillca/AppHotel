package com.umss.sistemas.tesis.hotel.parent;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;

public class ActivityParent extends AppCompatActivity {
    protected HelperSQLiteInsert helperSQLiteInsert;
    protected HelperSQLiteObtain helperSQLiteObtain;

    protected View progressView;
    protected View container;

    protected void showToolBar(String tittle, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
    }

    /**
     * muestra el progress bar mientras carga el activity
     * @param show:estado del progressBar
     */
    protected void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        container.setVisibility(show ? View.VISIBLE : View.GONE);
        container.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                container.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });

        progressView.setVisibility(show ? View.GONE : View.VISIBLE);
        progressView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressView.setVisibility(show ? View.GONE : View.VISIBLE);
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
        if (helperSQLiteInsert !=null)
            helperSQLiteInsert.destroy();
        super.onDestroy();
    }
}
