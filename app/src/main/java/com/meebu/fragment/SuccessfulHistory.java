package com.meebu.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meebu.R;
import com.meebu.adapter.SuccessfulAdapter;
import com.meebu.databinding.FragmentSuccessfulHistoryBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuccessfulHistory extends Fragment {


    public SuccessfulHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSuccessfulHistoryBinding fragmentSuccessfulHistoryBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_successful_history,container,false);
        View view = fragmentSuccessfulHistoryBinding.getRoot();


        fragmentSuccessfulHistoryBinding.recyclerSuccessfulhistory.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        fragmentSuccessfulHistoryBinding.recyclerSuccessfulhistory.setAdapter(new SuccessfulAdapter(getActivity()));

        return view;
    }

}
