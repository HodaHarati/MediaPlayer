package com.example.mediaplayer.model;

public class Artist {
    private Long _id;
    private String mArtistName;
    private int mMusicCont;

    public Artist(Long _id, String mArtistName, int mMusicCont) {
        this._id = _id;
        this.mArtistName = mArtistName;
        this.mMusicCont = mMusicCont;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getmArtistName() {
        return mArtistName;
    }

    public void setmArtistName(String mArtistName) {
        this.mArtistName = mArtistName;
    }

    public int getmMusicCont() {
        return mMusicCont;
    }

    public void setmMusicCont(int mMusicCont) {
        this.mMusicCont = mMusicCont;
    }
}
