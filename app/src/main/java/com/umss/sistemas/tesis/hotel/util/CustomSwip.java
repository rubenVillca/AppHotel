package com.umss.sistemas.tesis.hotel.util;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

        ImageView imageView= (ImageView) itemView.findViewById(R.id.swip_imageview);
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
