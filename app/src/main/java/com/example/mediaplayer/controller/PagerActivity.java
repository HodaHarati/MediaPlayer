package com.example.mediaplayer.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mediaplayer.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class PagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PagerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        mViewPager=findViewById(R.id.viewPager);
        mTabLayout= findViewById(R.id.tablayout);


        ArrayList<String > tablist = new ArrayList<>();
        tablist.add("musics");
        tablist.add("albums");
        tablist.add("singers");

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tablist);
        mViewPager.setAdapter(pagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }


}
