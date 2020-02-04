package com.example.mediaplayer.controller;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mediaplayer.BitBox;
import com.example.mediaplayer.R;
import com.example.mediaplayer.model.Album;
import com.example.mediaplayer.model.Artist;
import com.example.mediaplayer.model.Music;
import com.example.mediaplayer.model.TabState;

import java.util.List;

import static com.example.mediaplayer.BitBox.MY_PERMISSION_REQUEST;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicListFragment extends Fragment {

    private static final String TAG = "MusicListFragment";

    public static final String ARG_TABSTATE = "tabstate";
    public static final int REQUEST_CODE_MUSIC_FRAGMENT = 0;
    public static final String TAG_MUSIC_DETAIL = "musicDetail";
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE  = 1;
    public static final String ARG_ID = "id";
    public static final int REQUEST_CODE_AlbumFargment = 0;
    private RecyclerView mRecyclerView;
    private MultiAdapter mAdapter;
    private BitBox mBitBox;
    private MusicCallBack mActivity;

    /*private SeekBar mseekbar;
    private ImageView mImgNext, mImgPreviouse, mImgRepeat, mShufel, mEqulizer, mImgCover;
    private ImageButton mImgPause;*/
    private TextView mTxtMusicName;
    // private TextView mTxtArtistName;



    // private TextView mTextViewSongTitleBottemsheet;
   // private BottomSheetBehavior behavior;
    FrameLayout mlayoutDetailMusic;

    private Music music;
    private Handler handler = new Handler();
    private List mMediaPlayerList;
    private List<Music> musics;
    private List<Album> mAlbums;

    boolean flag, shufel;
    private TabState mTabState;
    private CallbacksMusicList mCallback;

    public static MusicListFragment newInstance(TabState state) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TABSTATE, state);
        MusicListFragment fragment = new MusicListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public MusicListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        mTabState = (TabState) getArguments().getSerializable(ARG_TABSTATE);
/*
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }*/
        AndroidRuntimePermission();
        mBitBox = BitBox.getInstanse(getContext());
        mActivity = (MusicCallBack) getActivity();

    }
/*
    public void showDialog(final String msg, final Context context, final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                MY_PERMISSION_REQUEST);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }*/

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        initView(view);
        //initListener();
        setAdapter();


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof CallbacksMusicList)
            mCallback = (CallbacksMusicList) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

       private void setAdapter() {

        //if (checkPermissionREAD_EXTERNAL_STORAGE(getActivity())) {
            if (mTabState == TabState.musics){
                mMediaPlayerList = mBitBox.getmMusic();
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAdapter = new MultiAdapter(mMediaPlayerList);
                mRecyclerView.setAdapter(mAdapter);
            }
            else if (mTabState == TabState.albums){
                mMediaPlayerList = mBitBox.loadAlbum();
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                mAdapter = new MultiAdapter(mMediaPlayerList);
                // mAdapter.setMusicList(mMediaPlayerList);
                mRecyclerView.setAdapter(mAdapter);
            }
            else if (mTabState == TabState.singers){
                mMediaPlayerList = mBitBox.loadArtist();
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAdapter = new MultiAdapter(mMediaPlayerList);
                //mAdapter.setMusicList(mMediaPlayerList);
                mRecyclerView.setAdapter(mAdapter);
            }
        //}
    }


    private void initView(View view) {
        mTxtMusicName = view.findViewById(R.id.album_txt_musicName);
        mRecyclerView = view.findViewById(R.id.album_recycler);
    }

    private class MediaHolder extends RecyclerView.ViewHolder{

        private TextView mTextviewTitle;
        private TextView mTextview;
        private ImageView mImageCover;
        private Music music;


        public MediaHolder(@NonNull View itemView) {
            super(itemView);
            mTextviewTitle = itemView.findViewById(R.id.textview_title);
            mTextview = itemView.findViewById(R.id.textview_singer);
            mImageCover = itemView.findViewById(R.id.img_music_cover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    mCallback.clickMusicHolder(mTabState,music);
                    mActivity.UiHandler(music);

                    /*if (mTabState == TabState.musics) {
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        mBitBox.play(music);
                        mTxtMusicName.setText(music.getmNameMusic());
                       // mImgCover.setImageBitmap(BitmapFactory.decodeFile(music.getmAlbumPath()));
                    }*/
                }
            });
        }

        public void bindMusic(Music music){
            this.music = music;
            mTextviewTitle.setText(music.getmNameMusic());
            mTextview.setText(music.getmNameSinger());//.substring(0,music.getmNameSinger().lastIndexOf("-")));
            mImageCover.setImageBitmap(BitmapFactory.decodeFile(music.getmAlbumPath()));
        }
    }

    private class AlbumHolder extends RecyclerView.ViewHolder{

        private TextView mTextAlbumsinger;
        private TextView mTextAlmumTitle;
        private ImageView mImageAlbum;
        private Album album;

        public AlbumHolder(@NonNull View itemView) {
            super(itemView);
            mTextAlmumTitle = itemView.findViewById(R.id.txt_album_name);
            mTextAlbumsinger = itemView.findViewById(R.id.txt_singerOfAlbum);
            mImageAlbum = itemView.findViewById(R.id.img_album);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mTabState == TabState.albums){
                        /*Intent intent = AlbumMusicActivity.newIntent(getActivity(),album.getAlbumId(), mTabState);
                        startActivityForResult(intent, REQUEST_CODE_AlbumFargment);*/

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.pager_container, AlbumMusicFragment.newInstance(album.getAlbumId(), mTabState))
                        .addToBackStack(AlbumMusicFragment.TAG)
                        .commit();

                    }
                }
            });
        }
        public void bindAlbum (Album album){
            this.album = album;
            mTextAlmumTitle.setText(album.getAlbumTitle());
            mTextAlbumsinger.setText(album.getAlbumArtist());
            mImageAlbum.setImageBitmap(BitmapFactory.decodeFile(album.getAlbumArt()));
        }
    }

    private class ArtistHolder extends RecyclerView.ViewHolder{

        private TextView mTextSingerName;
        private ImageView mImgSinger;
        private Artist artist;
        private Album album;
        public ArtistHolder(@NonNull View itemView) {
            super(itemView);
            mTextSingerName = itemView.findViewById(R.id.textview_title_singer);
            mImgSinger = itemView.findViewById(R.id.img_singer_cover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mTabState == TabState.singers){
                        /*Intent intent = AlbumMusicActivity.newIntent(getActivity(), artist.get_id(), mTabState);
                        startActivity(intent);*/
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.pager_container, AlbumMusicFragment.newInstance(artist.get_id(), mTabState))
                                .addToBackStack(AlbumMusicFragment.TAG)
                                .commit();
                    }
                }
            });
        }
        public void bindSinger (Artist artist){
            this.artist = artist;
            mTextSingerName.setText(artist.getmArtistName());
            mImgSinger.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.singer,null));
        }
    }

    private class MultiAdapter extends RecyclerView.Adapter {

        private int Type_Music;
        private int Type_Albume;
        private List musicList;

        public void setMusicList(List musicList) {
            this.musicList = musicList;
        }

        public MultiAdapter(List musicList) {
            this.musicList = musicList;
        }

        public int getItemViewType (int position){
            if (musicList.get(position) instanceof Music ){
                return 0;
            }
            else if (musicList.get(position) instanceof Album){
                return 1;
            }
            else if (musicList.get(position) instanceof Artist){
                return 2;
            }
            return -1;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            RecyclerView.ViewHolder viewHolder;
            switch (viewType) {
                case 0 :
                    View view = inflater.inflate(R.layout.music_item, parent, false);
                    viewHolder = new MediaHolder(view);
                    break;
                case 1 :
                    View view1 = inflater.inflate(R.layout.album_item, parent, false);
                    viewHolder = new AlbumHolder(view1);
                    break;
                case 2:
                    View view2 = inflater.inflate(R.layout.artist_item, parent, false);
                    viewHolder = new ArtistHolder(view2);
                    break;
                default :
                    viewHolder = null;
                    break;
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (mTabState == TabState.musics && holder.getItemViewType()== 0)
                ((MediaHolder) holder).bindMusic((Music) musicList.get(position));
            else if (mTabState == TabState.albums &&  holder.getItemViewType() == 1){
                ((AlbumHolder) holder).bindAlbum((Album) musicList.get(position));
            }else if (mTabState == TabState.singers && holder.getItemViewType() == 2){
                ((ArtistHolder) holder).bindSinger((Artist) musicList.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }
    }



    public interface CallbacksMusicList {
        void clickMusicHolder(TabState mTabstate, Music music);
    }



    public void AndroidRuntimePermission() {

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    AlertDialog.Builder alert_builder = new AlertDialog.Builder(getContext());
                    alert_builder.setMessage("External Storage Permission is Required.");
                    alert_builder.setTitle("Please Grant Permission.");
                    alert_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            ActivityCompat.requestPermissions(
                                    getActivity(),
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    0
                            );
                        }
                    });

                    alert_builder.setNeutralButton("Cancel", null);
                    AlertDialog dialog = alert_builder.create();
                    dialog.show();

                } else {

                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            0);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(getActivity(), "permission Denied!", Toast.LENGTH_SHORT).show();
                    AndroidRuntimePermission();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }





  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(getActivity(), "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }
    public boolean checkPermissionREAD_EXTERNAL_STORAGE(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSION_REQUEST);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }
*/


}