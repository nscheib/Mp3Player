package sample;

import java.util.Date;

public class Playlist {

    private long id;
    private String title;
    private Date creationDate;
    private int trackanzahl = 0;
    private Track [] tracklist = new Track[10];

    String coverFile;
    private int numberOfTracks(){
        return trackanzahl;
    }

    public Track getTrack(int number){
        return tracklist[number];
    }


}

