package com.zaeemasvat.musicplayer;

import android.media.AudioDeviceCallback;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.UniversalAudioInputStream;
import be.tarsos.dsp.io.android.AndroidFFMPEGLocator;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.mfcc.MFCC;
import jAudioFeatureExtractor.ACE.DataTypes.Batch;
import jAudioFeatureExtractor.AudioFeatures.SpectralCentroid;

import static cern.jet.math.Functions.log;

public class FeatureExtractor {

    public float[] getMFCC (String filePath) throws FileNotFoundException {

        final float[] result = new float[10];

        int sampleRate = 44100;
        int bufferSize = 1024;
        int bufferOverlap = 128;
//        new AndroidFFMPEGLocator(this);
        InputStream inStream = new FileInputStream(filePath);
        AudioDispatcher dispatcher = new AudioDispatcher(new UniversalAudioInputStream(inStream, new TarsosDSPAudioFormat(sampleRate, bufferSize, 1, true, true)), bufferSize, bufferOverlap);
        final MFCC mfcc = new MFCC(bufferSize, sampleRate, 40, 50, 300, 3000);

        dispatcher.addAudioProcessor(mfcc);
        dispatcher.addAudioProcessor(new AudioProcessor() {

            @Override
            public void processingFinished() {

                //vvv error b/c mfcc instance variable is null
                float[] temp = mfcc.getMFCC();
                System.out.println(temp.length);
                int size = Math.min(result.length, temp.length);
                for (int i = 0; i < size; i++)
                    result[i] = temp[i];
                System.out.println("DONE");
            }

            @Override
            public boolean process(AudioEvent audioEvent) {
                // breakpoint or logging to console doesn't enter function
                return true;
            }
        });
        dispatcher.run();

        return result;
    }

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
