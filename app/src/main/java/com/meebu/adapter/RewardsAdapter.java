package com.meebu.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.goibibo.libs.views.ScratchRelativeLayoutView;
import com.meebu.CustomerCareActivity;
import com.meebu.DialogActivity;
import com.meebu.FullscreenActivity;
import com.meebu.InviteFriendsActivity;
import com.meebu.MyProfileActivity;
import com.meebu.OrderStatusActivity;
import com.meebu.R;
import com.meebu.SavedAddressActivity;
import com.meebu.model.HomeData;
import com.meebu.model.RewardsData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.MyViewHolder> {
    Context context;
    ArrayList<RewardsData> arrayList;
    Activity activity;

    public RewardsAdapter(Context context, ArrayList<RewardsData> arrayList) {
        this.context = context;
        this.activity = (Activity) context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.reward_layout, viewGroup, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        final RewardsData rewardsData = arrayList.get(i);

        /*Glide.with(context)
                .load(rewardsData.getBackgroundId())
                .into(myViewHolder.row_image);*/
        myViewHolder.scratch_card_main.setImageResource(rewardsData.getBackgroundId());

        myViewHolder.scratch_card_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_scratch);
                // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


                ScratchRelativeLayoutView scratchRelativeLayoutView = dialog.findViewById(R.id.scratch_card);
                scratchRelativeLayoutView.setStrokeWidth(10);
                scratchRelativeLayoutView.setScratchView(R.layout.layout_scratch);

                TextView amount = dialog.findViewById(R.id.amount);

                ImageView image_foreground = scratchRelativeLayoutView.findViewById(R.id.image_foreground);

                amount.setText(rewardsData.getAmount()+" $");

                *//*Glide.with(context)
                        .load(rewardsData.getBackgroundId())
                        .into(image_foreground);*//*
                //image_foreground.setImageResource(rewardsData.getBackgroundId());

                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2; //style id
                dialog.show();
*/
                Intent intent=new Intent(context,DialogActivity.class);
                intent.putExtra("foreground",rewardsData.getBackgroundId())
                        .putExtra("amount",rewardsData.getAmount());
               /* ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(activity, myViewHolder.card_trans, ViewCompat.getTransitionName(myViewHolder.card_trans));*/
                context.startActivity(intent);
                //context.startActivity(new Intent(context,DialogActivity.class));

            }
        });

    }

        //myViewHolder.textView.setText(rewardsData.getTitle());

        /*myViewHolder.scratchRelativeLayoutView.setRevealListener(new ScratchRelativeLayoutView.IRevealListener() {
            @Override
            public void onRevealed(ScratchRelativeLayoutView tv) {
                // on reveal

            }

            @Override
            public void onRevealPercentChangedListener(ScratchRelativeLayoutView siv, float percent) {
                // on percent change
                if(percent==0.7)
                {
                    Toast.makeText(context, "TaDaaaaaa!!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });*/


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView row_image;
        /*TextView textView;
        RelativeLayout main;*/
        //ScratchRelativeLayoutView scratchRelativeLayoutView;
        ImageView scratch_card_main;
        RelativeLayout card_trans;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            scratch_card_main = itemView.findViewById(R.id.scratch_card_main);
            card_trans = itemView.findViewById(R.id.card_trans);
            //row_image = itemView.findViewById(R.id.row_image);
/*
            textView = itemView.findViewById(R.id.title);
            main = itemView.findViewById(R.id.main);*/
           /* scratchRelativeLayoutView = itemView.findViewById(R.id.scratch_card);
            scratchRelativeLayoutView.setStrokeWidth(10);
            scratchRelativeLayoutView.setScratchView(R.layout.layout_scratch);
*/

        }
    }
}
