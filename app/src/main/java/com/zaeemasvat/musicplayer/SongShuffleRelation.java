package com.zaeemasvat.musicplayer;

public class SongShuffleRelation {

    private long song_id;
    private int shuffleOrderNum;

    SongShuffleRelation(long song_id, int shuffleOrderNum) {
        this.shuffleOrderNum = shuffleOrderNum;
        this.song_id = song_id;
    }

    public void setShuffleOrderNum(int shuffleOrderNum) {
        this.shuffleOrderNum = shuffleOrderNum;
    }

    public int getShuffleOrderNum() {
        return shuffleOrderNum;
    }

    public long getSongId() {
        return song_id;
    }
}
