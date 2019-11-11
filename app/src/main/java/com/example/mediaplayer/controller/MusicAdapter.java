/*
package com.example.mediaplayer.controller;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaplayer.BitBox;
import com.example.mediaplayer.R;
import com.example.mediaplayer.model.Music;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {

    private TextView mTextViewSongTitleBottemsheet;
    private BitBox mBitBox;
    private BottomSheetBehavior behavior;
    private Context mContext;
    private List<Music> musicList;

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }


    public MusicAdapter(Context mContext, List<Music> musicList) {
        this.mContext = mContext;
        this.musicList = musicList;
    }

    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.music_item, parent, false);
        MusicHolder musicHolder = new MusicHolder(view);
        return musicHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHolder holder, int position) {
        holder.bindMusic(musicList.get(position));
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class MusicHolder extends RecyclerView.ViewHolder{
        private TextView mTextviewTitle;
        private TextView mTextviewSinger;
        private ImageView mImageCover;
        private Music music;

        public MusicHolder(@NonNull View itemView) {
            super(itemView);
            mTextviewTitle = itemView.findViewById(R.id.textview_title);
            mTextviewSinger = itemView.findViewById(R.id.textview_singer);
            mImageCover = itemView.findViewById(R.id.img_music_cover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    mBitBox.play(music);
                    mTextViewSongTitleBottemsheet.setText(music.getmNameMusic());
                }
            });
        }

        public void bindMusic(Music music){
            this.music = music;
            mTextviewTitle.setText(music.getmNameMusic());
            mTextviewSinger.setText(music.getmNameSinger());//.substring(0,music.getmNameSinger().lastIndexOf("-")));
            mImageCover.setImageBitmap(BitmapFactory.decodeFile(music.getmAlbumPath()));
        }
    }
}
*/
