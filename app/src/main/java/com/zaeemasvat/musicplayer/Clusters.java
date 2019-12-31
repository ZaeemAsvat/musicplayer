package com.zaeemasvat.musicplayer;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

public class Clusters {

    private  SimpleKMeans kmeans;
    private int numClusters;

    /** initialize k means clustering **/
    Clusters (int numClusters) throws Exception {

        this.kmeans = new SimpleKMeans();
        this.kmeans.setSeed(10);

        this.numClusters = numClusters;

        // important parameter to set: preserver order, number of cluster.
        this.kmeans.setPreserveInstancesOrder(true);
        this.kmeans.setNumClusters(this.numClusters);
    }

    /** get cluster assignmments from data passed **/
    public int[] getCluisters (Instances data) throws Exception {

        kmeans.buildClusterer(data);

        // This array returns the cluster number (starting with 0) for each instance
        // The array has as many elements as the number of instances
        int[] assignments = kmeans.getAssignments();

        int i = 0;
        for(int clusterNum : assignments) {
            System.out.printf("Instance %d -> Clusters %d \n", i, clusterNum);
            i++;
        }

        return assignments;
    }

    public void setNumClusters(int numClusters) { this.numClusters = numClusters; }

    public int getNumClusters() { return numClusters; }
}
