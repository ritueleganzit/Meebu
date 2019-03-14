package com.meebu.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meebu.R;
import com.meebu.adapter.CardFragmentPagerAdapter;
import com.meebu.adapter.CardPagerAdapter;
import com.meebu.adapter.MyRecycler;
import com.meebu.model.HomeData;
import com.meebu.utils.CardItem;
import com.meebu.utils.ShadowTransformer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView recycler;
    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
NestedScrollView  scrollview;

    ArrayList<HomeData> arrayList=new ArrayList<>();
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = (ViewPager) v.findViewById(R.id.viewPager);

        recycler=v.findViewById(R.id.recycler);
        scrollview=v.findViewById(R.id.scrollview);
        recycler.setLayoutManager(new GridLayoutManager(getActivity(),3));


        HomeData homeData1=new HomeData("SEND PACKAGE",R.mipmap.ic_send_package);
        HomeData homeData2=new HomeData("MY ORDER",R.mipmap.ic_myorder);
        HomeData homeData3=new HomeData("MY PROFILE",R.mipmap.ic_my_profile);
        HomeData homeData4=new HomeData("MEEBU WALLET",R.mipmap.ic_wallet);
        HomeData homeData5=new HomeData("HISTORY",R.mipmap.ic_history);
        HomeData homeData6=new HomeData("SAVE ADDRESS",R.mipmap.ic_home);
        HomeData homeData7=new HomeData("CUSTOMER CARE",R.mipmap.ic_customer_care);
        HomeData homeData8=new HomeData("REWARDS",R.mipmap.ic_orange_reward);
        HomeData homeData9=new HomeData("INVITE A FRIEND",R.mipmap.ic_invite);


scrollview.scrollTo(0,0);
        arrayList.add(homeData1);
        arrayList.add(homeData2);
        arrayList.add(homeData3);
        arrayList.add(homeData4);
        arrayList.add(homeData5);
        arrayList.add(homeData6);
        arrayList.add(homeData7);
        arrayList.add(homeData8);
        arrayList.add(homeData9);

        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_2, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_3, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_4, R.string.text_1));
        mFragmentCardAdapter = new CardFragmentPagerAdapter(getActivity().getSupportFragmentManager(),
                dpToPixels(2, getActivity()));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);
recycler.setAdapter(new MyRecycler(getActivity(),arrayList));
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        mCardShadowTransformer.enableScaling(true);
        mFragmentCardShadowTransformer.enableScaling(true);
        return v;
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

}
