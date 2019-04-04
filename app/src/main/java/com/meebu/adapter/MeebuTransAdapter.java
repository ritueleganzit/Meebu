package com.meebu.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.meebu.R;
import com.meebu.databinding.TransactionRowBinding;
import com.meebu.model.TransactionData;

import java.util.ArrayList;

/**
 * Created by eleganz on 20/3/19.
 */

public class MeebuTransAdapter extends RecyclerView.Adapter<MeebuTransAdapter.MyViewHolder> {
Context context;
ArrayList<TransactionData>transactionDataArrayList;


    public MeebuTransAdapter(Context context, ArrayList<TransactionData> transactionDataArrayList) {
        this.context = context;
        this.transactionDataArrayList = transactionDataArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        TransactionRowBinding transactionRowBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.transaction_row,viewGroup,false);



        return new MyViewHolder(transactionRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


        TransactionData transactionData=transactionDataArrayList.get(i);
        myViewHolder.transactionRowBinding.transactionDate.setText(transactionData.getDate());




        myViewHolder.transactionRowBinding.perDayTransaction.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        myViewHolder.transactionRowBinding.perDayTransaction.setAdapter(new MeebuPerDayAdapter(context,transactionData.getArrayList()));

    }

    @Override
    public int getItemCount() {
        return transactionDataArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder

    {

        TransactionRowBinding transactionRowBinding;
        public MyViewHolder(@NonNull TransactionRowBinding transactionRowBinding) {
            super(transactionRowBinding.getRoot());
            this.transactionRowBinding=transactionRowBinding  ;
        }
    }


}
