package com.meebu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meebu.HomeActivity;
import com.meebu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermsFragment extends Fragment {


    public TermsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_terms, container, false);
        HomeActivity.textTitle.setText("Terms & Condition");

        return v;
    }

}
