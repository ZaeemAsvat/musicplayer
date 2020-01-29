package com.zaeemasvat.musicplayer;

import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;

public class SongShuffleHandler {

    private ArrayList<Long> songIds;
    private ArrayList<SongFeatures> songFeaturesList;
    private Clusters songClusters;

    SongShuffleHandler(ArrayList<Song> songs, ArrayList<SongFeatures> songFeaturesList) {

        // set member variables
        this.songIds = new ArrayList<>(songs.size());
        for (Song song : songs)
            this.songIds.add(song.getId());
        this.songFeaturesList = new ArrayList<>(songFeaturesList.size());
        this.songFeaturesList.addAll(songFeaturesList);

        // initialize clusters objects for these songs
        try { songClusters = new Clusters(songs.size() / 5); } catch (Exception e) { e.printStackTrace(); }
    }

    public void setupHandlerAndShuffleSongs() {

        // TODO: Test this function

        ArrayList<Attribute> attributes = new ArrayList<>(songFeaturesList.get(0).getSongFeatures().length);
        for (int i = 0; i < songFeaturesList.get(0).getSongFeatures().length; i++)
            attributes.add(new Attribute("mfcc " + i));

        Instances songFeaturesInstances = new Instances("SongFeatureInstances", attributes, 0);
        for (SongFeatures songFeatures : songFeaturesList)
            songFeaturesInstances.add(new DenseInstance(1.0, toDoubles(songFeatures.getSongFeatures())));

        try { int[] clusters = songClusters.getCluisters(songFeaturesInstances); } catch (Exception e) { e.printStackTrace();}
    }

    private static double[] toDoubles (float[] floats) {
        double[] doubles = new double[floats.length];
        for (int i = 0; i < floats.length; i++)
            doubles[i] = (double) floats[i];
        return doubles;
    }
}
