package business;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Stellt eine Playlist dar, die mehrere Songs enthalten kann.
 *
 * Verwaltet keine Funktionen der Playlist,
 * enthaelt lediglich Informationen ueber eine Playlist.
 */
public class Playlist {

    private long id;
    private String title;
    private String creationDate;
    private int amount = 0;
    private ArrayList<Track> songlist = new ArrayList<Track>();

    public ArrayList<Track> getSonglist(){
        return songlist;
    }

    /**
     * Konstruktor
     */
    public Playlist(String title)
    {
        this.title = title;
    }

    /**
     *
     * @return Name der Playlist
     */
    public String getTitle() {
        return title;
    }
}
    /*
        /**
         * Methode um die Anzahl aller Songs zu erhalten
         *
         * @return Songanzahl der Playlist
         *
    private int numberOfTracks() {
        return amount;
    }
}

    /**
     * Methode um einen Song anhand seiner Nummer in der Liste zu erhalten
     * @param number an welcher Stelle der Song in der Playlist steht
     * @return
     *
    public Track getTrackByNumber(int number)
    {
        return songlist.get(number);
    }

    public void addTrack (String song)
    {
        Track track = new Track (song);
        songlist.add(track);
    }

    public String getTitle()
    {
        return this.title;
    }

    public void deleteTrack(String song){

        songlist.remove(song); // Geht das ???
    }

}*/

