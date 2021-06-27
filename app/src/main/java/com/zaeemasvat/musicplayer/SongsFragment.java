package com.zaeemasvat.musicplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ListView;

import java.util.ArrayList;

public class SongsFragment extends Fragment {

    private ArrayList<Song> songList;
    private ArrayList<Artist> artistList;
    private ListView songView;

    // declare database table interfaces
    DbHelper db;
//    SongDbHelper songDbHelper;
//    ArtistDbHelper artistDbHelper;
    UserSongInteractionDbHelper userSongInteractionDbHelper;
//    SongFeatureDbHelper songFeatureDbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_songs, container, false);

        songList = getArguments().get;

        songView = (ListView) rootView.findViewById(R.id.song_list);

        return rootView;
    }
}