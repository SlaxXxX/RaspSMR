import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Main {
    Logger logger = Logger.instance();
    File[] fileList;
    String folderLocation;
    MediaPlayer mediaPlayer;

    public static void main(String[] args) {
        new Main(args);
    }

    private Main(String[] args) {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });

        logger.logAudioAndWait("hello_world");

        if (args.length == 0) {
            logger.logAudioAndWait("no_arg");
            return;
        }

        folderLocation = args[0];
        File folder = new File(folderLocation);
        if (!folder.isDirectory()) {
            logger.logAudioAndWait("invalid_path");
            return;
        }

        fileList = folder.listFiles(pathname -> pathname.getName().endsWith(".mp3") || pathname.getName().endsWith(".wav"));

        if (fileList.length == 0) {
            logger.logAudioAndWait("no_files");
            return;
        }

        playFile(fileList[0]);
    }

    private void playFile(File file) {
        try {
            Media hit = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
            mediaPlayer.setOnReady(() -> {
                try {
                    Thread.sleep((long) hit.getDuration().toMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                quit();
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void quit() {
        mediaPlayer.dispose();
        fileList[0].delete();
        System.exit(0);
    }
}
