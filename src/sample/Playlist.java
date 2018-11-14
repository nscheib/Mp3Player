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


    public Playlist(String name){


        this.title = name;
    }
    private int numberOfTracks(){
        return trackanzahl;
    }

    public Track getTrackByNumber(int number){
        return tracklist.get(number);
    }

    public void trackHinzufügen(String lied){
        Track song = new Track (lied);
        tracklist.add(song);
    }

    public String getTitle(){

        return this.title;

    }

    public void trackLöschen(String song){

        tracklist.remove(song); // Geht das ???
    }

}

