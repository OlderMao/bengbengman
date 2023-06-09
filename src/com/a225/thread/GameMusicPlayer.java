package com.a225.thread;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GameMusicPlayer extends Thread {
    private final List<String> files;

    public GameMusicPlayer() {
        files = new ArrayList<>();
        files.add("music/bgm0.wav");
        files.add("music/bgm1.wav");
        files.add("music/bgm2.wav");
    }

    @Override
    public void run() {
        startMusic();
    }

    private void startMusic() {

        int i = 0;
        byte[] buffer = new byte[4096];
        while (true) {
            try {
                File file = new File(files.get(i));
                InputStream stream = new FileInputStream(file);
                InputStream bufferedIn = new BufferedInputStream(stream);
                AudioInputStream is = AudioSystem.getAudioInputStream(bufferedIn);
                AudioFormat format = is.getFormat();
                SourceDataLine line = AudioSystem.getSourceDataLine(format);
                line.open(format);
                line.start();
                while (is.available() > 0) {
                    int len = is.read(buffer);
                    line.write(buffer, 0, len);
                }
                line.drain();
                line.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
            i = i % 3;
        }
    }

}