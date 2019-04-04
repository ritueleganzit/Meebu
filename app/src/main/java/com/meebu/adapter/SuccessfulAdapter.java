package com.meebu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.meebu.R;
import com.meebu.SuccessDetailActivity;
import com.meebu.databinding.RowSuccessfulBinding;

import java.util.ArrayList;

/**
 * Created by eleganz on 18/3/19.
 */

public class SuccessfulAdapter extends RecyclerView.Adapter<SuccessfulAdapter.MyViewHolder> {

    Activity activity;
    Context context;
    ArrayList<String> arrayList=new ArrayList<>();

    public SuccessfulAdapter(Context context) {
        this.context = context;
        this.activity = (Activity) context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RowSuccessfulBinding rowSuccessfulBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_successful,viewGroup,false);



        return new MyViewHolder(rowSuccessfulBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.rowSuccessfulBinding.cardviewsuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SuccessDetailActivity.class));
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        Glide.with(context).load(R.drawable.successful).into(myViewHolder.rowSuccessfulBinding.successfulcard);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        RowSuccessfulBinding rowSuccessfulBinding;


        public MyViewHolder(@NonNull RowSuccessfulBinding rowSuccessfulBinding) {
            super(rowSuccessfulBinding.getRoot());
            this.rowSuccessfulBinding=rowSuccessfulBinding  ;
        }
    }

}
