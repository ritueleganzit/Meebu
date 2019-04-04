package com.meebu.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meebu.R;
import com.meebu.databinding.MyreviewRowBinding;

import java.util.ArrayList;

/**
 * Created by eleganz on 18/3/19.
 */

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.MyViewHolder> {


    Context context;
    ArrayList<String> arrayList=new ArrayList<>();

    public MyReviewAdapter(Context context) {
        this.context = context;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyreviewRowBinding myreviewRowBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.myreview_row,viewGroup,false);



        return new MyViewHolder(myreviewRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        MyreviewRowBinding myreviewRowBinding;


        public MyViewHolder(@NonNull MyreviewRowBinding myreviewRowBinding) {
            super(myreviewRowBinding.getRoot());
            this.myreviewRowBinding=myreviewRowBinding;
        }
    }

}
