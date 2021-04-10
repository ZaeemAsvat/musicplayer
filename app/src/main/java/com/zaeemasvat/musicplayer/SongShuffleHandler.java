package com.zaeemasvat.musicplayer;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class SongShuffleHandler {

    private ArrayList<Integer> songPositions;
    private ArrayList<SongFeatures> songFeaturesList;
    private Clusters songClusters;
    private HashMap<Integer, ArrayList<Integer>> clusterSongMapping;
    private int clusterNum = -1;
    private int numClusters = 0;

    SongShuffleHandler(ArrayList<Song> songs, ArrayList<SongFeatures> songFeaturesList) {

        // set member variables
        this.songPositions = new ArrayList<>(songs.size());
        for (int songPos = 0; songPos < songs.size(); songPos++)
            this.songPositions.add(songPos);
        this.songFeaturesList = new ArrayList<>(songFeaturesList.size());
        this.songFeaturesList.addAll(songFeaturesList);

        // initialize clusters objects for these songs
        try {
            this.numClusters = Math.min(10, this.songPositions.size());
            this.songClusters = new Clusters(this.numClusters);
        } catch (Exception e) { e.printStackTrace(); }

        this.clusterSongMapping = new HashMap<>();
        this.clusterNum = -1;
    }

    public void setupHandler() {

        // set feature column names for clusterer
        ArrayList<Attribute> attributes = new ArrayList<>(this.songFeaturesList.get(0).getSongFeatures().length);
        for (int i = 0; i < this.songFeaturesList.get(0).getSongFeatures().length; i++)
            attributes.add(new Attribute("mfcc " + i));

        // add songs and features for clusterer
        Instances songFeaturesInstances = new Instances("SongFeatureInstances", attributes, 0);
        for (SongFeatures songFeatures : this.songFeaturesList)
            songFeaturesInstances.add(new DenseInstance(1.0, toDoubles(songFeatures.getSongFeatures())));

        try {

            // compute and store cluster-song mappings
            int[] clusters = this.songClusters.getCluisters(songFeaturesInstances);
            for (int i = 0; i < clusters.length; i++) {
                if (!this.clusterSongMapping.containsKey(clusters[i]))
                    this.clusterSongMapping.put(clusters[i], new ArrayList<Integer>());
                this.clusterSongMapping.get(clusters[i]).add(this.songPositions.get(i));
            }

            // shuffle songs in each cluster
            for (int c = 0; c < numClusters; c++) {
                Collections.shuffle(this.clusterSongMapping.get(c));
                Log.d("", "Cluster num: " + c + " Size: " + this.clusterSongMapping.get(c).size());
            }

            this.clusterNum = new Random().nextInt(numClusters);  // init cluster nim

        } catch (Exception e) { e.printStackTrace();}
    }

    public int getNextSongPosition(boolean positiveFeedback) {

        int nextSongPos = -1;

        if (!clusterSongMapping.isEmpty()) { // ensure that there are still clusters available

            if (!positiveFeedback)
                setNewClusterNum(); // get new cluster num if -ve feedback on song from cluster cluster
            // or if current cluster is empty

            // make sure that there are songs current cluster
            while (this.clusterSongMapping.get(this.clusterNum).isEmpty())
                this.clusterSongMapping.remove(this.clusterNum);
            setNewClusterNum();

            nextSongPos = this.clusterSongMapping.get(this.clusterNum).remove(clusterSongMapping.get(this.clusterNum).size() - 1);

        }

        return nextSongPos;
    }

    private void setNewClusterNum() {

        int newClusterNum;

        // shuffle available cluster numbers
        ArrayList<Integer> tempClusterNums = new ArrayList<>(this.clusterSongMapping.keySet().size());
        tempClusterNums.addAll(this.clusterSongMapping.keySet());
        Collections.shuffle(tempClusterNums);

        // get new random cluster number if more than one exists
        newClusterNum = tempClusterNums.get(0);
        if (newClusterNum == this.clusterNum && tempClusterNums.size() > 1) newClusterNum = tempClusterNums.get(1);

        this.clusterNum = newClusterNum;
    }

    private static double[] toDoubles (float[] floats) {
        double[] doubles = new double[floats.length];
        for (int i = 0; i < floats.length; i++)
            doubles[i] = (double) floats[i];
        return doubles;
    }
}