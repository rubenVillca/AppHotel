package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.FrequentlyModel;

import java.util.ArrayList;

public class FrequentlyAdapterRecycler extends RecyclerView.Adapter<FrequentlyAdapterRecycler.FrequentlyViewHolder>{

    private ArrayList<FrequentlyModel> frequentlyList;
    private int resource;
    private Activity activity;

    public FrequentlyAdapterRecycler(ArrayList<FrequentlyModel> frequentlyList, int resource, Activity activity) {
        this.frequentlyList = frequentlyList;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public FrequentlyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new FrequentlyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FrequentlyViewHolder holder, int position) {
        FrequentlyModel frequentlyModel = frequentlyList.get(position);

        holder.nQuestionCardView.setText(position+1+".- ");
        holder.questionCardView.setText(frequentlyModel.getQuestion());
        holder.responseCardView.setText(android.text.Html.fromHtml(frequentlyModel.getResponse()).toString());
    }

    @Override
    public int getItemCount() {
        return frequentlyList.size();
    }

    class FrequentlyViewHolder extends RecyclerView.ViewHolder{
        private TextView nQuestionCardView;
        private TextView questionCardView;
        private TextView responseCardView;

        private FrequentlyViewHolder(View itemView) {
            super(itemView);

            nQuestionCardView =(TextView)itemView.findViewById(R.id.nQuestionCardView);
            questionCardView =(TextView)itemView.findViewById(R.id.questionCardView);
            responseCardView =(TextView)itemView.findViewById(R.id.responseCardViewText);
        }
    }
}
