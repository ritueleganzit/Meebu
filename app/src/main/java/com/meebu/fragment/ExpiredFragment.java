package com.meebu.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meebu.R;
import com.meebu.adapter.ExpiredAdapter;
import com.meebu.databinding.FragmentExpiredBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpiredFragment extends Fragment {


    public ExpiredFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentExpiredBinding fragmentExpiredBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_expired,container,false);
        View view = fragmentExpiredBinding.getRoot();


        fragmentExpiredBinding.recyclerExpiredlhistory.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        fragmentExpiredBinding.recyclerExpiredlhistory.setAdapter(new ExpiredAdapter(getActivity()));

        return view;
    }

}
