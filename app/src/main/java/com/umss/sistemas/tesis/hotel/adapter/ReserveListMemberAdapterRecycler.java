package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.activity.ReserveMemberActivity;
import com.umss.sistemas.tesis.hotel.model.ConsumeModel;
import com.umss.sistemas.tesis.hotel.model.MemberModel;

public class ReserveListMemberAdapterRecycler extends RecyclerView.Adapter<ReserveListMemberAdapterRecycler.MemberReserveViewHolder> {

    private ConsumeModel consumeModel;
    private int resource;
    private Activity activity;

    public ReserveListMemberAdapterRecycler(ConsumeModel consumeModel, int resource, Activity activity) {
        this.consumeModel = consumeModel;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public MemberReserveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new MemberReserveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MemberReserveViewHolder holder, int position) {
        final MemberModel memberModel=consumeModel.getMemberModelArrayList().get(position);
        holder.memberReserveTextView.setText(String.valueOf(" "+(position + 1)+": "));
        holder.memberNameReserveTextView.setText(memberModel.getNamePerson() +" "+memberModel.getNameLastPerson());

        holder.btnEditCardViewReserveMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReserveMemberActivity.class);
                intent.putExtra("idConsume",consumeModel.getIdConsum());
                intent.putExtra("member",memberModel);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return consumeModel.getMemberModelArrayList().size();
    }

    class MemberReserveViewHolder extends RecyclerView.ViewHolder {
        TextView memberReserveTextView;
        TextView memberNameReserveTextView;
        ImageView btnEditCardViewReserveMember;

        private MemberReserveViewHolder(View itemView) {
            super(itemView);
            memberReserveTextView = (TextView) itemView.findViewById(R.id.memberReserveTextView);
            memberNameReserveTextView =(TextView)itemView.findViewById(R.id.memberNameReserveTextView);
            btnEditCardViewReserveMember = (ImageView) itemView.findViewById(R.id.btnEditCardViewReserveMember);
        }
    }
}