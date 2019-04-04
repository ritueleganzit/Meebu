package com.meebu.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.meebu.R;
import com.meebu.databinding.RowPendingOrderBinding;
import com.meebu.databinding.RowRunningOrderBinding;

import java.util.ArrayList;

/**
 * Created by eleganz on 18/3/19.
 */

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.MyViewHolder> {


    Context context;
    ArrayList<String> arrayList=new ArrayList<>();

    public PendingOrderAdapter(Context context) {
        this.context = context;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RowPendingOrderBinding rowPendingOrderBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.row_pending_order,viewGroup,false);



        return new MyViewHolder(rowPendingOrderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        RowPendingOrderBinding rowPendingOrderBinding;


        public MyViewHolder(@NonNull RowPendingOrderBinding rowPendingOrderBinding) {
            super(rowPendingOrderBinding.getRoot());
            this.rowPendingOrderBinding=rowPendingOrderBinding  ;
        }
    }

}
