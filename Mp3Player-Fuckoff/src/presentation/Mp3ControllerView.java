package presentation;

import business.Mp3Player;
import business.PlaylistManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Mp3ControllerView extends BorderPane {

    private PlaylistManager verwalter;
    private Label interpret, title, information;

    private VBox topVBox, centerVBox;
    private VBox topVBox2, centerVBoxZwei;

    private HBox buttonHBox, centerHBox;
    private HBox centerHBoxZwei, buttonHBox2;

    Slider timeslider;
    private double value = 0;
    Slider volumeslider;
    private double value2 = 0;

    private final DateFormat TIMEFORMAT = new SimpleDateFormat(("mm:ss"));

    Button stop, skipleft, skipright, shuffle, repeat;
    Button changeWindow, changeWindow2, choose;
    ToggleButton play;                                                      // kann mit einer Aktion belegt werden Play/Pause

    Mp3Player Mp3player;

    // Alle Songs anzeigen und auswählen
    ObservableList<String> inhaltAllSongs;
    ListView<String> allSongsList ;

    // Hier noch ohne Observable List alles geklärt
    // Man könnte in playlistverwalter die playlist durch observable ersetzen..

    PlaylistManager playlistManager = new PlaylistManager();
    Button playSong;

    private void setInformation(Integer position) {
        timeslider.setMax(/*player.getMP3Property().getValue().getLenghtInSecounds()*/ 100);
        timeslider.valueProperty().setValue(position/1000);
        information.setText(TIMEFORMAT.format(position));
    }

    public Mp3ControllerView() {
        //alle Elemente laden
        guiElemente();

        verwalter = new PlaylistManager();

        // Textlabel - top for title
        title = new Label("Titel: " + Mp3Controller.getMp3Player().getData().title());
        title.getStyleClass().add("text");


        // Textlabel - top for interpret
        interpret = new Label("Interpret: " + Mp3Controller.getMp3Player().getData().author());
        interpret.getStyleClass().add("text");

        // Textlabel - middel for running time
        information = new Label("00:00");
        information.getStyleClass().add("time");

        // Slider for music
        timeslider.getStyleClass().add("timeslider");
        timeslider.setMin(0);
        timeslider.setMax(100);
        timeslider.setShowTickLabels(false);  // hier später true um die länge des songs anzeigen zu lassen...

        //Lautstaerkeregler
        volumeslider.getStyleClass().add("volumeslider");
        volumeslider.setOrientation(Orientation.VERTICAL);


        // AllsongsList


        for (int i = 0; i < playlistManager.getAllSongs().size() ; i++){
            inhaltAllSongs.add(playlistManager.getAllSongs().get(i));
        }

        // hier nicht mehr daten in allsongslist laden, sondern die observable list

        // allSongsList.getItems().addAll(playlistVerwalter.getAllSongs());
        allSongsList = new ListView<String>(inhaltAllSongs);
        allSongsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        allSongsList.setMaxSize(400,100);
        allSongsList.getStyleClass().add("allSongsList");

        //playSong

        // Top
        topVBox.getChildren().addAll(changeWindow, allSongsList, playSong, title, interpret );
        changeWindow.getStyleClass().add("changeWindow");
        topVBox.setAlignment(Pos.CENTER_LEFT);

        // Center
        centerHBox.setAlignment(Pos.CENTER);
        centerHBox.getChildren().addAll(information,timeslider);
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(centerHBox);

        //Bottom Box mit Buttons
        play.getStyleClass().add("playbutton");
        stop.getStyleClass().add("stopbutton");
        skipleft.getStyleClass().add("skipleft");
        skipright.getStyleClass().add("skipright");
        shuffle.getStyleClass().add("shuffle");
        repeat.getStyleClass().add("repeat");
        buttonHBox.getChildren().addAll(shuffle, skipleft, stop, play, skipright, repeat, volumeslider);

        buttonHBox.getStyleClass().add("hbox");
        buttonHBox.setPadding(new Insets(10));
        buttonHBox.setAlignment(Pos.BOTTOM_CENTER);

        // Background Scene
        this.setTop(topVBox);
        this.setCenter(centerVBox);
        this.setBottom(buttonHBox);
        this.getStyleClass().add("ground");
        this.setMaxSize(600,600);
    }


    public void guiElemente(){

        topVBox = new VBox(20);
        topVBox2 = new VBox(20);

        changeWindow = new Button("Go to Scene 2");

        centerVBoxZwei = new VBox();
        centerHBoxZwei = new HBox();
        buttonHBox2 = new HBox();

        centerVBox = new VBox();
        buttonHBox = new HBox();
        centerHBox = new HBox();

        timeslider = new Slider(0,100, value);
        volumeslider = new Slider(0,100,value2);

        choose = new Button();
        stop = new Button();
        play = new ToggleButton();
        skipleft = new Button();
        skipright = new Button();
        shuffle = new Button();
        repeat = new Button();
        changeWindow2 = new Button();

        //        allSongsList = new ListView<String>(inhaltAllSongs);
        inhaltAllSongs = FXCollections.observableArrayList();
        playSong = new Button("Play Song");
    }
}
