package com.meebu.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meebu.R;
import com.meebu.adapter.MyReviewAdapter;
import com.meebu.databinding.FragmentMyReviewBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyReviewFragment extends Fragment {


    public MyReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentMyReviewBinding myReviewBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_my_review,container,false);
        View view = myReviewBinding.getRoot();


        myReviewBinding.recyclerReviewlist.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        myReviewBinding.recyclerReviewlist.setAdapter(new MyReviewAdapter(getActivity()));





        return view;
    }

}
