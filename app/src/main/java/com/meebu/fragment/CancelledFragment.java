package com.meebu.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meebu.R;
import com.meebu.adapter.CancelledAdapter;
import com.meebu.databinding.FragmentCancelledBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class CancelledFragment extends Fragment {


    public CancelledFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentCancelledBinding fragmentCancelledBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_cancelled,container,false);
        View view = fragmentCancelledBinding.getRoot();


        fragmentCancelledBinding.recyclerCancelledhistory.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        fragmentCancelledBinding.recyclerCancelledhistory.setAdapter(new CancelledAdapter(getActivity()));

        return view;    }

}
