package com.meebu.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meebu.R;
import com.meebu.adapter.PendingOrderAdapter;
import com.meebu.adapter.RunningOrderAdapter;
import com.meebu.databinding.FragmentPendingOrdersBinding;
import com.meebu.databinding.FragmentRunningOrderBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingOrdersFragment extends Fragment {


    public PendingOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentPendingOrdersBinding fragmentPendingOrdersBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_pending_orders,container,false);
        View view = fragmentPendingOrdersBinding.getRoot();


        fragmentPendingOrdersBinding.recyclerPendingorderlist.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        fragmentPendingOrdersBinding.recyclerPendingorderlist.setAdapter(new PendingOrderAdapter(getActivity()));

        return view;
    }

}
