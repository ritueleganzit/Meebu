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
import com.meebu.adapter.RunningOrderAdapter;
import com.meebu.databinding.FragmentMyReviewBinding;
import com.meebu.databinding.FragmentRunningOrderBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunningOrderFragment extends Fragment {


    public RunningOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentRunningOrderBinding fragmentRunningOrderBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_running_order,container,false);
        View view = fragmentRunningOrderBinding.getRoot();


        fragmentRunningOrderBinding.recyclerRunningorderlist.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        fragmentRunningOrderBinding.recyclerRunningorderlist.setAdapter(new RunningOrderAdapter(getActivity()));

        return view;
    }

}
