package sample;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Playlist {

    private long id;
    private String title;
    private String creationDate;
    private int trackanzahl = 0;
    private ArrayList<Track> tracklist = new ArrayList<Track>();
    String coverFile;

    public Playlist(String titel){
        this.title = titel;

    }
    private int numberOfTracks(){
        return trackanzahl;
    }

    public Track getTrack(int number){
        return tracklist.get(number);
    }

    public void generateTrack(String titel){
        Track song = new Track(titel);
    }


}

