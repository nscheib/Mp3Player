package gui.view;

import businessLogic.PlayListManager;
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

    private VBox topVBox, topVBox2, centerVBoxZwei, centerVBoxZwei2;
    private HBox topHBox, centerHBoxZwei, buttonHbox;

    private Button changeBZwei, changeBSZwei, delete,
            changeBPZwei, playBZwei, loadPlaylist;

    private Label playlist, songs;

    //private PlayListManager playListManager;
    private ObservableList<String> itemList ;
    private ListView<String> spaltenViewLeft;
    private ListView<Track> spaltenViewRight;

    // Constructor
    public PlayListView(PlayListManager playListManager) {

        //this.playListManager = playListManager;
        guiElemente();

        for(int i = 0; i < playListManager.getAllPlayLists().size(); i++) {

            String[] tempname = playListManager.getAllPlayLists().get(i).split("[/.]");
            itemList.add(tempname[1]);
        }
        spaltenViewLeft.getItems().addAll(itemList);


        // Alignment
        topVBox2.setAlignment(Pos.TOP_LEFT);
        centerVBoxZwei.setAlignment(Pos.CENTER);
        centerVBoxZwei2.setAlignment(Pos.CENTER);
        centerHBoxZwei.setAlignment(Pos.CENTER);
        buttonHbox.setAlignment(Pos.BOTTOM_CENTER);


        //StyleClasses
        topHBox.getStyleClass().add("topHBoxSeiteZwei");
        topVBox.getStyleClass().add("topVBoxSeiteZwei");
        topVBox2.getStyleClass().add("topVBoxZweiSeiteZwei");
        buttonHbox.getStyleClass().add("hboxSeiteZwei");

        delete.getStyleClass().add("delete");
        loadPlaylist.getStyleClass().add("loadPlaylist");
        changeBZwei.getStyleClass().add("changeBSZwei");
        changeBPZwei.getStyleClass().add("changeBSZGame");
        changeBSZwei.getStyleClass().add("changeBSZSettings");
        playBZwei.getStyleClass().add("playBSZwei");


        //Abstände
        centerVBoxZwei.setPadding(new Insets(10,10,10,10));
        centerVBoxZwei2.setPadding(new Insets(10,10,10,10));


        // Children
        topHBox.getChildren().addAll(changeBZwei, changeBPZwei, changeBSZwei);
        topVBox.getChildren().addAll(topHBox);
        centerVBoxZwei.getChildren().addAll(playlist, spaltenViewLeft, delete, loadPlaylist);
        centerVBoxZwei2.getChildren().addAll(songs, spaltenViewRight, playBZwei);
        centerHBoxZwei.getChildren().addAll(centerVBoxZwei, centerVBoxZwei2);


        buttonHbox.setPadding(new Insets(10));

        // Scene
        this.setTop(topVBox);
        this.setCenter(centerHBoxZwei);
        this.setBottom(buttonHbox);
        this.getStyleClass().add("backgroundSeiteZwei");
        this.setMinSize(500,800);
        this.setMaxSize(700,900);

    }

    /**
     * Methode instanziiert alle Gui-Elemente
     */
    public void guiElemente(){

        // Top
        topHBox = new HBox();
        topVBox = new VBox();
        topVBox2 = new VBox(20);

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
        changeBZwei = new Button();
        changeBPZwei = new Button();
        changeBSZwei = new Button();
        playBZwei = new Button();
        delete = new Button("Delete");
        loadPlaylist = new Button("Load Playlist");

        spaltenViewLeft = new ListView<>();
        spaltenViewRight = new ListView<>();

        itemList = FXCollections.observableArrayList();
    }

    // Gibt die einzelnen Buttons zurück
    public Button getPBZwei() { return playBZwei; }
    public Button getChangeBZwei() { return changeBZwei; }
    public Button getLoadPZwei() { return loadPlaylist; }

    // Gibt die ListViews zurück
    public ListView<String> getlistView() { return spaltenViewLeft; }
    public ListView<Track> getlistViewRight() { return spaltenViewRight; }
    public ListView<Track> getSpaltenViewRight() { return spaltenViewRight; }

}
