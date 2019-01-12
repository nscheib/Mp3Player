package gui.Controller;

import businessLogic.Main;
import businessLogic.Mp3Player;
import businessLogic.PlayListManager;
import businessLogic.Track;
import gui.view.PlayListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;


/**
 * Klasse PlayListController händelt die einzelen Funktionen von Gui-Elementen und verwaltet Listener,
 * dient als Schnittstelle zwischen Gui und der Businesslogic
 */
public class PlayListController {

    private PlayListView view;
    private PlayListManager playListManager;
    private Track auswahlTrackPlayList;
    private ListView<Track> spaltenViewRight;
    private String auswahlPlaylist;
    private ObservableList<Track> songsInPlaylist = FXCollections.observableArrayList();

    // Construtor
    public PlayListController(Main application, Mp3Player player) {

        // Läde den PlayListManager aus dem Player in ein Klassenatribut
        this.playListManager = player.getPlayListManager();

        view = new PlayListView(playListManager);
        this.spaltenViewRight = view.getSpaltenViewRight();

        // Stylesheet, aus welcher Datei die Einstellung gelesen werden sollen
        view.getStylesheets().add(getClass().getResource("/gui/css/style.css").toExternalForm());

        // ListViewLeft Listener
        view.getlistView().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null) {
                    auswahlPlaylist = newValue;
                }
            }
        });

        // ListViewRight Listener
        view.getlistViewRight().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Track>() {
            @Override
            public void changed(ObservableValue<? extends Track> observable, Track oldValue, Track newValue) {
                auswahlTrackPlayList = newValue;
            }
        });

        // Button Funktionen
        view.getLoadPZwei().setOnAction(e->loadPlayList(player));
        view.getChangeBZwei().setOnAction(e->application.switchScene("MP3Player"));
        view.getPBZwei().setOnAction(e-> playAndSwitch(application,player));

    }

    /**
     * Methode zeigt alles Songs aus einer gewählten PlayList an
     * @param player
     */
    private void loadPlayList(Mp3Player player) {
        view.getlistViewRight().getItems().clear();
        songsInPlaylist = playListManager.returnPlaylistSongs("Playlisten/" + auswahlPlaylist + ".m3u");
        player.setAktuellePlayList(songsInPlaylist);

        view.getlistViewRight().setCellFactory(new Callback<ListView<Track>, ListCell<Track>>() {
            @Override
            public ListCell<Track> call(ListView<Track> param) {
                return new CustomTrackCell();
            }
        });

        view.getlistViewRight().setItems(songsInPlaylist);
        view.getlistViewRight().refresh();
    }

    /**
     * Methode läd das gewählte Lied aus der Playlist in den Mp3Player und switcht danach die Scene
     * @param application
     * @param player
     */
    private void playAndSwitch(Main application, Mp3Player player) {
        try {
            if(playListManager.getOneSongFromList(auswahlTrackPlayList.getFileName()) != null) {
                player.playSelected(playListManager.getOneSongFromList(auswahlTrackPlayList.getFileName()));
                application.switchScene("MP3Player");
            }
        } catch(NullPointerException e) {
            System.out.println("Wählen Sie ein Lied aus!");
        }
    }

    /**
     * Methode gibt die view zurück
     * @return
     */
    public Pane getView() { return view; }
}
