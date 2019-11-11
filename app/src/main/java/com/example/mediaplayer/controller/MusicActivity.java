package com.example.mediaplayer.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mediaplayer.R;
import com.example.mediaplayer.model.TabState;

public abstract class MusicActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_STATE = "state";

    public abstract Fragment createFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container_fragment);

        if (fragment == null)
            fragmentManager.beginTransaction()
                    .add(R.id.container_fragment,createFragment())
                    .commit();
    }
}
