package com.example.mediaplayer.controller;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mediaplayer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicOfAlbumFragment extends Fragment {

    private Long albumid;

    public static final String ARG_ALBUMID = "albumid";

    public static MusicOfAlbumFragment newInstance(Long albumid) {

        Bundle args = new Bundle();
        args.putLong(ARG_ALBUMID, albumid);
        MusicOfAlbumFragment fragment = new MusicOfAlbumFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public MusicOfAlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        albumid = getArguments().getLong(ARG_ALBUMID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music_of_album, container, false);
        return view;
    }

}
