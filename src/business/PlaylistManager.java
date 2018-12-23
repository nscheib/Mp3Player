package business;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class PlaylistManager {
    private ArrayList<String> everyAvailableSong = new ArrayList<String>();     // Stringarray mit songs als NAMEN
    private ArrayList<String> listePlaylistNamen = new ArrayList<String>();     // Stringarray mit allen playlistnamen
    private ArrayList<Playlist> listePlaylist = new ArrayList<>();

    public PlaylistManager() {
        updateAllSongs();
//        loadPlaylists("nichts tun");
    }

    public ArrayList<Playlist> getPlaylists() {
        return listePlaylist;
    }

    public void updateAllSongs() {

        File f = new File("tracks");
        File[] fileArray = f.listFiles();
        if (fileArray != null) {
            for (int i = 0; i < fileArray.length; i++) {
                if (fileArray[i].getName().endsWith(".mp3")) {
                    everyAvailableSong.add(fileArray[i].getName());
                }
            }
        }
    }


    public String createPlaylist(String name) {
        String playlistFile = "playlisten/" + name + ".txt"; // Ort wo gespeichert
        File newFile = new File(playlistFile);
        try {
            newFile.createNewFile();
            fileBeschreiben(playlistFile, "#EXTM3U\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return playlistFile;
    }

    public void loadPlaylists(String befehl) {

        File f = new File("playlists");
        File[] fileArray = f.listFiles();
        if (fileArray != null) {
            for (int i = 0; i < fileArray.length; i++) {
                if (fileArray[i].getName().endsWith(".m3u")) {
                    String name = fileArray[i].getName();
                    listePlaylistNamen.add(name.substring(0, name.length() - 4));
                    // Namen der Playlists in array speichern
                    listePlaylist.add(new Playlist(name.substring(0, name.length() - 4)));
                    loadSongsInPlaylist(listePlaylist.get(i));
                    // Playlists komplett speichern
                }
            }
        }
        if (befehl.equals("ausgabe")) {
            for (int i = 0; i < listePlaylistNamen.size(); i++) {
                System.out.println(listePlaylistNamen.get(i));
            }
        } else {
            for (int i = 0; i < listePlaylistNamen.size(); i++) {
                if (listePlaylistNamen.get(i).equals(befehl)) {
                }
            }
        }
    }

    private void loadSongsInPlaylist(Playlist playlist) {
        File f = new File("playlists/" + playlist.getTitle() + ".m3u");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("playlists/" + playlist.getTitle() + ".m3u"));
            String line;
            while ((line = reader.readLine()) != null) {
                playlist.getSonglist().add(new Track(line.substring(0, line.length() - 4)));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Track> returnPlaylistSongs(String playlist) {
        ObservableList<Track> songsInPlaylist = FXCollections.observableArrayList();
        for (int i = 0; i < listePlaylist.size(); i++) {

            if (listePlaylist.get(i).getTitle() == playlist) {
                songsInPlaylist.addAll(listePlaylist.get(i).getSonglist());
            }
        }
        return songsInPlaylist;
    }

    public void fileBeschreiben(String playlistFile, String text) {
        FileWriter addSong;
        BufferedWriter writeFile;

        try {
            addSong = new FileWriter(playlistFile, true);
            addSong.write(text); // runtime, title
            addSong.flush();
            addSong.close();
        } catch (IOException e) {
            System.out.println("help");
            e.printStackTrace();
        }
    }

}