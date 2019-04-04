package com.meebu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.meebu.fragment.HomeFragment;
import com.meebu.fragment.LanguageFragment;
import com.meebu.fragment.NotificationSettingsFragment;
import com.meebu.fragment.TermsFragment;
import com.meebu.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager banner_slider;
    SessionManager sessionManager;
    ArrayList<Integer> slider_image_list = new ArrayList<>();
    AppBarLayout mAppBarLayout;
    String fullname;
public static TextView textTitle;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        //window.setStatusBarColor(getColor(R.color.black));
        setContentView(R.layout.activity_home);
        sessionManager=new SessionManager(HomeActivity.this);

HashMap<String,String> hashMap=sessionManager.getUserDetails();
fullname=hashMap.get(SessionManager.FULLNAME);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        textTitle=findViewById(R.id.textTitle);

        HomeFragment  homeFragment=new HomeFragment();
        FragmentTransaction  fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,homeFragment);
        fragmentTransaction.commit();
        mAppBarLayout=(AppBarLayout)findViewById(R.id.mAppBarLayout);

        mAppBarLayout.setElevation(0);
        mAppBarLayout.setOutlineProvider(null);

        banner_slider= (ViewPager) findViewById(R.id.banner_slider);

        banner_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                banner_slider.getParent().requestDisallowInterceptTouchEvent(true);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        slider_image_list.add(R.drawable.banner1);
        slider_image_list.add(R.drawable.banner1);
        slider_image_list.add(R.drawable.banner1);


        banner_slider.setAdapter(new SliderPagerAdapter(this, slider_image_list));


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.menu_icon, null);
        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView tvfullname=headerView.findViewById(R.id.fullname);
        tvfullname.setText(""+fullname);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_feedback) {

            Toast.makeText(this, "feedback", Toast.LENGTH_SHORT).show();
            // Handle the camera action
        } else if (id == R.id.nav_language) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            LanguageFragment languageFragment=new LanguageFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack("HomeActivity");
            fragmentTransaction.replace(R.id.container,languageFragment);
            fragmentTransaction.commit();


        }
        else if (id == R.id.nav_notificationsetting) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            NotificationSettingsFragment notificationSettingsFragment=new NotificationSettingsFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack("HomeActivity");
            fragmentTransaction.replace(R.id.container,notificationSettingsFragment);
            fragmentTransaction.commit();

        }
        else if (id == R.id.nav_becomemeebupartner) {

        }
        else if (id == R.id.nav_termsandcondition) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            TermsFragment termsFragment=new TermsFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack("HomeActivity");
            fragmentTransaction.replace(R.id.container,termsFragment);
            fragmentTransaction.commit();


        }else if (id == R.id.nav_logout) {
            sessionManager.logoutUser();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
