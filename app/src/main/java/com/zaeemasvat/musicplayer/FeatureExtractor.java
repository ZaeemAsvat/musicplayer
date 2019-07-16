package com.zaeemasvat.musicplayer;

import android.media.AudioDeviceCallback;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import jAudioFeatureExtractor.ACE.DataTypes.Batch;
import jAudioFeatureExtractor.AudioFeatures.SpectralCentroid;

import static cern.jet.math.Functions.log;

public class FeatureExtractor {

    public void getSpectralCentroid (String filePath) {


        try {

            Log.d("", "hellloooo");

            File[] files = new File[1];
            files[0] = new File(URI.create(Environment.getExternalStorageDirectory().getPath()+ "Paris.mp3"));

            Batch b = new Batch();
            b.setRecordings(files);
            b.execute();
            double[][] answers = b.getDataModel().container.getResults();

            for (double[] row : answers) {
                for (double e : row)
                    Log.d("", e + " ");
                Log.d("", "\n");
            }

        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }

//        try {
//
//            Batch batch = new Batch();
//
//            File[] files = new File[1];
//            files[0] = new File(filePath);
//            batch.setRecordings(files);
//
//            HashMap<String, Boolean> activated = new HashMap<>();
//            activated.put("SpectralCentroid", true);
//            HashMap<String, String[]> attributes = new HashMap<>();
//            String[] temp = {};
//
//        } catch (java.lang.Exception e) {
//            e.printStackTrace();
//        }

    }


}
