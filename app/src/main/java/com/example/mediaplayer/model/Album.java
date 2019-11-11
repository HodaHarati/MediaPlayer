package com.example.mediaplayer.model;

public class Album {
    Long albumId;
    Long artistId;
    String albumArtist;
    String albumTitle;
    String albumArt;
    int numberOfMusic;

    public Album(Long albumId, Long artistId, String albumArtist, String albumTitle, String albumArt, int numberOfMusic) {
        this.albumId = albumId;
        this.artistId = artistId;
        this.albumArtist = albumArtist;
        this.albumTitle = albumTitle;
        this.albumArt = albumArt;
        this.numberOfMusic = numberOfMusic;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public int getNumberOfMusic() {
        return numberOfMusic;
    }

    public void setNumberOfMusic(int numberOfMusic) {
        this.numberOfMusic = numberOfMusic;
    }
}
