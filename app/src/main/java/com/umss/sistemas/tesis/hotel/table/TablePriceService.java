package com.umss.sistemas.tesis.hotel.table;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.ArrayList;

public class TablePriceService {
    private TableLayout table;
    private ArrayList<TableRow> tableRows; // Array de las filas de la tabla
    private Activity activity;
    private Resources resources;
    private int rows; // Filas y columnas de nuestra tabla

    public TablePriceService(ActivityParent activity, TableLayout tableLayout) {
        this.table = tableLayout;
        this.activity = activity;
        this.resources = activity.getResources();

        this.tableRows = new ArrayList<>();
    }

    /**
     * Añade la cabecera a la tabla
     * @param recursocabecera Recurso (array) donde se encuentra la cabecera de la tabla
     */
    public void agregarCabecera(int recursocabecera) {
        TableRow.LayoutParams layoutCelda;
        TableRow fila = new TableRow(activity);
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        fila.setLayoutParams(layoutFila);

        String[] arraycabecera = resources.getStringArray(recursocabecera);

        for (String contentCeldaTable : arraycabecera) {
            TextView texto = new TextView(activity);
            layoutCelda = new TableRow.LayoutParams(getWidhtCeldaText(contentCeldaTable), TableRow.LayoutParams.WRAP_CONTENT);
            texto.setText(contentCeldaTable);
            texto.setTextSize(20);
            texto.setGravity(Gravity.CENTER_HORIZONTAL);
            //texto.setTextAppearance(activity, R.style.estilo_celda);
            //texto.setBackgroundResource(R.drawable.tabla_celda_cabecera);
            texto.setLayoutParams(layoutCelda);

            fila.addView(texto);
        }

        table.addView(fila);
        tableRows.add(fila);

        rows++;
    }

    /**
     * Agrega una fila a la tabla
     * @param elementos Elementos de la fila
     */
    public void agregarFilaTabla(ArrayList<String> elementos) {
        TableRow.LayoutParams layoutCelda;
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        TableRow fila = new TableRow(activity);
        fila.setLayoutParams(layoutFila);

        for(String elemento:elementos) {
            TextView texto = new TextView(activity);
            texto.setText(String.valueOf(elemento));
            texto.setTextSize(20);
            texto.setSingleLine();
            texto.setGravity(Gravity.CENTER_HORIZONTAL);
            //texto.setTextAppearance(activity, R.style.estilo_celda);
            //texto.setBackgroundResource(R.drawable.tabla_celda);
            layoutCelda = new TableRow.LayoutParams(getWidhtCeldaText(texto.getText().toString()), TableRow.LayoutParams.WRAP_CONTENT);
            texto.setLayoutParams(layoutCelda);

            fila.addView(texto);
        }

        table.addView(fila);
        tableRows.add(fila);

        rows++;
    }

    /**
     * Obtiene el ancho en píxeles de un texto en un String
     * @param texto: Texto
     * @return Ancho en píxeles del texto
     */
    private int getWidhtCeldaText(String texto) {
        Rect bounds = new Rect();

        Paint p = new Paint();
        p.setTextSize(40);

        p.getTextBounds(texto, 0, texto.length(), bounds);
        return bounds.width();
    }
}
