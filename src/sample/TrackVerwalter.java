package sample;

import java.io.File;
import java.util.ArrayList;

public class TrackVerwalter {

    private File f = new File("tracks");
    private File [] fileArray = f.listFiles();
    private ArrayList<String> songs = new ArrayList<String>();

    public TrackVerwalter(){

        if (fileArray != null) {
            for (int i = 0; i < fileArray.length; i++) {
                if (fileArray[i].getName().endsWith(".mp3")){
                    songs.add(fileArray[i].getName());
                    Track lied = new Track(fileArray[i].getName());
                }
            }
        }
    }


    public ArrayList<String> getAllSongs() {
        return songs;
    }
}
