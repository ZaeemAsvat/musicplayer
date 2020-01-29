package com.zaeemasvat.musicplayer;

import java.util.ArrayList;
import java.util.HashMap;

public class SongClusterRelation {

    private long song_id;
    private int clusterNum;

    SongClusterRelation(long song_id, int clusterNum) {
        this.song_id = song_id;
        this.clusterNum = clusterNum;
    }

    public long getSongId() {
        return song_id;
    }

    public int getClusterNum() {
        return clusterNum;
    }
}
