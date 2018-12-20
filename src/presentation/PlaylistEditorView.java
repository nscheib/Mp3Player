package presentation;

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
    private VBox topVBox2, centerVBoxZwei, centerVBoxZwei2;
    private Button addPlaylist,deletePlaylist;
    private HBox buttonHbox;
    private HBox centerHBoxZwei;
    private PlaylistManager playlistVerwalter;
    Button add, delete, loadPlaylist, add2,delete2,play;
    Button changeWindow2;
    ObservableList<String> inhaltListView ;
    ObservableList<Playlist> inhaltListViewR;
    ListView<String> listView;                // kann das public bleiben oder ist das kacke?
    ListView<Track> listViewR;
    Mp3ControllerView view = new Mp3ControllerView();
    //Cells
    private Label titleLable,artistLabel;

    public PlaylistEditorView(PlaylistManager playlistVerwalter){
        this.playlistVerwalter = playlistVerwalter;
        guiElemente();
        playlistVerwalter.loadPlaylists("nichtstun"); // nichtstun siehe methode...
        // speichert playlisten in liste ...

        //Laden aller bis jetzt erstellten Playlists in Observable
        for (int i = 0 ;i < playlistVerwalter.getPlaylists().size();i++){
            inhaltListView.add(playlistVerwalter.getPlaylists().get(i).getTitle());
        }
//        inhaltListView.addAll(playlistVerwalter.getPlaylists());

//        for (int i = 0; i < inhaltListView.size();i++){
//            listView.getItems().add(inhaltListView.get(i));
//        }
        listView.getItems().addAll(inhaltListView);     // Alle playlists und dann auch immer aktuell nice so geregelt...

        //listViewR.getItems().addAll("Song1","Song2","Song3");
        //listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Top
        topVBox2.getChildren().addAll(changeWindow2);
        changeWindow2.getStyleClass().add("changeWindow2");

        topVBox2.setAlignment(Pos.TOP_LEFT);
        topVBox2.getStyleClass().add("topVBox2");

        // Center
        centerVBoxZwei.getChildren().addAll(listView,delete,loadPlaylist);
        centerVBoxZwei.setAlignment(Pos.CENTER);
        centerVBoxZwei2.getChildren().addAll(listViewR,play);
        centerVBoxZwei2.setAlignment(Pos.CENTER);
        centerHBoxZwei.setAlignment(Pos.CENTER);
        //centerHBoxZwei.getChildren().addAll(centerVBoxZwei,centerVBoxZwei2);

        // Buttonleiste
        view.shuffle.getStyleClass().add("shuffle");
        view.skipleft.getStyleClass().add("skipleft");
        view.play.getStyleClass().add("play");
        view.stop.getStyleClass().add("stop");
        view.skipright.getStyleClass().add("skipright");
        view.repeat.getStyleClass().add("repeat");
        buttonHbox.getChildren().addAll(view.shuffle, view.skipleft, view.play, view.stop, view.skipright, view.repeat);
        buttonHbox.setAlignment(Pos.BOTTOM_CENTER);
        buttonHbox.getStyleClass().add("hbox");
        buttonHbox.setPadding(new Insets(10));
        centerHBoxZwei.getChildren().addAll(centerVBoxZwei,centerVBoxZwei2);

        // Background Scene
        this.setTop(topVBox2);
        this.setCenter(centerHBoxZwei);
        this.setBottom(buttonHbox);
        this.getStyleClass().add("ground2");
        this.setMaxSize(600,600);
    }



    public void guiElemente(){
        topVBox2 = new VBox(20);
        changeWindow2 = new Button("Go to Scene 1");
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
        changeWindow2 = new Button("Player");
        loadPlaylist = new Button("Load Playlist");
        listView = new ListView<>();
        listViewR = new ListView<>();
        inhaltListView = FXCollections.observableArrayList();
        inhaltListViewR = FXCollections.observableArrayList();
    }
}