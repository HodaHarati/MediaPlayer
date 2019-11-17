package com.example.mediaplayer.model;

import java.util.UUID;

public class Music {

    private UUID mUuid;
    private Long _id;
    private String mNameMusic;
    private String mNameSinger;
    private String mNameAlbum;
    private String mAlbumPath;
    private String mAssetPath;
    private int mMusicPosition;
    private Long mAlbumId;
    private Long mArtistId;

    public UUID getmUuid() {
        return mUuid;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }


    public String getmNameMusic() {
        return mNameMusic;
    }

    public void setmNameMusic(String mNameMusic) {
        this.mNameMusic = mNameMusic;
    }

    public String getmNameSinger() {
        return mNameSinger;
    }

    public void setmNameSinger(String mNameSinger) {
        this.mNameSinger = mNameSinger;
    }

    public String getmNameAlbum() {
        return mNameAlbum;
    }

    public void setmNameAlbum(String mNameAlbum) {
        this.mNameAlbum = mNameAlbum;
    }

    public String getmAssetPath() {
        return mAssetPath;
    }

    public void setmAssetPath(String mAssetPath) {
        this.mAssetPath = mAssetPath;
    }

    public int getmMusicPosition() {
        return mMusicPosition;
    }

    public void setmMusicPosition(int mMusicPosition) {
        this.mMusicPosition = mMusicPosition;
    }

    public String getmAlbumPath() {
        return mAlbumPath;
    }

    public void setmAlbumPath(String mAlbumPath) {
        this.mAlbumPath = mAlbumPath;
    }

    public Long getmAlbumId() {
        return mAlbumId;
    }

    public void setmAlbumId(Long mAlbumId) {
        this.mAlbumId = mAlbumId;
    }

    public Long getmArtistId() {
        return mArtistId;
    }

    public void setmArtistId(Long mArtistId) {
        this.mArtistId = mArtistId;
    }

    public Music(String mNameMusic, String mNameSinger, String mNameAlbum) {
        this.mUuid = UUID.randomUUID();
        this.mNameMusic = mNameMusic;
        this.mNameSinger = mNameSinger;
        this.mNameAlbum = mNameAlbum;
    }

    public Music(String path){
        mAssetPath = path;
        String[] paths = mAssetPath.split("/");
        String fullname = paths[paths.length -1];
        mNameMusic = fullname.substring(0,fullname.lastIndexOf('.'));
    }

    /*public Music(Long _id, String mNameMusic, String ) {
        this._id = _id;
        this.mNameMusic = mNameMusic;
    }*/

    public Music(Long _id, String mNameMusic, String mNameSinger, String mNameAlbum, String mAlbumPath, Long mAlbumId) {
        this._id = _id;
        this.mNameMusic = mNameMusic;
        this.mNameSinger = mNameSinger;
        this.mNameAlbum = mNameAlbum;
        this.mAlbumPath = mAlbumPath;
        this.mAlbumId = mAlbumId;
    }
}
