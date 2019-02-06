package gui.PlayListen;

import businessLogic.Main;
import businessLogic.Mp3Player;
import businessLogic.PlayListManager;
import businessLogic.Track;
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

        view = new PlayListView();
        this.spaltenViewRight = view.getSpaltenViewRight();

        for(int i = 0; i < playListManager.getAllPlayLists().size(); i++) {
            String[] tempname = playListManager.getAllPlayLists().get(i).split("[/.]");
            view.getItemList().add(tempname[1]);
        }
        view.getSpaltenViewLeft().getItems().addAll(view.getItemList());

        // Stylesheet, aus welcher Datei die Einstellung gelesen werden sollen
        view.getStylesheets().addAll(
                getClass().getResource("/gui/css/style.css").toExternalForm(),
                getClass().getResource("/gui/PlayListen/playlisten.css").toExternalForm()
        );

        // Button Funktionen
        view.getLoadPlayList().setOnAction(e->loadPlayList(player));
        view.getMp3playerButton().setOnAction(e -> application.switchScene("MP3Player"));
        view.getplaylistButton().setOnAction(e -> application.switchScene("Playlist"));
        view.getgameButton().setOnAction(e -> application.switchScene("Game"));
        //Game.getsettingButton().setOnAction(e->application.switchScene("Setting"));
        view.getPlayButton().setOnAction(e -> playAndSwitch(application,player));
        view.getDelete().setOnAction(e -> playlistDelete());


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

    }

    private void playlistDelete() {
        if(playListManager.deletePlayList(auswahlPlaylist)) {
            //refresh screen
        }
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
     * Methode gibt die Game zurück
     * @return
     */
    public Pane getView() { return view; }
}
