package com.meebu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meebu.CustomerCareActivity;
import com.meebu.FullscreenActivity;
import com.meebu.HistoryActivity;
import com.meebu.InviteFriendsActivity;
import com.meebu.MeebuWalletActivity;
import com.meebu.MyProfileActivity;
import com.meebu.OrderStatusActivity;
import com.meebu.R;
import com.meebu.RewardsActivity;
import com.meebu.SavedAddressActivity;
import com.meebu.model.HomeData;

import java.util.ArrayList;

/**
 * Created by eleganz on 1/3/19.
 */

public class MyRecycler extends RecyclerView.Adapter<MyRecycler.MyViewHolder> {
    Context context;
    ArrayList<HomeData> arrayList;
    Activity activity;

    public MyRecycler(Context context, ArrayList<HomeData> arrayList) {
        this.context = context;
        this.activity = (Activity) context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.homegrid, viewGroup, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        HomeData homeData = arrayList.get(i);
        myViewHolder.imageView.setImageResource(homeData.getImage());
        myViewHolder.textView.setText(homeData.getTitle());

        myViewHolder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    context.startActivity(new Intent(context, FullscreenActivity.class));
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                if (i == 1) {
                    context.startActivity(new Intent(context, OrderStatusActivity.class));
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                if (i == 2) {
                    context.startActivity(new Intent(context, MyProfileActivity.class));
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                if (i == 5) {
                    context.startActivity(new Intent(context, SavedAddressActivity.class));
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                if (i==4)
                {
                    context.startActivity(new Intent(context, HistoryActivity.class));
                    activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }
                if(i==3)
                {
                    context.startActivity(new Intent(context, MeebuWalletActivity.class));
                    activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }
                if (i == 6) {
                    context.startActivity(new Intent(context, CustomerCareActivity.class));
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }if (i == 7) {
                    context.startActivity(new Intent(context, RewardsActivity.class));
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                if (i == 8) {
                    context.startActivity(new Intent(context, InviteFriendsActivity.class));
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder


    {
        ImageView imageView;
        TextView textView;
        RelativeLayout main;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);

            textView = itemView.findViewById(R.id.title);
            main = itemView.findViewById(R.id.main);

        }
    }
}
