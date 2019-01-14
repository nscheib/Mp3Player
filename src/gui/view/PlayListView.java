package gui.view;

import businessLogic.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayListView extends BorderPane {

    private VBox centerVBoxZwei, centerVBoxZwei2;
    private HBox topHBox, centerHBoxZwei, buttonHbox;

    private Button mp3playerButton, gameButton, settingButton, delete,
            playListButton, play, loadPlaylist;

    private Label playlist, songs;

    private ObservableList<String> itemList ;
    private ListView<String> spaltenViewLeft;
    private ListView<Track> spaltenViewRight;

    // Constructor
    public PlayListView() {

        guiElemente();

        // Alignment
        centerVBoxZwei.setAlignment(Pos.CENTER);
        centerVBoxZwei2.setAlignment(Pos.CENTER);
        centerHBoxZwei.setAlignment(Pos.CENTER);
        buttonHbox.setAlignment(Pos.BOTTOM_CENTER);


        //StyleClasses
        mp3playerButton.getStyleClass().add("playerButton");
        playListButton.getStyleClass().add("playListButton");
        gameButton.getStyleClass().add("gameButton");
        settingButton.getStyleClass().add("settingButton");
        play.getStyleClass().add("playlistPlayButton");
        loadPlaylist.getStyleClass().add("loadPlaylist");
        delete.getStyleClass().add("delete");


        //Abstände
        centerVBoxZwei.setPadding(new Insets(10,10,10,10));
        centerVBoxZwei2.setPadding(new Insets(10,10,10,10));
        topHBox.setPadding(new Insets(10,10,10,10));


        // Children
        topHBox.getChildren().addAll(mp3playerButton, playListButton, gameButton, settingButton);
        centerVBoxZwei.getChildren().addAll(playlist, spaltenViewLeft, delete, loadPlaylist);
        centerVBoxZwei2.getChildren().addAll(songs, spaltenViewRight, play);
        centerHBoxZwei.getChildren().addAll(centerVBoxZwei, centerVBoxZwei2);

        buttonHbox.setPadding(new Insets(10));

        // Scene
        this.setTop(topHBox);
        this.setCenter(centerHBoxZwei);
        this.setBottom(buttonHbox);
        this.setMinSize(500,800);
        this.setMaxSize(700,900);
        this.getStyleClass().add("background");

    }

    /**
     * Methode instanziiert alle Gui-Elemente
     */
    public void guiElemente(){

        // Top
        topHBox = new HBox();

        // Center
        centerVBoxZwei = new VBox();
        centerVBoxZwei2 = new VBox();
        centerHBoxZwei = new HBox();

        // Down
        buttonHbox = new HBox();

        // Label
        playlist = new Label("Playlisten");
        songs = new Label("Songs");

        // Buttons
        mp3playerButton = new Button();
        playListButton = new Button();
        gameButton = new Button();
        settingButton = new Button();
        play = new Button();
        delete = new Button("Delete");
        loadPlaylist = new Button("Load Playlist");

        spaltenViewLeft = new ListView<>();
        spaltenViewRight = new ListView<>();

        itemList = FXCollections.observableArrayList();
    }

    // Gibt die einzelnen Buttons zurück
    public Button getMp3playerButton() { return mp3playerButton; }
    public Button getplaylistButton() { return playListButton; }
    public Button getgameButton() { return gameButton; }
    public Button getsettingsButton() { return settingButton; }
    public Button getPlayButton() { return play; }
    public Button getLoadPlayList() { return loadPlaylist; }

    // Gibt die ListViews zurück
    public ListView<String> getlistView() { return spaltenViewLeft; }
    public ListView<Track> getlistViewRight() { return spaltenViewRight; }
    public ListView<Track> getSpaltenViewRight() { return spaltenViewRight; }
    public ObservableList<String> getItemList() { return itemList; }
    public ListView<String> getSpaltenViewLeft() { return spaltenViewLeft; }

}
