package com.example.mediaplayer.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mediaplayer.BitBox;
import com.example.mediaplayer.R;
import com.example.mediaplayer.model.Music;
import com.example.mediaplayer.model.TabState;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.mediaplayer.BitBox.MY_PERMISSION_REQUEST;


public  class PagerActivity extends AppCompatActivity implements
                                                        MusicListFragment.Callbacks,
                                                        BitBox.BitBoxCallbacks,
                                                        BitBox.BitBoxFlagCallbacks,
                                                        BitBox.BitBoxShufelCallbacks {

    public static final String EXTRA_ALBUM_ID = "id";
    public static final String EXTRA_TAB_NAME = "tabName";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private BitBox mBitBox;
    private SeekBar mseekbar;
    private ImageView mImgNext, mImgPreviouse, mImgRepeat, mShufel, mEqulizer, mImgCover, mImgPause;
    private TextView mTxtMusicName;
    private boolean flag = true, shufel= false;
    private List<Music> musics;

    private BottomSheetBehavior behavior;
    FrameLayout mlayoutDetailMusic;
    private Handler handler = new Handler();



    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PagerActivity.class);
        return intent;
    }

    /*public static Intent newIntent (Context context, Long id, TabState tag){
        Intent intent = new Intent(context, PagerActivity.class);
        intent.putExtra(EXTRA_ALBUM_ID, id);
        intent.putExtra(EXTRA_TAB_NAME, tag);
        return intent;
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);


        mViewPager=findViewById(R.id.viewPager);
        mTabLayout= findViewById(R.id.tablayout);

        mTxtMusicName = findViewById(R.id.album_txt_musicName);
        mEqulizer = findViewById(R.id.album_equlizer);
        mImgCover = findViewById(R.id.album_cover);
        mImgNext = findViewById(R.id.album_image_next);
        mImgPreviouse = findViewById(R.id.album_image_previous);
        mImgPause = findViewById(R.id.album_imageButton_pause);
        mImgRepeat = findViewById(R.id.album_icon_repeat);
        mShufel = findViewById(R.id.album_icon_shufel);
        // mTextViewSongTitleBottemsheet = view.findViewById(R.id.textview_title_bottemsheet);
        mlayoutDetailMusic = findViewById(R.id.album_framelayout_sheet_behavior);
        behavior = BottomSheetBehavior.from(mlayoutDetailMusic);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mseekbar = findViewById(R.id.album_seekbar);
        handler.postDelayed(updateTime, 100);

        mBitBox = BitBox.getInstanse(getApplicationContext());
        mBitBox.setmCallback(this);
        mBitBox.setmFlagCallback(this);
        mBitBox.setmShufelCallback(this);
        initListener();


        ArrayList<String > tablist = new ArrayList<>();
        tablist.add("musics");
        tablist.add("albums");
        tablist.add("singers");

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tablist);
        mViewPager.setAdapter(pagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initListener() {
        mseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if (fromUser)
                    mBitBox.getmMediaplayer().seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mImgPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBitBox.getmMediaplayer().isPlaying()){
                    mBitBox.getmMediaplayer().pause();
                    mImgPause.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_action_play,null));
                }else {
                    mBitBox.getmMediaplayer().start();
                    mImgPause.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_action_pause, null));
                }
            }
        });

        mImgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBitBox.next(mBitBox.getmMusic());
            }
        });

        mImgPreviouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBitBox.previous(mBitBox.getmMusic());
            }
        });

        mImgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == true) {
                    mImgRepeat.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_repeatone, null));
                    flag = false;
                }
                else if(flag == false) {
                    mImgRepeat.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_repeat, null));
                    flag = true;
                }
            }
        });

        mShufel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shufel == true){
                    mShufel.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_shufel, null));
                    shufel = false;
                }
                else if (shufel == false){
                    mShufel.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_shufel_fade, null));
                    shufel = true;
                }
            }
        });
    }


    private Runnable updateTime = new Runnable() {
        @Override
        public void run() {
            int time = mBitBox.getmMediaplayer().getCurrentPosition();
            mseekbar.setMax(mBitBox.getmMediaplayer().getDuration());
            mseekbar.setProgress(time);
            handler.postDelayed(this, 100);
        }
    };



    @Override
    public void clickMusicHolder(TabState mTabstate, Music music) {
        if (mTabstate == TabState.musics) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            mBitBox.play(music);
            setUi(music);
            mImgPause.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_action_pause, null));
        }
    }


    @Override
    public void setUi(Music music) {
        mTxtMusicName.setText(music.getmNameMusic());
        mImgCover.setImageBitmap(BitmapFactory.decodeFile(music.getmAlbumPath()));
    }


    @Override
    public boolean setFlag() {
        return flag;
    }

    @Override
    public boolean setShufel() {
        return shufel;
    }
}
