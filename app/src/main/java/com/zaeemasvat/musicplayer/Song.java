package com.zaeemasvat.musicplayer;

public class Song {

    final private long id;
    final private String title;
    final private Long artistId;

    final private String path;

    Song (long id, String title, Long artistId, String path) {
        this.id = id;
        this.title = title;
        this.artistId = artistId;
        this.path = path;
    }

    public long getId() { return id; }

    public String getTitle() { return title; }

    public Long getArtistId() {
        return artistId;
    }

    public String getPath() { return path; }
}
