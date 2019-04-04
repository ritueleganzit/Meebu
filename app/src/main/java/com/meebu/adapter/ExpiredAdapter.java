package com.meebu.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.meebu.R;
import com.meebu.databinding.RowExpiredBinding;

import java.util.ArrayList;

/**
 * Created by eleganz on 18/3/19.
 */

public class ExpiredAdapter extends RecyclerView.Adapter<ExpiredAdapter.MyViewHolder> {


    Context context;
    ArrayList<String> arrayList=new ArrayList<>();

    public ExpiredAdapter(Context context) {
        this.context = context;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RowExpiredBinding rowExpiredBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_expired,viewGroup,false);



        return new MyViewHolder(rowExpiredBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Glide.with(context).load(R.drawable.expired).into(myViewHolder.rowExpiredBinding.expiredcard);

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        RowExpiredBinding rowExpiredBinding;


        public MyViewHolder(@NonNull RowExpiredBinding rowExpiredBinding) {
            super(rowExpiredBinding.getRoot());
            this.rowExpiredBinding=rowExpiredBinding  ;
        }
    }

}
