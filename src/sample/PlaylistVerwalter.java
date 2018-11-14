package sample;

import de.hsrm.mi.prog.util.StaticScanner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class PlaylistVerwalter {



   private ArrayList<String> songs = new ArrayList<String>();

   //private  HashMap songs = new HashMap();

    private ArrayList<String> liste = new ArrayList<String>();

    public PlaylistVerwalter() {
        updateAllSongs();
    }


    public void updateAllSongs (){

        File f = new File("tracks");
        File[] fileArray = f.listFiles();
        if (fileArray != null) {
            for (int i = 0; i < fileArray.length; i++) {
                if (fileArray[i].getName().endsWith(".mp3")){
                    songs.add(fileArray[i].getName());
                }
            }
        }
    }
    public ArrayList<String> getAllSongs() {
        return songs;
    }


    public String createPlaylist(String name){
        String playlistFile = "playlisten\\"+name+".m3u";
        File file = new File (playlistFile);
        return playlistFile;
    }

    public void loadFromFile(String befehl) {

        File f = new File("playlisten");
        File[] fileArray = f.listFiles();
        if (fileArray != null) {
            for (int i = 0; i < fileArray.length; i++) {
                if (fileArray[i].getName().endsWith(".m3u")) {
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


        public void hinzufügenLied(String playlistFile, String song) {

            try {
                FileWriter playlistLiedhinzufügen = new FileWriter(playlistFile);
                BufferedWriter playlistFileBeschreiber = new BufferedWriter (playlistLiedhinzufügen);

                //Hier beschreiben
                playlistFileBeschreiber.write(song + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }


            //löschenLied(playlist);
        }

    /*private void löschenLied(String playlist){
        int position = findPlaylist(playlist);
        getPlaylist(position).trackLöschen(eingabe);
    }*/

    public void savePlaylist(Playlist actPlaylist){

    }

    public void deletePlaylist(Playlist actPlaylist){

    }

    public void updateList(Playlist actPlaylist){



    }



    private void createFile(){

        File f = new File("playlisten");


    }



}
