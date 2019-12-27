package com.zaeemasvat.musicplayer;

public class SongFeatures {

    private long song_id;
    private float[] songFeatures;

    SongFeatures (long song_id, float[] songFeatures) {
        this.song_id = song_id;
        this.songFeatures = new float[songFeatures.length];
        for (int i = 0; i < songFeatures.length; i++)
            this.songFeatures[i] = songFeatures[i];
    }

    public long getSong_id() {
        return song_id;
    }

    public void setSongFeatures(float[] songFeatures) {
        this.songFeatures = songFeatures;
    }

    public float[] getSongFeatures() {
        return songFeatures;
    }

}
