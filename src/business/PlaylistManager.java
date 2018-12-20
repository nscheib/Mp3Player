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
    private ArrayList<String> everyAvailableSong = new ArrayList<String>();
    // Stringarray mit songs als NAMEN
    // groove.mp3


    //private  HashMap songs = new HashMap();

    private ArrayList<String> listePlaylistNamen = new ArrayList<String>();
    // Stringarray mit allen playlistnamen


    private ArrayList<Playlist> listePlaylist = new ArrayList<>();



    public PlaylistManager() {
        updateAllSongs();
//        loadPlaylists("nichts tun");
    }

    public ArrayList<Playlist> getPlaylists(){
        return listePlaylist;
    }

    public void updateAllSongs (){

        File f = new File("tracks");
        File[] fileArray = f.listFiles();
        if (fileArray != null) {
            for (int i = 0; i < fileArray.length; i++) {
                if (fileArray[i].getName().endsWith(".mp3")){
                    everyAvailableSong.add(fileArray[i].getName());
                }
            }
        }
    }

    public ObservableList<String> getSongs(String playlist){
        ObservableList<String> obsList = FXCollections.observableArrayList();


        obsList.addAll(playlist);
        return obsList;
    }

    public ArrayList<String> getAllSongs() {
        return everyAvailableSong;
    }

    public void printPlaylistSongs(String playlistFile) {



        File file = new File(playlistFile);
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(playlistFile));
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
                System.out.println(zeile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }



    }

    public String createPlaylist(String name){
        // HIER NOCH UMWANDELN IN M3U DATEI


        String playlistFile = "playlisten/"+name+".txt"; // Ort wo gespeichert
        File newFile = new File (playlistFile);
        try {
            newFile.createNewFile();
            fileBeschreiben(playlistFile,"#EXTM3U\n");
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
                    listePlaylistNamen.add(name.substring(0,name.length()-4));
                    // Namen der Playlists in array speichern
                    listePlaylist.add( new Playlist(name.substring(0,name.length()-4)) );
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

        File f = new File("playlists/"+playlist.getTitle()+".m3u");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("playlists/" + playlist.getTitle()+".m3u"));
            String line;
            while ((line = reader.readLine()) != null)
            {
//                if (line.contains("#EXTM3U")){
//                    // erste Zeile ignorieren
//                    // hier könnte man die anzahl der lieder counten...
//                    continue;
//                }
                playlist.getSonglist().add(new Track(line.substring(0,line.length()-4)));
            }
            reader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<Track> returnPlaylistSongs(String playlist){

        ObservableList<Track> songsInPlaylist = FXCollections.observableArrayList();


        for (int i = 0; i < listePlaylist.size(); i++){

            if(listePlaylist.get(i).getTitle() == playlist){
                songsInPlaylist.addAll(listePlaylist.get(i).getSonglist());
            }
        }
        return songsInPlaylist;
    }

    public void fileBeschreiben(String playlistFile, String text) {
        //playlistFile ist �bergebene position der playlist und �bergeben mit dem erstellen der playlist
        // Sonst m�sste man noch ein Array mit den positionen der playlisten erstellen (auch filereader)

        FileWriter addSong;
        BufferedWriter writeFile;

        try {

            addSong = new FileWriter(playlistFile, true);
            //writeFile = new BufferedWriter (addSong);

            //Hier beschreiben
            addSong.write(text); // runtime, title
            addSong.flush();
            addSong.close();
            // in der playlist stehen eigentlich die LINKS, nicht NUR die namen ?!


        } catch (IOException e) {
            System.out.println("help");
            e.printStackTrace();
        }

    }

    public void loeschenLied(String playlistFile, String song) {

        File inputFile = new File(playlistFile);

        String string = playlistFile;

        // Playlist String teilen und _temp hinzufügen
        String[] parts = string.split("\\."); // Teilt bei PUNKT
        String part1 = parts[0]; // playlisten/name
        String part2 = parts[1]; // txt
        String playlistFileTemp = part1 + "_temp."+ part2; // fügt part1 + PUNKT + part2 zusammen für tempFile die aber gleich heißt
        File tempFile = new File(playlistFileTemp);


        // pretty much just copy paste from stackoverflow...
        // Also noch bearbeiten sheesh


        try {

            tempFile.createNewFile();

            BufferedReader reader;

            reader = new BufferedReader(new FileReader(inputFile));

            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemove = "#EXTINF:"+song;
            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                //String trimmedLine = currentLine.trim();
                //if(trimmedLine.equals(lineToRemove)) continue;
                if (currentLine.equals(lineToRemove)) continue;
                writer.write(currentLine ); // + System.getProperty("line.separator")


            }
            writer.close();
            reader.close();
            inputFile.delete(); // löscht alte Playlist
            boolean successful = tempFile.renameTo(inputFile); // nennt tempfile um in original
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void deletePlaylist(String playlist){

        File file = new File(playlist);
        if(file.delete()){


            System.out.println(playlist + " deleted");
        }else System.out.println(playlist + " doesn't exists");


    }
}