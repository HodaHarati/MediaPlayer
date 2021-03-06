package com.example.mediaplayer.controller;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mediaplayer.BitBox;
import com.example.mediaplayer.R;
import com.example.mediaplayer.model.Music;
import com.example.mediaplayer.model.TabState;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumMusicFragment extends Fragment {//implements BitBox.BitBoxCallbacks{

    public static final String TAG = "AlbumMusicFragment";

    public static final String ARG_ARTISTID = "artistid";
    public static final String ARG_TABSTATE = "tab";
    private Long id;
    private TabState tabState;

    private RecyclerView mAlbumRecycler;
    private AlbumMusicAdapter mAdapter;
    private List<Music> mlistMusic;
    private BitBox mBitBox;
    private MusicCallBack mActivity;

    public static final String ARG_ID = "id";

    public static AlbumMusicFragment newInstance(Long id, TabState tabState) {

        Bundle args = new Bundle();
        args.putLong(ARG_ID, id);
        args.putSerializable(ARG_TABSTATE, tabState);
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
        id = getArguments().getLong(ARG_ID);
        tabState = (TabState) getArguments().getSerializable(ARG_TABSTATE);
        mActivity = (MusicCallBack) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album_music, container, false);

        mAlbumRecycler = view.findViewById(R.id.album_recycler);

        mBitBox = BitBox.getInstanse(getActivity());

       // initListener();
        setAdapter();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        /*callbacksAlbumMusic = ((CallbacksAlbumMusic) context);*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        callbacksAlbumMusic = null;
    }

    private void setAdapter() {
        if (tabState == TabState.albums) {
            mlistMusic = BitBox.getInstanse(getActivity()).loadMusicByAlbum(id);  // id = albumId
        } else if(tabState == TabState.singers){
            mlistMusic = BitBox.getInstanse(getActivity()).loadMusicByArtist(id); // id = artistId
        }

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
                    /*mBitBox.play(music);*/
                    mActivity.UiHandler(music);
                    //callbacksAlbumMusic.clickMusicHolderAlbum(music);
                    /*behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    mBitBox.play(music);
                    mTxtMusicName.setText(music.getmNameMusic());
                    mImgCover.setImageBitmap(BitmapFactory.decodeFile(music.getmAlbumPath()));*/
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


    public interface CallbacksAlbumMusic {
        void clickMusicHolderAlbum( Music music);
    }



    /*@Override
    public void setUi(Music music) {
        mTxtMusicName.setText(music.getmNameMusic());
        mImgCover.setImageBitmap(BitmapFactory.decodeFile(music.getmAlbumPath()));
    }*/

}
