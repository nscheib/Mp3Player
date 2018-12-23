package presentation;

import business.Mp3Player;
import business.Playlist;
import business.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import business.PlaylistManager;
import javafx.scene.text.Text;

public class PlaylistEditorView extends BorderPane {

    private VBox topVBox,topVBox2, centerVBoxZwei, centerVBoxZwei2;
    private HBox buttonHbox, topHBox, centerHBoxZwei;
    private PlaylistManager playlistVerwalter;
    private Label playlist, songs;

    Button add, delete, loadPlaylist, add2,delete2,play;
    Button changeButton, changeButtonPlay, changeButtonZwei;
    ObservableList<String> inhaltListView ;
    ObservableList<Playlist> inhaltListViewR;
    ListView<String> listView;
    ListView<Track> listViewR;


    public PlaylistEditorView(PlaylistManager playlistVerwalter){
        this.playlistVerwalter = playlistVerwalter;
        guiElemente();
        playlistVerwalter.loadPlaylists("nichtstun");

        for (int i = 0 ;i < playlistVerwalter.getPlaylists().size();i++){
            inhaltListView.add(playlistVerwalter.getPlaylists().get(i).getTitle());
        }

        listView.getItems().addAll(inhaltListView);

        // Alignment
        topVBox2.setAlignment(Pos.TOP_LEFT);
        centerVBoxZwei.setAlignment(Pos.CENTER);
        centerVBoxZwei2.setAlignment(Pos.CENTER);
        centerHBoxZwei.setAlignment(Pos.CENTER);
        buttonHbox.setAlignment(Pos.BOTTOM_CENTER);

        //StyleClasses
        topHBox.getStyleClass().add("topHBox");
        topVBox.getStyleClass().add("topVBox");
        topVBox2.getStyleClass().add("topVBox2");
        buttonHbox.getStyleClass().add("hbox");

        delete.getStyleClass().add("delete");
        loadPlaylist.getStyleClass().add("loadPlaylist");
        changeButton.getStyleClass().add("changeButton");
        changeButtonPlay.getStyleClass().add("changeButtonPlay");
        changeButtonZwei.getStyleClass().add("changeButtonZwei");
        play.getStyleClass().add("playbutton");

        // Children
        topHBox.getChildren().addAll(changeButton, changeButtonPlay, changeButtonZwei);
        topVBox.getChildren().addAll(topHBox);
        centerVBoxZwei.getChildren().addAll(playlist, listView, delete, loadPlaylist);
        centerVBoxZwei2.getChildren().addAll(songs, listViewR, play);
        centerHBoxZwei.getChildren().addAll(centerVBoxZwei, centerVBoxZwei2);


        buttonHbox.setPadding(new Insets(10));


        // Background Scene
        this.setTop(topVBox);
        this.setCenter(centerHBoxZwei);
        this.setBottom(buttonHbox);
        this.getStyleClass().add("ground");
        this.setMinSize(500,800);
        this.setMaxSize(700,900);
    }



    public void guiElemente(){
        topHBox = new HBox();
        topVBox = new VBox();
        topVBox2 = new VBox(20);

        centerVBoxZwei = new VBox(10);
        centerVBoxZwei.setPadding(new Insets(10,10,10,10));
        centerVBoxZwei2 = new VBox(10);
        centerVBoxZwei2.setPadding(new Insets(10,10,10,10));
        centerHBoxZwei = new HBox();
        buttonHbox = new HBox();
        add = new Button("Add New");
        delete = new Button("Delete");
        play = new Button("Play");
        add2 = new Button("Add New");
        delete2 = new Button("Delete");
        changeButton = new Button();
        changeButtonPlay = new Button();
        changeButtonZwei = new Button();
        loadPlaylist = new Button("Load Playlist");

        playlist = new Label("Playlisten");
        songs = new Label("Songs");

        listView = new ListView<>();
        listViewR = new ListView<>();
        inhaltListView = FXCollections.observableArrayList();
        inhaltListViewR = FXCollections.observableArrayList();
    }
}