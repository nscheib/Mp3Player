package gui.playerview;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sample.Mp3Player;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.PlaylistVerwalter;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Mp3ControllerView extends BorderPane {


    private PlaylistVerwalter verwalter;

    private Label interpret, title, information;
    private VBox topVBox, centerVBox;
    private HBox buttonHBox, centerHBox;
    Slider timeslider;  // war vorher private ... macht es
    Button stop;
    Button skipleft;
    Button skipright;
    Button changeWindow, changeWindow2, choose;
    ToggleButton play; // kann mit einer Aktion belegt werden Play/Pause
    private Image img;
    private ImageView imgview;
    private Mp3Player player;
    private final DateFormat TIMEFORMAT = new SimpleDateFormat(("mm:ss"));


    // Alle Songs anzeigen und auswählen
    ObservableList<String> inhaltAllSongs;
    ListView<String> allSongsList ;

    PlaylistVerwalter playlistVerwalter = new PlaylistVerwalter();
    Button playSong;

    // Hier noch ohne Observable List alles geklärt
    // Man könnte in playlistverwalter die playlist durch observable ersetzen...

    // zum testen
    private double value = 30;



    private void setInformation(Integer position) {
        timeslider.setMax(/*player.getMP3Property().getValue().getLenghtInSecounds()*/ 100);
        timeslider.valueProperty().setValue(position/1000);
        information.setText(TIMEFORMAT.format(position));
    }


    public Mp3ControllerView() {


        //loading all elements
        guiElemente();
        verwalter = new PlaylistVerwalter();
        /*InputStream input2 = clazz.getResourceAsStream("/org/o7planning/javafx/icon/java-48.png");
        Image image2 = new Image(input2, 100, 200, false, true);
        ImageView imageView2 = new ImageView(image2);*/

        // Textlabel - top for title
        title = new Label();
        title.getStyleClass().add("text");

        // Textlabel - top for interpret
        interpret = new Label();
        interpret.getStyleClass().add("text");

        // Textlabel - middel for running time
        information = new Label("00:00");
        information.getStyleClass().add("time");

        // Slider for music
        timeslider.getStyleClass().add("timeslider");
        timeslider.setMin(0);
        timeslider.setMax(100);
        timeslider.setShowTickLabels(false); // hier später true um die länge des songs anzeigen zu lassen...

        // AllsongsList


        for (int i = 0; i < playlistVerwalter.getAllSongs().size() ; i++){
            inhaltAllSongs.add(playlistVerwalter.getAllSongs().get(i));
        }

        // hier nicht mehr daten in allsongslist laden, sondern die observable list

        // allSongsList.getItems().addAll(playlistVerwalter.getAllSongs());
        allSongsList = new ListView<String>(inhaltAllSongs);
        allSongsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        allSongsList.setMaxSize(400,100);
        allSongsList.getStyleClass().add("allSongsList");

        //playSong

        // Top
        topVBox.getChildren().addAll(changeWindow, allSongsList,playSong, title, interpret );
        changeWindow.getStyleClass().add("changeWindow");

        topVBox.setAlignment(Pos.CENTER_LEFT);

        // Center
        centerHBox.setAlignment(Pos.CENTER);
        centerHBox.getChildren().addAll(information,timeslider);
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(centerHBox);


        play.getStyleClass().add("playbutton");
        stop.getStyleClass().add("stopbutton");
        skipleft.getStyleClass().add("skipleft");
        skipright.getStyleClass().add("skipright");
        buttonHBox.getChildren().addAll(skipleft, stop, play, skipright);

        buttonHBox.getStyleClass().add("hbox");
        buttonHBox.setAlignment(Pos.CENTER);

        // Background Scene
        this.setTop(topVBox);
        this.setCenter(centerVBox);
        this.setBottom(buttonHBox);
        this.getStyleClass().add("ground");
        this.setMaxSize(600,600);






    }

    public HBox getButtonHBox(){
        return buttonHBox;
    }


    public void guiElemente(){

        topVBox = new VBox(20);
        changeWindow = new Button("Go to Scene 2");
        centerVBox = new VBox();
        buttonHBox = new HBox();
        centerHBox = new HBox();
        timeslider = new Slider(0,100, value);
        choose = new Button();
        stop = new Button();
        play = new ToggleButton();
        skipleft = new Button();
        skipright = new Button();
        changeWindow = new Button();
        changeWindow2 = new Button();


//        allSongsList = new ListView<String>(inhaltAllSongs);
        inhaltAllSongs = FXCollections.observableArrayList();
        playSong = new Button("Play Song");



    }

}
