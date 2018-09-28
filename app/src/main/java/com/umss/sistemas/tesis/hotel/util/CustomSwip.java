package com.umss.sistemas.tesis.hotel.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.model.SiteTourImageModel;

import java.util.ArrayList;

public class CustomSwip extends PagerAdapter{

    private ArrayList<SiteTourImageModel> imagesResources;
    private Context context;

    public CustomSwip(ArrayList<SiteTourImageModel> imagesResources, Context context) {
        this.imagesResources = imagesResources;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imagesResources.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView= layoutInflater.inflate(R.layout.actionbar_viewpager_image,container,false);
        //itemView.setBackgroundColor(Color.GREEN);
        itemView.setPadding(0,0,0,0);
        itemView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        itemView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

        ImageView imageView= itemView.findViewById(R.id.swip_imageview);
        imageView.setBackgroundColor(Color.GRAY);
        imageView.setPadding(0,0,0,0);
        imageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        imageView.getLayoutParams().height = 720;

        Picasso.with(context).load(Conexion.urlServer + imagesResources.get(position).getAddressSiteTour()).into(imageView);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
