package com.zaeemasvat.musicplayer;

public class PlaylistSongRelation {

    long songId, playlistId;

    PlaylistSongRelation(long songId, long playlistId) {
        this.songId = songId;
        this.playlistId = playlistId;
    }

    public void setPlaylistId(long playlistId) {
        this.playlistId = playlistId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public long getPlaylistId() {
        return playlistId;
    }

    public long getSongId() {
        return songId;
    }
}
