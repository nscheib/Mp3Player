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


