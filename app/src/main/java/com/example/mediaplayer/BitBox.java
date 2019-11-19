package com.example.mediaplayer;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.res.ResourcesCompat;

import com.example.mediaplayer.controller.MusicListFragment;
import com.example.mediaplayer.model.Album;
import com.example.mediaplayer.model.Artist;
import com.example.mediaplayer.model.Music;

import java.util.ArrayList;
import java.util.List;

public class BitBox {

    public static final String ASSET_SAMPLE_MUSIC = "music";
    public static final int MY_PERMISSION_REQUEST = 1;

    private static BitBox instance;
    private List<Music> mMusic;
    private Music curentMusic;
    private Context mContext;
    private MediaPlayer mMediaplayer ;
    ContentResolver contentResolver;
    private Music nextMusic;
   // private BitBoxCallbacks mBitboxCallbacks;
    //private AssetManager mAssetManager;


    public static BitBox getInstanse(Context context) {
        if (instance == null) {
            instance = new BitBox(context);

        }
        return instance;
    }
     private BitBox(Context context) {
            mContext = context.getApplicationContext();
            mMusic = loadMusic();
            mMediaplayer = new MediaPlayer();
     }

    /*public void setmBitboxCallbacks(BitBoxCallbacks mBitboxCallbacks) {
        this.mBitboxCallbacks = mBitboxCallbacks;
    }*/

    public MediaPlayer getmMediaplayer() {
        return mMediaplayer;
    }

    public  List<Music> getmMusic() {
        return mMusic;
    }

    /*    public BitBox(Context context)  {
       *//* mContext = context.getApplicationContext();
       mMusic = new ArrayList<>();
        mMediaplayer = new MediaPlayer();*//*
*//*        mAssetManager = mContext.getAssets();
        String[] nameList = mAssetManager.list(ASSET_SAMPLE_MUSIC);
        for (int i = 0; i < nameList.length-1; i++) {
            String path = ASSET_SAMPLE_MUSIC + "/" + nameList[i];
            Music music = new Music(path);
            mMusic.add(music);

        }*//*
    }*/

   public Uri getMusicUri (Music music){
       //long cursor = music.get_id();
       Uri musicUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,music.get_id());
       return musicUri;
   }

    public void play(Music music)  {

        if(mMediaplayer.isPlaying()){
            mMediaplayer.stop();
            mMediaplayer.release();
            mMediaplayer = null;
        }
            mMediaplayer = MediaPlayer.create(mContext,getMusicUri(music));
            mMediaplayer.start();
            curentMusic = music;


      /*  AssetFileDescriptor descriptor = mContext.getAssets().openFd(music.getmAssetPath());
        long start = descriptor.getStartOffset();
        long end = descriptor.getLength();
        mMediaplayer = new MediaPlayer();
        mMediaplayer.setDataSource(descriptor.getFileDescriptor(), start, end);
        mMediaplayer.prepare();
        mMediaplayer.start();*/

   }



    public Music next(){

       int index = mMusic.indexOf(curentMusic);
       nextMusic = mMusic.get((index + 1) % mMusic.size());
       curentMusic = nextMusic;
       play(curentMusic);
       return curentMusic;
    }

    public Music repeateOne(){
       play(curentMusic);
       return curentMusic;
    }

    public Music previous (){
       int index = mMusic.indexOf(curentMusic);
       Music previousMusic = mMusic.get((mMusic.size() + (index - 1))% mMusic.size());
       curentMusic = previousMusic;
       play(curentMusic);
       return curentMusic;
    }


    public List<Music> loadMusic() {
            ArrayList<Music> musicList = new ArrayList<>();
            contentResolver = mContext.getContentResolver();
            Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor songCursor = contentResolver.query(songUri, null, null, null, null);
            if (songCursor != null && songCursor.moveToFirst()) {
                int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int songAlbum = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int albumId = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
                int artistId = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
                do {
                    String currentTitle = songCursor.getString(songTitle);
                    Long currentId = songCursor.getLong(songId);
                    String currentArtist = songCursor.getString(songArtist);
                    String currentAlbum = songCursor.getString(songAlbum);
                    Long currentAlbumId = songCursor.getLong(albumId);
                    Long currentArtistId = songCursor.getLong(artistId);
                    Music music = new Music(currentId, currentTitle, currentArtist, currentAlbum, getAlbumMusic(currentAlbum), currentAlbumId, currentArtistId);
                    musicList.add(music);
                } while (songCursor.moveToNext());
                songCursor.close();
            }

        return musicList;
    }



    public String getAlbumMusic(String songAlbum) {
        String path="";
        contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.AlbumColumns.ALBUM, MediaStore.Audio.AlbumColumns.ALBUM_ART},
                MediaStore.Audio.AlbumColumns.ALBUM + "= ?",
                new String[]{songAlbum},
                null);

        //int a = cursor.getCount();
        if (cursor.moveToFirst()) {
           path= cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));

        }
        return path;
    }

    public List<Music> loadMusicByAlbum(Long albumId){
        List<Music> musicList = new ArrayList<>();
        for (Music music: loadMusic()) {
            if (music.getmAlbumId().equals(albumId)){
                musicList.add(music);
            }
        }
        return musicList;
    }


    public List<Album> loadAlbum (){
        ArrayList<Album> albumList = new ArrayList<>();
        contentResolver = mContext.getContentResolver();
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor albumCursor = contentResolver.query(albumUri, null, null, null, null);
        if (albumCursor != null && albumCursor.moveToFirst()) {

            int albumId = albumCursor.getColumnIndex(MediaStore.Audio.Albums._ID);
            int artistId = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST_ID);
            int albumArtist = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
            int titleAlbum = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
            int albumArt = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            int numberOfMusic = albumCursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);
            do {
                long currentAlbumId = albumCursor.getLong(albumId);
                long currentArtistId = albumCursor.getLong(artistId);
                String currentAlbumArtist = albumCursor.getString(albumArtist);
                String currentTitleAlbum = albumCursor.getString(titleAlbum);
                String currentAlbumArt = albumCursor.getString(albumArt);
                int currentNumberOfMusic = albumCursor.getInt(numberOfMusic);
                Album album = new Album(currentAlbumId, currentArtistId, currentAlbumArtist, currentTitleAlbum, currentAlbumArt, currentNumberOfMusic);
                albumList.add(album);
            } while (albumCursor.moveToNext());
            albumCursor.close();
        }

        return albumList;
     }

     public List<Artist> loadArtist (){
       ArrayList<Artist> artistList = new ArrayList<>();
       contentResolver = mContext.getContentResolver();
       Uri artistUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
       Cursor artistCurcer = contentResolver.query(artistUri, null, null, null, null);
       if (artistCurcer != null && artistCurcer.moveToFirst()){
           int id = artistCurcer.getColumnIndex(MediaStore.Audio.Artists._ID);
           int artistName = artistCurcer.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
           int artistNumberOfSong = artistCurcer.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);
           do {
               Long currentId = artistCurcer.getLong(id);
               String currentArtistName = artistCurcer.getString(artistName);
               int currentNumberOfSong = artistCurcer.getInt(artistNumberOfSong);

               Artist artist = new Artist(currentId, currentArtistName,currentNumberOfSong);
               artistList.add(artist);
           } while (artistCurcer.moveToNext());
           artistCurcer.close();
       }
       return artistList;
     }


    public List<Music> loadMusicByArtist(Long artistId){
        List<Music> musicList = new ArrayList<>();
        for (Music music: loadMusic()) {
            if (music.getmArtistId().equals(artistId)){
                musicList.add(music);
            }
        }
        return musicList;
    }


    public List<Music> getMusicOfAlbum(long albumId) {
        List<Music> musicListAlbum = new ArrayList<>();
        for (int i = 0; i < mMusic.size(); i++) {
            if (mMusic.get(i).get_id() == albumId)
                musicListAlbum.add(mMusic.get(i));
        }
        return musicListAlbum;
    }

    /*public interface BitBoxCallbacks{
        boolean setFlag();
    }*/
}
