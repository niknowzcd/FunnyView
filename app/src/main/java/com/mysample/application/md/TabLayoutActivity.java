package com.mysample.application.md;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mysample.application.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dly on 2018/1/11.
 */

public class TabLayoutActivity extends FragmentActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(TabLayoutFragment.newInstance("UFC"));
        fragments.add(TabLayoutFragment.newInstance("武林风"));
        fragments.add(TabLayoutFragment.newInstance("昆仑决"));
        fragments.add(TabLayoutFragment.newInstance("荣耀"));
        fragments.add(TabLayoutFragment.newInstance("勇士的崛起"));
        fragments.add(TabLayoutFragment.newInstance("K"));

        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), fragments));

        tabs.add("UFC");
        tabs.add("武林风");
        tabs.add("昆仑决");
        tabs.add("荣耀");
        tabs.add("勇士的崛起");
        tabs.add("K-1");

        tabLayout.setupWithViewPager(viewPager);

    }

    private List<String> tabs = new ArrayList<>();

    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }
}
