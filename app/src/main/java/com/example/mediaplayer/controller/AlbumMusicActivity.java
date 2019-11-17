package com.example.mediaplayer.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mediaplayer.R;

public class AlbumMusicActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "id";

    public static Intent newIntent (Context context, Long id){
        Intent intent = new Intent(context, AlbumMusicActivity.class);
        intent.putExtra(EXTRA_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_music);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container_musicOfAlbum);
        Long albumid = getIntent().getLongExtra(EXTRA_ID, 0);

        if (fragment == null)
            fragmentManager.beginTransaction()
                            .add(R.id.container_musicOfAlbum, AlbumMusicFragment.newInstance(albumid)).commit();
    }
}
