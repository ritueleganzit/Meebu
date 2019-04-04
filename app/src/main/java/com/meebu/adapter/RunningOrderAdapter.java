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

import com.meebu.R;
import com.meebu.RunningOrderActivity;
import com.meebu.databinding.MyreviewRowBinding;
import com.meebu.databinding.RowRunningOrderBinding;

import java.util.ArrayList;

/**
 * Created by eleganz on 18/3/19.
 */

public class RunningOrderAdapter extends RecyclerView.Adapter<RunningOrderAdapter.MyViewHolder> {


    Context context;
    Activity activity;
    ArrayList<String> arrayList=new ArrayList<>();

    public RunningOrderAdapter(Context context) {
        this.context = context;
        this.activity = (Activity) context;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RowRunningOrderBinding rowRunningOrderBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.row_running_order,viewGroup,false);

        rowRunningOrderBinding.runningOrderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, RunningOrderActivity.class));
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        return new MyViewHolder(rowRunningOrderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        RowRunningOrderBinding rowRunningOrderBinding;


        public MyViewHolder(@NonNull RowRunningOrderBinding rowRunningOrderBinding) {
            super(rowRunningOrderBinding.getRoot());
            this.rowRunningOrderBinding=rowRunningOrderBinding  ;
        }
    }

}
