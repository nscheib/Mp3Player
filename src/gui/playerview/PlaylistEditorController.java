package gui.playerview;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.Mp3Player;
import sample.PlaylistVerwalter;

import java.io.File;

public class PlaylistEditorController {

    private PlaylistEditorView view;
    private PlaylistVerwalter playlistVerwalter = new PlaylistVerwalter();

    private String auswahlPlaylist,auswahlSongPlaylist;
    private int aktPosition,aktPositionSong;

    private ObservableList<String> songsInPlaylist = FXCollections.observableArrayList(); // für buttonclicked 2

    public PlaylistEditorController(Main application, Mp3Player mp3Player){

        view = new PlaylistEditorView(playlistVerwalter);
        //view.add.setOnAction(e -> buttonClicked(0));            //Add Playlist
        view.delete.setOnAction(e -> buttonClicked(1));         //Delete chosen Playlist
        view.loadPlaylist.setOnAction(e-> buttonClicked(2));    //Load Playlist in listViewR
        //view.add2.setOnAction(e -> buttonClicked(3));           //Add Song to Playlist
        //view.delete2.setOnAction(e -> buttonClicked(4));        //Delete Song from Playlist


        view.play.setOnAction(e -> mp3Player.playSelected(auswahlSongPlaylist));

        view.changeWindow2.setOnAction(e->application.switchScene("MP3player"));
        view.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());

        //Listener für Liedauswahl beim Player
        view.listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                auswahlPlaylist = newValue;
                aktPosition = view.listView.getItems().indexOf(auswahlPlaylist);
                        //playlistVerwalter.getPlaylists().indexOf(auswahlPlaylist);
                System.out.println("Test: "+auswahlPlaylist);
                System.out.println("Test2: "+aktPosition);
//                previousSong = playlistVerwalter.getAllSongs().get(aktPosition-1);
//                nextSong = playlistVerwalter.getAllSongs().get(aktPosition+1);

                // Noch nicht so ganz klar ob beim skippen der aktuelle Song auch noch gespeichert wird und man immer weiter skippen kann..
                // Also hier auch noch den Fall beachten, dass beim ersten Song kein previous sond existiert und deshalb das lied einfach gestoppt wird...
            }
        });

        //Listener für Liedauswahl bei der Playlist ListViewR
        view.listViewR.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                auswahlSongPlaylist = newValue;
                aktPositionSong = view.listViewR.getItems().indexOf(auswahlSongPlaylist);
                //playlistVerwalter.getPlaylists().indexOf(auswahlPlaylist);
                System.out.println("Test: "+auswahlSongPlaylist);
                System.out.println("Test2: "+aktPositionSong);
//                previousSong = playlistVerwalter.getAllSongs().get(aktPosition-1);
//                nextSong = playlistVerwalter.getAllSongs().get(aktPosition+1);

                // Noch nicht so ganz klar ob beim skippen der aktuelle Song auch noch gespeichert wird und man immer weiter skippen kann..
                // Also hier auch noch den Fall beachten, dass beim ersten Song kein previous sond existiert und deshalb das lied einfach gestoppt wird...
            }
        });
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
                System.out.println("Keine Playlist zu löschen da...");;
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
            view.listViewR.getItems().addAll(songsInPlaylist);
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
}
