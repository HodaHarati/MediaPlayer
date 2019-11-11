package com.example.mediaplayer.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mediaplayer.model.TabState;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {

    ArrayList<String> mTabStateList = new ArrayList<>();


    public PagerAdapter(@NonNull FragmentManager fm, ArrayList<String> tablist) {
        super(fm);
        mTabStateList=tablist;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return MusicListFragment.newInstance(TabState.valueOf(mTabStateList.get(position)));
    }

    @Override
    public int getCount() {
        return mTabStateList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return mTabStateList.get(position);
       /* if (position == 0)
            return TabState.musics.toString();
        if (position == 1)
            return TabState.singers.toString();
        if (position == 2)
            return TabState.albums.toString();

        return null;*/
    }
}
