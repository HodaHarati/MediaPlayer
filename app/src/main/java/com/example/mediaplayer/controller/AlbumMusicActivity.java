package com.example.mediaplayer.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mediaplayer.R;
import com.example.mediaplayer.model.TabState;

public class AlbumMusicActivity extends AppCompatActivity {

    public static final String EXTRA_ALBUM_ID = "id";
   // public static final String EXTRA_ARTIST_ID = "id";
    public static final String EXTRA_TAB_NAME = "tabName";

    public static Intent newIntent (Context context, Long id, TabState tag){
        Intent intent = new Intent(context, AlbumMusicActivity.class);
        intent.putExtra(EXTRA_ALBUM_ID, id);
       // intent.putExtra(EXTRA_ARTIST_ID, artistId);
        intent.putExtra(EXTRA_TAB_NAME, tag);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_music);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container_musicOfAlbum);
        Long albumid = getIntent().getLongExtra(EXTRA_ALBUM_ID, 0);
       // Long artistid = getIntent().getLongExtra(EXTRA_ARTIST_ID, 0);
        TabState tag = (TabState) getIntent().getSerializableExtra(EXTRA_TAB_NAME);

        if (fragment == null)
            fragmentManager.beginTransaction()
                            .add(R.id.container_musicOfAlbum, AlbumMusicFragment.newInstance(albumid, tag)).commit();
    }
}
