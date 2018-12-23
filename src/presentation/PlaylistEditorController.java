package presentation;

import business.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.File;

public class PlaylistEditorController {

    private PlaylistEditorView view;
    private PlaylistManager playlistVerwalter = new PlaylistManager();

    private String auswahlPlaylist;
    private Track auswahlTrackPlaylist,auswahlSongPlaylist;
    private int aktPosition,aktPositionTrack;
    private ListView<Track> listViewR;

    private ObservableList<Track> songsInPlaylist = FXCollections.observableArrayList(); // für buttonclicked 2

    public int getAktPositionTrack(){return aktPositionTrack;}

    public PlaylistEditorController(Main application, Mp3Player mp3Player){

        view = new PlaylistEditorView(playlistVerwalter);
        this.listViewR = view.listViewR;
        //view.add.setOnAction(e -> buttonClicked(0));                  //Add Playlist
        view.delete.setOnAction(e -> buttonClicked(1));           //Delete chosen Playlist
        view.loadPlaylist.setOnAction(e-> buttonClicked(2));      //Load Playlist in listViewR
        //view.add2.setOnAction(e -> buttonClicked(3));                 //Add Song to Playlist
        //view.delete2.setOnAction(e -> buttonClicked(4));              //Delete Song from Playlist

        view.play.setOnAction(e-> playAndSwitch(application,mp3Player));

        view.changeButton.setOnAction(e->application.switchScene("MP3player"));
        view.getStylesheets().add(getClass().getResource("playlistview.css").toExternalForm());

        //Listener für Liedauswahl beim Player
        view.listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                auswahlPlaylist = newValue;
                aktPosition = view.listView.getItems().indexOf(auswahlPlaylist);
                //playlistVerwalter.getPlaylists().indexOf(auswahlPlaylist);
                System.out.println("Playlist: "+auswahlPlaylist);
                System.out.println("PlaylistPosition: "+aktPosition);
//                previousSong = playlistVerwalter.getAllSongs().get(aktPosition-1);
//                nextSong = playlistVerwalter.getAllSongs().get(aktPosition+1);

                // Noch nicht so ganz klar ob beim skippen der aktuelle Song auch noch gespeichert wird und man immer weiter skippen kann..
                // Also hier auch noch den Fall beachten, dass beim ersten Song kein previous sond existiert und deshalb das lied einfach gestoppt wird...
            }
        });

        //Listener für Liedauswahl bei der Playlist

        view.listViewR.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Track>() {
            @Override
            public void changed(ObservableValue<? extends Track> observable, Track oldValue, Track newValue) {

                try {
                    // Bei jedem Klick sicher gehen, dass aktuelle Playlist / Song immer richtig ist
                    mp3Player.setAktuellePlaylist(view.listViewR.getItems());
                    mp3Player.setAktuellerTrack(newValue);


                    auswahlTrackPlaylist = newValue;
                    aktPositionTrack = view.listViewR.getItems().indexOf(auswahlTrackPlaylist);
                    mp3Player.setAktuellePosition(aktPositionTrack);
                    //playlistVerwalter.getPlaylists().indexOf(auswahlPlaylist);
                    System.out.println("Track: " + auswahlTrackPlaylist.getFileName());
                    System.out.println("Trackposition: " + aktPositionTrack);
//                previousSong = playlistVerwalter.getAllSongs().get(aktPosition-1);
//                nextSong = playlistVerwalter.getAllSongs().get(aktPosition+1);

                    // Noch nicht so ganz klar ob beim skippen der aktuelle Song auch noch gespeichert wird und man immer weiter skippen kann..
                    // Also hier auch noch den Fall beachten, dass beim ersten Song kein previous sond existiert und deshalb das lied einfach gestoppt wird...
                }catch(NullPointerException e){
                    System.out.println("Playlist geladen, aber NullPointerException, da in zweiter standardmäßig nichts ausgewählt ist");}
            }
        });
    }

    private void playAndSwitch(Main application, Mp3Player mp3Player) {
        mp3Player.playSelected(auswahlTrackPlaylist.getFileName());
        application.switchScene("MP3player");
    }

    public ListView<Track> getListViewR(){
        return listViewR;
    }

    private void buttonClicked(int befehl){

        ObservableList<String> playlists;
        playlists = view.listView.getSelectionModel().getSelectedItems();

        // Macht es Sinn das hier zu platzieren ?

        // Hier können wir auch aktivieren, dass mehrere Playlist gleichzeitig gelöscht werden können

        if (befehl == 0){
            // Playlist neu erstellen...
            // Neues Fenster in neuer Scene...
            //window.setScene(scene4);

            System.out.println("Gib Name und die Songs ein ...");


        }

        if (befehl == 1){
            // Playlist löschen

            if (aktPosition == -1){
                System.out.println("Keine Playlist zu löschen da...");
            }
            String ort = "playlists/"+auswahlPlaylist;
            File delete = new File(ort);
            delete.delete();
            view.listView.getItems().remove(aktPosition);

        }

        if (befehl == 2){
            // Ausgewählte Playlist laden...
            view.listViewR.getItems().clear();

            songsInPlaylist = playlistVerwalter.returnPlaylistSongs(auswahlPlaylist);

            view.listViewR.setCellFactory(new Callback<ListView<Track>, ListCell<Track>>() {
                @Override
                public ListCell<Track> call(ListView<Track> param) {
                    return new CustomTrackCell();
                }
            });

            view.listViewR.setItems(songsInPlaylist);
            view.listViewR.refresh();

        }

        if (befehl == 3){
            System.out.println("Lied "+ playlists + " wurde hinzugefügt");
            // Hier Liedauswähler implementieren
        }

        if (befehl == 4){
            System.out.println("Lied "+ playlists + " wurde gelöscht");
        }

        // hier machen dass playlist erstellt / gelöscht wird

    }


    public Pane getView(){
        return view;
    }

    public void setAktPositionTrack(int i) {
        this.aktPositionTrack = i;
    }
}