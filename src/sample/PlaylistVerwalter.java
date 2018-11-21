package sample;

import de.hsrm.mi.prog.util.StaticScanner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;


public class PlaylistVerwalter {



    private ArrayList<String> availableSongs = new ArrayList<String>();
    // Stringarray mit songs als NAMEN
    // groove.mp3

    //private  HashMap songs = new HashMap();

    private ArrayList<String> liste = new ArrayList<String>();
    // Stringarray mit allen playlists gespeicherten standorten
    // playlisten\playlist1



    public PlaylistVerwalter() {
        updateAllSongs();
    }


    public void updateAllSongs (){

        File f = new File("tracks");
        File[] fileArray = f.listFiles();
        if (fileArray != null) {
            for (int i = 0; i < fileArray.length; i++) {
                if (fileArray[i].getName().endsWith(".mp3")){
                    availableSongs.add(fileArray[i].getName());
                }
            }
        }
    }
    public ArrayList<String> getAllSongs() {
        return availableSongs;
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

    public void loadFromFile(String befehl) {

        File f = new File("playlisten");
        File[] fileArray = f.listFiles();
        if (fileArray != null) {
            for (int i = 0; i < fileArray.length; i++) {
                if (fileArray[i].getName().endsWith(".txt")) {
                    liste.add(fileArray[i].getName());
                    // Namen in array speichern
                }
            }
        }
        if (befehl.equals("ausgabe")) {
            for (int i = 0; i < liste.size(); i++) {
                System.out.println(liste.get(i));
            }
        } else {
            for (int i = 0; i < liste.size(); i++) {
                if (liste.get(i).equals(befehl)) {

                }

            }
        }
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


        //löschenLied(playlist);
    }


    public void loeschenLied(String playlistFile, String song) {

        File inputFile = new File(playlistFile);

        String string = playlistFile;

        // Playlist String teilen und _temp hinzufügen
        String[] parts = string.split("\\.");
        String part1 = parts[0]; // playlisten/name
        String part2 = parts[1]; // txt
        System.out.println(part1 + part2);
        String playlistFileTemp = part1 + "_temp."+ part2;
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

    /*private void löschenLied(String playlist){
        int position = findPlaylist(playlist);
        getPlaylist(position).trackLöschen(eingabe);
    }*/

    public void savePlaylist(Playlist actPlaylist){

    }

    public void deletePlaylist(String playlist){

        File file = new File(playlist);
        if(file.delete()){
            System.out.println(playlist + " deleted");
        }else System.out.println(playlist + " doesn't exists");


    }

    public void updateList(Playlist actPlaylist){



    }



    private void createFile(){

        File f = new File("playlisten");


    }






}
