package gui.playerview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.PlaylistVerwalter;

public class PlaylistEditorView extends BorderPane {


    private VBox topVBox2, centerVBoxZwei, centerVBoxZwei2;

    private Button addPlaylist,deletePlaylist;

    //VBox playlistWahl;
    Button add, delete, loadPlaylist, add2,delete2;
    private HBox centerHBoxZwei, buttonHBox2;

    Button changeWindow2;
    private PlaylistVerwalter playlistVerwalter;



    ListView<String> listView,listViewR;                // kann das public bleiben oder ist das kacke?



    public PlaylistEditorView(PlaylistVerwalter playlistVerwalter){


        //___for Scene 2__________

        this.playlistVerwalter = playlistVerwalter;

        guiElemente();




        playlistVerwalter.loadFromFile("nichtstun"); // nichtstun siehe methode...
        // speichert playlisten in liste ...

        for (int i = 0; i < playlistVerwalter.getPlaylists().size(); i++){

            listView.getItems().add(playlistVerwalter.getPlaylists().get(i)); // aus liste

        }
        //listViewListe.add(playlistVerwalter.getPlaylists().get(i)); // aus liste

        //listView.getItems().addAll("Peeeeda", "ur mum gwy", "ASS","Surrogates"); // hier dann die playlistnamen
        // hier werden nur STRINGS eingespeichert in listview


        listViewR.getItems().addAll( playlistVerwalter.getAllSongs()); // aus availableSongs







        //listViewR.getItems().addAll("Song1","Song2","Song3");

        //listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Top
        topVBox2.getChildren().addAll(changeWindow2);
        changeWindow2.getStyleClass().add("changeWindow2");

        topVBox2.setAlignment(Pos.TOP_LEFT);
        topVBox2.getStyleClass().add("topVBox2");

        // Center
        centerVBoxZwei.getChildren().addAll(listView,add,delete);
        centerVBoxZwei.setAlignment(Pos.CENTER);
        centerVBoxZwei2.getChildren().addAll(listViewR,add2,delete2);
        centerVBoxZwei2.setAlignment(Pos.CENTER);
        centerHBoxZwei.setAlignment(Pos.CENTER);
        centerHBoxZwei.getChildren().addAll(centerVBoxZwei,loadPlaylist,centerVBoxZwei2);


//        besser das hier noch dazu oder buttons so lassen wie sie sind ??

//        buttonHBox2.getChildren().addAll(choose);
//        buttonHBox2.getStyleClass().add("hbox2");
//        buttonHBox2.setAlignment(Pos.CENTER);

        // Background Scene
        this.setTop(topVBox2);
        this.setCenter(centerHBoxZwei);
//        ground2.setBottom(buttonHBox2);
        this.getStyleClass().add("ground2");
        this.setMaxSize(600,600);


        // create scene with width and height


        //this.setCenter(new Label("Hallo WoScene"));

    }


    public void guiElemente(){


        topVBox2 = new VBox(20);


        changeWindow2 = new Button("Go to Scene 1");






        centerVBoxZwei = new VBox(10);
        centerVBoxZwei.setPadding(new Insets(10,10,10,10));
        centerVBoxZwei2 = new VBox(10);
        centerVBoxZwei2.setPadding(new Insets(10,10,10,10));
        centerHBoxZwei = new HBox();

        add = new Button("Add New");
        delete = new Button("Delete");
        add2 = new Button("Add New");
        delete2 = new Button("Delete");
        changeWindow2 = new Button("Player");
        loadPlaylist = new Button("Load Playlist");


        listView = new ListView<>();
        listViewR = new ListView<>();






    }

}
