package businessLogic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Klasse PlayListManager verwaltet und erstellt Playliste aus dem Playlisten Ordner
 */
public class PlayListManager {

    private HashMap<String, Track> allSongsInPlayList = new HashMap<>();
    private ArrayList<String> allPlayLists = new ArrayList();
    private ArrayList<String> choosenPlayList = new ArrayList<>();

    // Constructor
    public PlayListManager() {
        loadAllPlayLists();
        updateAllSongs();
    }

    /**
     * Methode läd alle im Playlistenordner existierenden Playlisten
     */
    public void loadAllPlayLists() {
        File file = new File("Playlisten");
        File[] useableFiles = file.listFiles();

        if (useableFiles != null) {
            for (File tempfile : useableFiles) {
                if (tempfile.getName().endsWith(".m3u")) {
                    allPlayLists.add("Playlisten/" + tempfile.getName());
                }
            }
        }
    }

    /**
     * Methode erstellt aus allen Songs eine HashMap mit einerm K und V
     * k = name (String)
     * v = obj. (Track)
     */
    public void updateAllSongs() {
        File file = new File("tracks");
        File[] tmpFile = file.listFiles();

        if (tmpFile != null) {
            for ( File tempfile : tmpFile) {
                if (tempfile.getName().endsWith(".mp3")) {
                    String title = tempfile.getName();
                    Track track = new Track(title);
                    allSongsInPlayList.put(title, track);
                }
            }
        }
    }


    /**
     * Methode läd eine Playlist mit bestimmtem namen
     * @param playListFile
     * @return
     */
    public ArrayList<String> loadChoosenPlayList(String playListFile) {

        if(choosenPlayList != null) {
            choosenPlayList.clear();
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(playListFile));
            String text;
            while ((text = reader.readLine()) != null) {
                choosenPlayList.add(text);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Es wurde keine übereinstimmende Playlist gefunden!");
        }
        return choosenPlayList;
    }

    /**
     * Methode gibt alle Songs als ObservableList mit dem Typ <Track> aus einer gewählten Playlist zurück
     * @param playlist
     * @return
     */
    public ObservableList<Track> returnPlaylistSongs(String playlist) {
        ObservableList<Track> songsInPlaylist = FXCollections.observableArrayList();
        for (String tempList :allPlayLists) {
            if (playlist.equalsIgnoreCase(tempList)) {

                loadChoosenPlayList(tempList);
                for (String tempName : choosenPlayList) {
                    songsInPlaylist.addAll(allSongsInPlayList.get(tempName));
                }
            }
        }
        return songsInPlaylist;
    }

    public boolean deletePlayList(String playlist) {
        for (int i = 0; i < allPlayLists.size()-1; i++) {
            if(allPlayLists.get(i).equalsIgnoreCase("Playlisten/" + playlist + ".m3u")) {
                allPlayLists.remove(i);
            }
        }

        File file = new File("Playlisten");
        File[] tempFile = file.listFiles();

        for (int i = 0; i < tempFile.length-1;i++) {
            if (tempFile[i].toString().equals("Playlisten\\" + playlist + ".m3u")) {
                tempFile[i].delete();
                return true;
            }
        }
        return false;
    }

    /**
     * Methode gibt einen bestimmtes Track obj. aus der HashMap zurück
     * @param trackname
     * @return
     */
    public Track getOneSongFromList(String trackname) { return allSongsInPlayList.get(trackname); }

    /**
     * Methode gibt alle Namen (String) der Playlisten zurück
     */
    public ArrayList<String> getAllPlayLists() { return allPlayLists; }

}
