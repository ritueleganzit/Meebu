package com.meebu.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.meebu.R;
import com.meebu.databinding.RowPerDayTransactionBinding;
import com.meebu.model.PerDayTransaction;

import java.util.ArrayList;

/**
 * Created by eleganz on 20/3/19.
 */

public class MeebuPerDayAdapter extends RecyclerView.Adapter<MeebuPerDayAdapter.MyViewHolder> {
Context context;
ArrayList<PerDayTransaction> arrayList;


    public MeebuPerDayAdapter(Context context, ArrayList<PerDayTransaction> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RowPerDayTransactionBinding rowPerDayTransactionBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_per_day_transaction,viewGroup,false);



        return new MyViewHolder(rowPerDayTransactionBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder

    {

        RowPerDayTransactionBinding rowPerDayTransactionBinding;
        public MyViewHolder(@NonNull RowPerDayTransactionBinding rowPerDayTransactionBinding) {
            super(rowPerDayTransactionBinding.getRoot());
            this.rowPerDayTransactionBinding=rowPerDayTransactionBinding  ;
        }
    }


}
