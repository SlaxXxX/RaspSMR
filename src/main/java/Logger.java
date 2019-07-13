import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.File;

public class Logger {
    private static Logger instance;
    private Clip clip;

    public static Logger instance() {
        if (instance == null)
            instance = new Logger();
        return instance;
    }

    private Logger() {
    }

    public void log(String message) {
        System.out.println(message);
    }

    public void logAudio(String soundFile) {
        String filename = "audioCommands/" + soundFile + ".wav";
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    public void logAudioAndWait(String soundFile) {
        logAudio(soundFile);
        waitForClip();
    }

    public void waitForClip() {
        try {
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
