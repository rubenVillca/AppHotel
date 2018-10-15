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
import com.umss.sistemas.tesis.hotel.model.ConsumeServiceModel;
import com.umss.sistemas.tesis.hotel.model.MemberModel;

public class ReserveListMemberAdapterRecycler extends RecyclerView.Adapter<ReserveListMemberAdapterRecycler.MemberReserveViewHolder> {

    private ConsumeServiceModel consumeServiceModel;
    private int resource;
    private Activity activity;

    public ReserveListMemberAdapterRecycler(ConsumeServiceModel consumeServiceModel, int resource, Activity activity) {
        this.consumeServiceModel = consumeServiceModel;
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
        holder.memberReserveTextView.setText(String.valueOf(" "+(position + 1)+": "));
        if (consumeServiceModel.getMemberModelArrayList().size()>0) {
            final MemberModel memberModel = consumeServiceModel.getMemberModelArrayList().get(position);
            holder.memberNameReserveTextView.setText(memberModel.getNamePerson() + " " + memberModel.getNameLastPerson());

            holder.btnEditCardViewReserveMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ReserveMemberActivity.class);
                    intent.putExtra("idConsume", consumeServiceModel.getIdConsum());
                    intent.putExtra("member",memberModel);
                    activity.startActivity(intent);
                }
            });
        }else{
            holder.memberNameReserveTextView.setText("");

            holder.btnEditCardViewReserveMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MemberModel memberModel=new MemberModel();
                    memberModel.setIdKeyConsum(consumeServiceModel.getIdConsum());

                    Intent intent = new Intent(activity, ReserveMemberActivity.class);
                    intent.putExtra("idConsume", consumeServiceModel.getIdConsum());
                    intent.putExtra("member",memberModel);
                    intent.putExtra("idCheck",consumeServiceModel.getIdKeyCheck());
                    activity.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (consumeServiceModel.getMemberModelArrayList().size()>0) {
            return consumeServiceModel.getMemberModelArrayList().size();
        }else{
            return consumeServiceModel.getReserveModelArrayList().size();
        }
    }

    class MemberReserveViewHolder extends RecyclerView.ViewHolder {
        TextView memberReserveTextView;
        TextView memberNameReserveTextView;
        ImageView btnEditCardViewReserveMember;

        private MemberReserveViewHolder(View itemView) {
            super(itemView);
            memberReserveTextView = itemView.findViewById(R.id.memberReserveTextView);
            memberNameReserveTextView = itemView.findViewById(R.id.memberNameReserveTextView);
            btnEditCardViewReserveMember = itemView.findViewById(R.id.btnEditCardViewReserveMember);
        }
    }
}
