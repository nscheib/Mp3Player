package sample;

import java.util.ArrayList;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class PlaylistVerwalter {



   private ArrayList<String> songs = new ArrayList<String>();

   //private  HashMap songs = new HashMap();

    private ArrayList<Playlist> liste = new ArrayList<Playlist>();

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

    public Playlist getPlaylist(int index){


        return liste.get(index);
    }

    public void addPlaylist(String name){

        Playlist playlist = new Playlist(name);

        liste.add(playlist);

    }

    public int findPlaylist(String name){

        for (int i = 0; i < liste.size(); i++){
            if (name.equals(liste.get(i).getTitle()) ){
                return i;
            }
        }

        return -1; // playlist nicht gefunden
    }

    public void savePlaylist(Playlist actPlaylist){

    }

    public void deletePlaylist(Playlist actPlaylist){

    }

    public void updateList(Playlist actPlaylist){



    }

}
