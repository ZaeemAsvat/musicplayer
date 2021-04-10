package com.zaeemasvat.musicplayer;

public class Playlist {

    long playlistId;
    String playlistName;

    Playlist() {}

    Playlist(long playlistId, String playlistName) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
    }

    public long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPlaylistName() {
        return playlistName;
    }
}
