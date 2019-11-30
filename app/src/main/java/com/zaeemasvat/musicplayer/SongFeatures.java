package com.zaeemasvat.musicplayer;

import java.util.ArrayList;
import java.util.Arrays;

enum SongFeature {
    mfcc1,
    mfcc2,
    mfcc3,
    numSongFeatures
}

public class SongFeatures {

    private long song_id;
    private Float[] songFeatures;

    SongFeatures (long song_id, Float[] songFeatures) {
        this.song_id = song_id;
        this.songFeatures = new Float[songFeatures.length];
        for (int i = 0; i < songFeatures.length; i++)
            this.songFeatures[i] = songFeatures[i];
    }

    public long getSong_id() {
        return song_id;
    }

    public void setSongFeatures(Float[] songFeatures) {
        this.songFeatures = songFeatures;
    }

    public Float[] getSongFeatures() {
        return songFeatures;
    }

    public void setSongFeature(SongFeature songFeature, float songFeatureVal) {

        if (songFeature.ordinal() < SongFeature.numSongFeatures.ordinal())
            songFeatures[songFeature.ordinal()] = songFeatureVal;
    }

    public float getSongFeature(SongFeature songFeature) {

        float featureVal = 0f;
        if (songFeature.ordinal() < SongFeature.numSongFeatures.ordinal())
            featureVal = songFeatures[songFeature.ordinal()];

        return featureVal;
    }


}
