package com.zaeemasvat.musicplayer;

public class UserSongInteraction {

    private long id;
    private long song_id;
    private boolean interactionVerdict;

    UserSongInteraction() {}

    UserSongInteraction(long id, long song_id, boolean interactionVerdict) {
        this.id = id;
        this.song_id = song_id;
        this.interactionVerdict = interactionVerdict;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setSongId(long song_id) {
        this.song_id = song_id;
    }

    public long getSongId() {
        return song_id;
    }

    public void setInteractionVerdict(boolean interactionVerdict) {
        this.interactionVerdict = interactionVerdict;
    }

    public boolean getInteractionVerdict() {
        return interactionVerdict;
    }
}
