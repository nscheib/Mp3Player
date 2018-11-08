package sample;

import java.util.ArrayList;

public class PlaylistVerwalter {

    public Playlist getPlaylist(String name){
        Playlist playListe= new Playlist(name);

        return playListe;
    }

    /*public Playlist getAllTracks (){

        Playlist playListe= new Playlist();

        return playListe;
    }*/

    public ArrayList<Playlist> findPlaylist(String name){
        ArrayList<Playlist> liste = new ArrayList<Playlist>();
        return liste;
    }

    public void savePlaylist(Playlist actPlaylist){

    }

    public void deletePlaylist(Playlist actPlaylist){

    }

    public void updateList(Playlist actPlaylist){

    }

}
