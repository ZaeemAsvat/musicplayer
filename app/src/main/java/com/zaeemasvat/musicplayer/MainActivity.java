package com.zaeemasvat.musicplayer;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.widget.MediaController.MediaPlayerControl;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MediaPlayerControl {

    private ArrayList<Song> songList;
    private ListView songView;

    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound = false;

    private MusicController musicController;

    private boolean paused = false, playbackPaused = false;

    // declare database table interfaces
    DbHelper db;
    SongDbHelper songDbHelper;
    UserSongInteractionDbHelper userSongInteractionDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

                return;
            }
        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // initialize databases
        db = new DbHelper(MainActivity.this);
        songDbHelper = new SongDbHelper(db.getWritableDatabase());
        userSongInteractionDbHelper = new UserSongInteractionDbHelper(db.getWritableDatabase());
      //  songDbHelper.deleteSong(null);

        // display songs from user's device
        
        songView = (ListView) findViewById(R.id.song_list);
        songList = new ArrayList<>();

        fillSongList();

        Collections.sort(songList, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        SongAdapter songAdt = new SongAdapter(this, songList);
        songView.setAdapter(songAdt);

        setupMusicController();




      //  FeatureExtractor featureExtxractor = new FeatureExtractor();
      //  featureExtractor.getSpectralCentroid("/home/zaeemasvat_/AndroidStudioProjects/musicplayer/app/src/main/java/com/zaeema");
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            musicSrv.setMusicController(musicController);
            //pass list
            musicSrv.setSongs(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    public void songPicked(View view) {

        recordInteraction();

        musicSrv.setSongPosition (Integer.parseInt(view.getTag().toString()));
        musicSrv.playSong();
        if (playbackPaused) {
            setupMusicController();
            playbackPaused = false;
        }
        musicController.show(0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setupMusicController();
        paused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (paused) {
            setupMusicController();
            paused = false;
        }
    }

    @Override
    protected void onStop() {
        musicController.hide();
        super.onStop();
    }


        // ------------------------------ DRAWER FUNCTIONALITY --------------------------------------------

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_shuffle:
                musicSrv.setShuffle(true);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // ---------------------------------------------------------------------------------------------------

    public void fillSongList() {

        //retrieve song info
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {

            //get column indices
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);

            do {

                // get references to this song's data
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);

                // query the song in the database
                ArrayList<String> songWhereQueryArgs = new ArrayList<>(3);
                songWhereQueryArgs.add(DbContract.Song.COLUMN_NAME_ID + " = " + thisId);

                Song thisSong = songDbHelper.selectSong(songWhereQueryArgs);

                if (thisSong == null) {
                    // song doesn't exist in the Song database table, so we add it
                    thisSong = new Song(thisId, thisTitle, thisArtist);
                    songDbHelper.insertSong(thisSong);
                }

                // add song to app interface
                songList.add(thisSong);

            } while (musicCursor.moveToNext());
        }

        if (musicCursor != null)
            musicCursor.close();

        // print song db table (testing)
        ArrayList<Song> songs = songDbHelper.selectSongs(null);
        Log.d("", "Number of songs: " + songs.size());
        for (Song song : songs)
            Log.d("", song.getId() + " " + song.getTitle() + " " + song.getArtist());
    }


    // ------------------------------ MEDIA CONTROLLER ---------------------------------------


    @Override
    public void start() {
        musicSrv.start();
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public void pause() {
        musicSrv.pausePlayer();
        playbackPaused = true;
    }

    @Override
    public boolean isPlaying() {
        if (musicSrv != null && musicBound)
            return musicSrv.isPlaying();
        else return false;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public void seekTo(int pos) {
        musicSrv.seek(pos);
    }

    @Override
    public int getDuration() {
        return musicSrv.getDuration();
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (musicSrv != null && musicBound)
            return musicSrv.getPosition();
        else return 0;
    }

    // ---------------------------------------------------------------------------------------

    private void setupMusicController() {
        musicController = new MusicController(this);
        musicController.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });

        musicController.setMediaPlayer(this);
        musicController.setAnchorView(findViewById(R.id.song_list));
        musicController.setEnabled(true);
    }

    private void playNext() {

        recordInteraction();

        musicSrv.playNext();
        if (playbackPaused) {
            setupMusicController();
            playbackPaused = false;
        }
        musicController.show(0);
    }

    private void playPrev() {
        musicSrv.playPrev();
        if (playbackPaused) {
            setupMusicController();
            playbackPaused = false;
        }
        musicController.show(0);
    }

    /*** record user's interaction with skipped song ***/
    private void recordInteraction () {

        // get reference to current (skipped song)
        Song currSong = musicSrv.getSongs().get(musicSrv.getSongPosition());

        Log.e("", "duration: " + getCurrentPosition()/1000 + "\n");

        int lengthPlayed = getCurrentPosition()/1000;

        // create object that holds user-song interaction data
        UserSongInteraction thisInteraction = new UserSongInteraction();
        thisInteraction.setSongId(currSong.getId());
        thisInteraction.setInteractionVerdict(lengthPlayed > 25);

        // store interaction in UserSongInteraction database table
        userSongInteractionDbHelper.insertUserSongInteraction(thisInteraction);

        // print user-song interaction db table (testing)
        ArrayList<UserSongInteraction> interactions = userSongInteractionDbHelper.selectUserSongInteractions(null);
        for (UserSongInteraction i : interactions) {
            Log.d("", i.getId() + " " + i.getSongId() + i.getInteractionVerdict() + "\n");
            ArrayList<String> w = new ArrayList<>();
            w.add("" + DbContract.Song.COLUMN_NAME_ID + " = " + i.getSongId());
            Song s = songDbHelper.selectSong(w);
            Log.d("", "" + s.getTitle() + " " + s.getArtist() + "\n");
        }
    }

    @Override
    protected void onDestroy() {
        if (musicBound) unbindService(musicConnection);
        stopService(playIntent);
        musicSrv = null;
        super.onDestroy();
    }

}
