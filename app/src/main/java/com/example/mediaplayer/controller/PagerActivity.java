package com.example.mediaplayer.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class PagerActivity extends AppCompatActivity implements MusicListFragment.Callbacks, BitBox.BitBoxCallbacks{
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private BitBox mBitBox;
    private SeekBar mseekbar;
    private ImageView mImgNext, mImgPreviouse, mImgRepeat, mShufel, mEqulizer, mImgCover, mImgPause;
    private TextView mTxtMusicName;
    private boolean flag = true, shufel;
    private List<Music> musics;

    private BottomSheetBehavior behavior;
    FrameLayout mlayoutDetailMusic;
    private Handler handler = new Handler();



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
                if (flag == true && shufel == false)
                    mBitBox.next(mBitBox.getmMusic());
                else if (flag == true && shufel == true)
                    ;
                else if (flag == false && shufel == false)
                    mBitBox.repeateOne();
                else if (flag == false && shufel == true)
                    mBitBox.repeateOne();
            }
        });

        mImgPreviouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == true)
                    mBitBox.previous();
                else
                    mBitBox.repeateOne();
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
                    mShufel.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_shufel_fade, null));
                    shufel = false;
                }
                else if (shufel == false){
                    mShufel.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_shufel, null));
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
            mTxtMusicName.setText(music.getmNameMusic());
            mImgCover.setImageBitmap(BitmapFactory.decodeFile(music.getmAlbumPath()));
            mImgPause.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_action_pause, null));
        }
    }


    @Override
    public void setUi(Music music) {
        mTxtMusicName.setText(music.getmNameMusic());
        mImgCover.setImageBitmap(BitmapFactory.decodeFile(music.getmAlbumPath()));
    }
}
