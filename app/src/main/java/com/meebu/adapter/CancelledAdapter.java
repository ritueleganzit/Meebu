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
import com.meebu.CancelledDetail;
import com.meebu.R;
import com.meebu.databinding.RowCancelledBinding;

import java.util.ArrayList;

/**
 * Created by eleganz on 18/3/19.
 */

public class CancelledAdapter extends RecyclerView.Adapter<CancelledAdapter.MyViewHolder> {


    Context context;
    ArrayList<String> arrayList=new ArrayList<>();
    Activity activity;

    public CancelledAdapter(Context context) {
        this.context = context;
        this.activity = (Activity) context;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RowCancelledBinding rowCancelledBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_cancelled,viewGroup,false);



        return new MyViewHolder(rowCancelledBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
myViewHolder.rowCancelledBinding.cardviewcancelled.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        context.startActivity(new Intent(context, CancelledDetail.class));
        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
});

        Glide.with(context).load(R.drawable.cancelled).into(myViewHolder.rowCancelledBinding.cancelledcard);

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        RowCancelledBinding rowCancelledBinding;


        public MyViewHolder(@NonNull RowCancelledBinding rowCancelledBinding) {
            super(rowCancelledBinding.getRoot());
            this.rowCancelledBinding=rowCancelledBinding  ;
        }
    }

}
