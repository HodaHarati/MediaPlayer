package com.example.mediaplayer.controller;


import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mediaplayer.BitBox;
import com.example.mediaplayer.R;
import com.example.mediaplayer.model.Music;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumMusicFragment extends Fragment {

    private Long albumid;

    private RecyclerView mAlbumRecycler;
    private AlbumMusicAdapter mAdapter;
    private List<Music> mlistMusic;



    public static final String ARG_ALBUMID = "albumid";

    public static AlbumMusicFragment newInstance(Long albumid) {

        Bundle args = new Bundle();
        args.putLong(ARG_ALBUMID, albumid);
        AlbumMusicFragment fragment = new AlbumMusicFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public AlbumMusicFragment() {
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
        View view = inflater.inflate(R.layout.fragment_album_music, container, false);

        mAlbumRecycler = view.findViewById(R.id.album_recycler);

        setAdapter();
        return view;
    }

    private void setAdapter() {
        mlistMusic = BitBox.getInstanse(getActivity()).loadMusicByAlbum(albumid);
        mAlbumRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new AlbumMusicAdapter(mlistMusic);
        mAlbumRecycler.setAdapter(mAdapter);
    }

    private class AlbumMusicHolder extends RecyclerView.ViewHolder{

        private TextView mTitleAlbumMusic;
        private TextView mSingerAlbumMusic;
        private ImageView mImageCoverAlbumMusic;
        private Music music;

        public AlbumMusicHolder(@NonNull View itemView) {
            super(itemView);

            mTitleAlbumMusic = itemView.findViewById(R.id.album_textview_title);
            mSingerAlbumMusic = itemView.findViewById(R.id.album_textview_singer);
            mImageCoverAlbumMusic = itemView.findViewById(R.id.album_img_music_cover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        public void bind(Music music){
            this.music = music;
            mTitleAlbumMusic.setText(music.getmNameMusic());
            mSingerAlbumMusic.setText(music.getmNameSinger());//.substring(0,music.getmNameSinger().lastIndexOf("-")));
            mImageCoverAlbumMusic.setImageBitmap(BitmapFactory.decodeFile(music.getmAlbumPath()));
        }
    }

    private class AlbumMusicAdapter extends RecyclerView.Adapter<AlbumMusicHolder>{

        private List<Music> musicList;

        public AlbumMusicAdapter(List<Music> musicList) {
            this.musicList = musicList;
        }

        @NonNull
        @Override
        public AlbumMusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.album_music_item, parent, false);
            AlbumMusicHolder albumMusicHolder = new AlbumMusicHolder(view);
            return albumMusicHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumMusicHolder holder, int position) {

            holder.bind(musicList.get(position));
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }
    }

}
