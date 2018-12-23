package presentation;

import business.Mp3Player;
import business.PlaylistManager;
import business.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Mp3ControllerView extends BorderPane {

    private PlaylistManager verwalter;
    private ToggleButton changeButton, changeButtonPlay, changeButtonZwei, stop, play, skipleft, skipright, shuffle, repeat;
    private Slider timeslider, volumeslider;

    private VBox topVBox, centerVBox, buttonVBox;
    private HBox topHBox, centerHBox, centerZweiHBox, buttonHBox, buttonHBoxZwei, centerPicHBox;
    private Label title, interpret, time, vol;


    private Image img;
    private ImageView imgview;

    private final DateFormat TIMEFORMAT = new SimpleDateFormat(("mm:ss"));




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
        time = new Label("00:00");
        time.getStyleClass().add("time");

        // Slider for music
        timeslider.getStyleClass().add("timeslider");
        timeslider.setMin(0);
        timeslider.setMax(100);
        timeslider.setShowTickLabels(false);  // hier später true um die länge des songs anzeigen zu lassen...

        //Lautstaerkeregler
        volumeslider.getStyleClass().add("volumeslider");
        //volumeslider.setOrientation(Orientation.VERTICAL);
        volumeslider.setValue(50);

        // Image
        imgview.setImage(img);
        imgview.setFitHeight(100);
        imgview.setFitWidth(100);

        // Alignment
        topVBox.setAlignment(Pos.CENTER);
        centerVBox.setAlignment(Pos.CENTER);
        centerHBox.setAlignment(Pos.CENTER);
        centerPicHBox.setAlignment(Pos.CENTER);
        centerZweiHBox.setAlignment(Pos.CENTER);
        buttonVBox.setAlignment(Pos.CENTER);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBoxZwei.setAlignment(Pos.CENTER);

        //StyleClasses
        topVBox.getStyleClass().add("topVBox");
        topHBox.getStyleClass().add("topHBox");
        centerVBox.getStyleClass().add("centerVBox");
        centerHBox.getStyleClass().add("centerHBox");
        centerZweiHBox.getStyleClass().add("centerZweiHBox");
        buttonVBox.getStyleClass().add("buttonVBox");
        buttonHBox.getStyleClass().add("buttonHBox");

        timeslider.getStyleClass().add("timeslider");
        volumeslider.getStyleClass().add("volumeslider");

        title.getStyleClass().add("text");
        interpret.getStyleClass().add("text");

        imgview.getStyleClass().add("imgview");

        changeButton.getStyleClass().add("changeButton");
        changeButtonPlay.getStyleClass().add("changeButtonPlay");
        changeButtonZwei.getStyleClass().add("changeButtonZwei");
        play.getStyleClass().add("playbutton");
        stop.getStyleClass().add("stopbutton");
        skipleft.getStyleClass().add("skipleft");
        skipright.getStyleClass().add("skipright");
        shuffle.getStyleClass().add("shufflebutton");
        repeat.getStyleClass().add("repeatbutton");


        // Volumeslider
        volumeslider.setValue(30);


        //Picture
        imgview.setImage(img);
        imgview.setFitWidth(200);
        imgview.setFitHeight(200);

        // Children
        topHBox.getChildren().addAll(changeButton, changeButtonPlay, changeButtonZwei);
        centerVBox.getChildren().addAll(title, interpret);
        centerZweiHBox.getChildren().addAll(time, timeslider);
        centerPicHBox.getChildren().addAll(imgview);
        buttonHBox.getChildren().addAll(shuffle, skipleft, stop, play, skipright, repeat, vol, volumeslider);
        buttonHBoxZwei.getChildren().addAll(vol, volumeslider);

        topVBox.getChildren().addAll(topHBox);
        centerVBox.getChildren().addAll(centerHBox, centerPicHBox, centerZweiHBox);
        buttonVBox.getChildren().addAll(buttonHBox, buttonHBoxZwei);


        //buttonHBox.setPadding(new Insets(10));
        //buttonHBox.setAlignment(Pos.BOTTOM_CENTER);

        // Background Scene
        this.setTop(topVBox);
        this.setCenter(centerVBox);
        this.setBottom(buttonVBox);
        this.getStyleClass().add("ground");
        this.setMinSize(500,700);
        this.setMaxSize(700,900);
    }


    public void guiElemente(){


        // Top
        topVBox = new VBox();
        topHBox = new HBox();

        // Center
        centerVBox = new VBox();
        centerHBox = new HBox();
        centerZweiHBox = new HBox();
        centerPicHBox = new HBox();

        // Down
        buttonVBox = new VBox();
        buttonHBox = new HBox();
        buttonHBoxZwei = new HBox();

        // Buttons
        changeButton = new ToggleButton();
        changeButtonPlay = new ToggleButton();
        changeButtonZwei = new ToggleButton();
        play = new ToggleButton();
        stop = new ToggleButton();
        skipleft = new ToggleButton();
        skipright = new ToggleButton();
        shuffle = new ToggleButton();
        repeat = new ToggleButton();

        // Text
        title = new Label();
        interpret = new Label();
        time = new Label("00:00");
        vol = new Label("Vol:");

        // Slider
        timeslider = new Slider();
        volumeslider = new Slider();

        // Picture
        imgview = new ImageView();
        img = new Image("images/song.png");

    }

    public ToggleButton getChangeWindowButton() {
        return changeButton;
    }

    public ToggleButton getSkipRightButton(){return skipright;}

    public ToggleButton getSkipLeftButton(){return skipleft;}

    public ToggleButton getShuffle(){return shuffle;}

    public ToggleButton getRepeat(){return repeat;}


    public ToggleButton getStopButton() {
        return stop;
    }

    public ToggleButton getPlayButton() {
        return play;
    }

    public Slider getTimeslider() {
        return timeslider;
    }

    public Slider getVolumeslider() {
        return volumeslider;
    }

    public void setTime(int time) {
        this.time.setText(TIMEFORMAT.format(time));
    }

    public ImageView getImgview() {
        return imgview;
    }
}
