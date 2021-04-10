package com.zaeemasvat.musicplayer;

public class Artist {

    long id;
    String name;

    Artist(long id, String name) {
        this.id = id;
        this.name = name;
    }

    Artist(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
