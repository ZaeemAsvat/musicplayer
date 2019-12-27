package com.zaeemasvat.musicplayer;

public class UserSongInteraction {

    private long song_id;
    private int num_positive_interactions;
    private int num_negative_interactions;

    UserSongInteraction() {}

    UserSongInteraction(long song_id, int num_positive_interactions, int num_negative_interactions) {
        this.song_id = song_id;
        this.num_positive_interactions = num_positive_interactions;
        this.num_negative_interactions = num_negative_interactions;
    }

    public void setSongId(long song_id) {
        this.song_id = song_id;
    }

    public long getSongId() {
        return song_id;
    }

    public int getNum_positive_interactions() { return num_positive_interactions; }

    public int getNum_negative_interactions() { return num_negative_interactions; }

    public void setNum_positive_interactions(int num_positive_interactions) { this.num_positive_interactions = num_positive_interactions; }

    public void setNum_negative_interactions(int num_negative_interactions) { this.num_negative_interactions = num_negative_interactions; }
}
