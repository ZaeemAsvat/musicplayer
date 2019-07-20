package com.zaeemasvat.musicplayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    //media player
    private MediaPlayer player;

    //song list
    private ArrayList<Song> songs;

    //current position
    private int songPosition;

    // notification control variables
    private String songTitle;
    private static final int NOTIFY_ID = 1;

    private final IBinder musicBind = new MusicBinder();

    private boolean shuffle = false;
    private Random rand;
    private HashSet<Integer> songPositionsNotYetPlayed;

    private MusicController musicController;

    @Override
    public void onCreate() {

        //create the service
        super.onCreate();

        //initialize position
        songPosition = 0;

        //create player
        player = new MediaPlayer();

        initMusicPlayer();

        rand = new Random();
    }

    public void initMusicPlayer(){

        //set player properties
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);

    }

    public void setMusicController (MusicController musicController) {
        this.musicController = musicController;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent){
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        // start playback
        mp.start();
        musicController.show(0);

        // set up notification widget for navigating back to the app
        Intent notIntent = new Intent(this, MainActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0,
                notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";
            String channelName = "My Background Service";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

            builder.setContentIntent(pendInt)
                    .setSmallIcon(R.drawable.ic_play)
                    .setTicker(songTitle)
                    .setOngoing(true)
                    .setContentTitle("")
                    .setContentText(songTitle);
            Notification not = builder.build();

            startForeground(NOTIFY_ID, not);

        } else {

            Notification.Builder builder = new Notification.Builder(this);

            builder.setContentIntent(pendInt)
                    .setSmallIcon(R.drawable.ic_play)
                    .setTicker(songTitle)
                    .setOngoing(true)
                    .setContentTitle("")
                    .setContentText(songTitle);
            Notification not = builder.build();

            startForeground(NOTIFY_ID, not);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (player.getCurrentPosition() > 0) {
            mp.reset();
            playNext();
        }
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongPosition(int songPosition) {
        this.songPosition = songPosition;
    }

    public int getSongPosition() {
        return songPosition;
    }

    public void playSong(){

        player.reset();

        //get song
        Song playSong = songs.get(songPosition);
        songTitle = playSong.getTitle();

        //get id
        long currSongId = playSong.getId();

        //set uri
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSongId);

        try{
            player.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }

        //player.prepareAsync();

        try {
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    public int getPosition () {
        return player.getCurrentPosition();
    }

    public int getDuration (){
        return player.getDuration();
    }

    public boolean isPlaying () {
        return player.isPlaying();
    }

    public void pausePlayer(){
        player.pause();
    }

    public void seek(int posn){
        player.seekTo(posn);
    }

    public void start() {
        player.start();
    }

    public void playPrev(){
        songPosition--;
        if (songPosition < 0)
            songPosition = songs.size() - 1;
        playSong();
    }

    //skip to next
    public void playNext() {

        if (shuffle) {
            int songPositionTemp = getNextShuffledSongPosition();
            if (songPositionTemp == -1)
                setShuffle(false);
            else {
                songPosition = songPositionTemp;
                songPositionsNotYetPlayed.remove(songPosition);
            }

        }
        else{
            songPosition++;
            if(songPosition >= songs.size())
                songPosition = 0;
        }
        playSong();
    }

    public void setShuffle (boolean shuffle){
        this.shuffle = shuffle;
        if (shuffle) {
            songPositionsNotYetPlayed = new HashSet<>();
            for (int i = 0; i < songs.size(); i++)
                songPositionsNotYetPlayed.add(i);
        }
    }

    private int getNextShuffledSongPosition() {

        int shuffledPosition = -1;

        if (!songPositionsNotYetPlayed.isEmpty()) {
            int n = rand.nextInt(songPositionsNotYetPlayed.size());
            Iterator<Integer> iterator = songPositionsNotYetPlayed.iterator();
            while (n >= 0) {
                shuffledPosition = iterator.next();
                n--;
            }
        }

        return shuffledPosition;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        player.stop();
        player.release();
    }
}
