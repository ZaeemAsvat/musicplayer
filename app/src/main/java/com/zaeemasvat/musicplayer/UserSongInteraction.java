package com.zaeemasvat.musicplayer;

public class UserSongInteraction {

    private long song_id;
    private boolean song_interaction;

    UserSongInteraction(long song_id, boolean song_interaction) {
        this.song_id = song_id;
        this.song_interaction = song_interaction;
    }

    public long getSongId() {
        return song_id;
    }

    public void setSongInteraction(boolean song_interaction) {
        this.song_interaction = song_interaction;
    }

    public boolean getSongInteraction() {
        return song_interaction;
    }
}
