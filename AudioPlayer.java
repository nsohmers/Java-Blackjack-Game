
import java.io.*;
import java.util.concurrent.CompletableFuture;

import javax.sound.sampled.*;

public class AudioPlayer {
    private Clip winClip, loseClip, hitClip, shuffleClip, standClip;

    public AudioPlayer() {
        String filePath = "audios/winning.wav";
        
        try {
            AudioInputStream winAudio = AudioSystem.getAudioInputStream(new File(filePath));
            AudioFormat format = winAudio.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            int bufferSize = (int) info.getMaxBufferSize();
            
            bufferSize = Math.min(bufferSize, 8192);

            info = new DataLine.Info(Clip.class, format, bufferSize);
            
            winClip = (Clip) AudioSystem.getLine(info);
            winClip.open(winAudio);
        } catch (Exception e) {
            System.err.println("Error loading audio: " + e.getMessage());
        }

        filePath = "audios/losing.wav";

        try {
            AudioInputStream loseAudio = AudioSystem.getAudioInputStream(new File(filePath));
            AudioFormat format = loseAudio.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
        
            int bufferSize = (int) info.getMaxBufferSize();
        
            bufferSize = Math.min(bufferSize, 8192); 
        
            info = new DataLine.Info(Clip.class, format, bufferSize);
        
            loseClip = (Clip) AudioSystem.getLine(info);
            loseClip.open(loseAudio);
        } catch (Exception e) {
            System.err.println("Error loading audio: " + e.getMessage());
        }

        filePath = "audios/hitting.wav";

        try {
            AudioInputStream hitAudio = AudioSystem.getAudioInputStream(new File(filePath));
            AudioFormat format = hitAudio.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
        
            info = new DataLine.Info(Clip.class, format, ((int) info.getMaxBufferSize()));
        
            hitClip = (Clip) AudioSystem.getLine(info);
            hitClip.open(hitAudio);
        } catch (Exception e) {
            System.err.println("Error loading audio: " + e.getMessage());
        }

        filePath = "audios/standing.wav";

        try {
            AudioInputStream standAudio = AudioSystem.getAudioInputStream(new File(filePath));
            AudioFormat format = standAudio.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
        
            info = new DataLine.Info(Clip.class, format, ((int) info.getMaxBufferSize()));
        
            standClip = (Clip) AudioSystem.getLine(info);
            standClip.open(standAudio);
        } catch (Exception e) {
            System.err.println("Error loading audio: " + e.getMessage());
        }

        filePath = "audios/shuffling.wav";

        try {
            AudioInputStream shuffleAudio = AudioSystem.getAudioInputStream(new File(filePath));
            AudioFormat format = shuffleAudio.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            info = new DataLine.Info(Clip.class, format, ((int) info.getMaxBufferSize()));
        
            shuffleClip = (Clip) AudioSystem.getLine(info);
            shuffleClip.open(shuffleAudio);
        } catch (Exception e) {
            System.err.println("Error loading audio: " + e.getMessage());
        }
    }

    public void playWinAudio() {
        try {   
            winClip.setFramePosition(0); //Reset Audio

            CompletableFuture.runAsync(() -> { //Async function to run in the background
                winClip.start(); //Start audio
            });

        } catch(Exception e) {
            System.out.println("Error playing win audio: " + e.getMessage());
        }
    }

    public void playLoseAudio() {
        try {   
            loseClip.setFramePosition(0); //Reset Audio

            CompletableFuture.runAsync(() -> { //Async function to run in the background
                loseClip.start(); //Start audio
            });

        } catch(Exception e) {
            System.out.println("Error playing win audio: " + e.getMessage());
        }
    }

    public void playHitAudio() {
        try {   
            hitClip.setFramePosition(0); //Reset Audio

            CompletableFuture.runAsync(() -> { //Async function to run in the background
                hitClip.start(); //Start audio
            });

        } catch(Exception e) {
            System.out.println("Error playing win audio: " + e.getMessage());
        }
    }

    public void playStandAudio() {
        try {   
            standClip.setFramePosition(0); //Reset Audio

            CompletableFuture.runAsync(() -> { //Async function to run in the background
                standClip.start(); //Start audio
            });

        } catch(Exception e) {
            System.out.println("Error playing win audio: " + e.getMessage());
        }
    }

    public void playShuffleAudio() {
        try {   
            shuffleClip.setFramePosition(0); //Reset Audio

            CompletableFuture.runAsync(() -> { //Async function to run in the background
                shuffleClip.start(); //Start audio
            });

        } catch(Exception e) {
            System.out.println("Error playing win audio: " + e.getMessage());
        }
    }
}
