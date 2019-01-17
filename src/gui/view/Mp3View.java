package gui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * Klasse Mp3View enthält alle Gui-Elemente der Mp3Player-Funktionen
 */
public class Mp3View extends BorderPane {

    private ToggleButton loop, shuffle, repeat ;
    private Button mp3playerButton, playListButton, gameButton, settingButton, stop, play, skipleft, skipright;
    private Slider timeslider, volumeslider;

    private VBox topVBox, centerVBox, buttonVBox;
    private HBox topHBox, centerZweiHBox, buttonHBox, buttonHBoxZwei, centerPicHBox;
    private Label title, interpret, time, timeZwei, vol;

    private ImageView imgview;
    private Image img; // !nicht löschen! //

    // Constructor
    public Mp3View() {

        initGuiElements();
        loadButtons();
        loadSongItems();


        // Alignment
        topVBox.setAlignment(Pos.CENTER);
        centerVBox.setAlignment(Pos.CENTER);
        centerPicHBox.setAlignment(Pos.CENTER);
        centerZweiHBox.setAlignment(Pos.CENTER);
        buttonVBox.setAlignment(Pos.CENTER);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBoxZwei.setAlignment(Pos.CENTER);


        //StyleClasses
        timeslider.getStyleClass().add("timeslider");
        volumeslider.getStyleClass().add("volumeslider");

        title.getStyleClass().add("text");
        interpret.getStyleClass().add("text");

        imgview.getStyleClass().add("imgview");

        mp3playerButton.getStyleClass().add("playerButton");
        playListButton.getStyleClass().add("playListButton");
        gameButton.getStyleClass().add("gameButton");
        settingButton.getStyleClass().add("settingButton");
        play.getStyleClass().add("playButton");
        stop.getStyleClass().add("stopbutton");
        skipleft.getStyleClass().add("skipleft");
        skipright.getStyleClass().add("skipright");
        loop.getStyleClass().add("loop");
        //shuffle.getStyleClass().add("shuffle");
        //repeat.getStyleClass().add("repeat");


        // Volumeslider
        volumeslider.setValue(50);


        //Picture
        imgview.setImage(img);
        imgview.setFitWidth(350);
        imgview.setFitHeight(350);


        //Abstände
        topHBox.setPadding(new Insets(10,10,10,10));


        // Children
        topHBox.getChildren().addAll(mp3playerButton, playListButton, gameButton, settingButton);
        //topVBox.getChildren().addAll(topHBox);
        centerVBox.getChildren().addAll(title, interpret, centerPicHBox, centerZweiHBox);
        centerZweiHBox.getChildren().addAll(time, timeslider, timeZwei);
        centerPicHBox.getChildren().addAll(imgview);
        buttonHBox.getChildren().addAll(skipleft, stop, play, skipright, loop);
        buttonHBoxZwei.getChildren().addAll(vol, volumeslider);
        buttonVBox.getChildren().addAll(buttonHBox, buttonHBoxZwei);


        // Scene
        this.setTop(topHBox);
        this.setCenter(centerVBox);
        this.setBottom(buttonVBox);
        this.getStyleClass().add("background");
        this.setMinSize(500, 700);
        this.setMaxSize(700, 700);
    }

    /**
     * Methode instanziiert die Boxen uns Slider der Gui
     */
    public void initGuiElements() {

        // Top
        topVBox = new VBox();
        topHBox = new HBox();

        // Center
        centerVBox = new VBox();
        centerZweiHBox = new HBox();
        centerPicHBox = new HBox();

        // Down
        buttonVBox = new VBox();
        buttonHBox = new HBox();
        buttonHBoxZwei = new HBox();


        // Slider
        timeslider = new Slider();
        volumeslider = new Slider();
    }

    /**
     * Methode instanziiert die Label und das Coverbild
     */
    public void loadSongItems() {

        //Label
        title = new Label();
        interpret = new Label();
        time = new Label("00:00");
        timeZwei = new Label();
        vol = new Label("Vol:");

        //Picture
        imgview = new ImageView(img);
    }

    /**
     * Methode instanziiert die Buttons
     */
    private void loadButtons() {
        mp3playerButton = new Button();
        playListButton = new Button();
        gameButton = new Button();
        settingButton = new Button();
        play = new Button();
        stop = new Button();
        skipleft = new Button();
        skipright = new Button();
        loop = new ToggleButton();
        //shuffle = new ToggleButton();
        //repeat = new ToggleButton();
    }

    // Gibt die Buttons zurück
    public Button getMp3PlayerButton() { return mp3playerButton; }
    public Button getPlayListButton() { return playListButton; }
    public Button getGameButton() { return gameButton; }
    public Button getSettingButton() { return settingButton; }
    public Button getStopButton() { return stop; }
    public Button getSkipleft() { return skipleft; }
    public Button getSkipright() { return skipright; }
    public Button getPlayButton() { return play; }
    public ToggleButton getLoop() { return loop; }
    //public ToggleButton getShuffle() { return shuffle; }
    //public ToggleButton getRepeat() { return repeat; }

    // Gibt die Slider zurück
    public Slider getTimeslider() { return timeslider; }
    public Slider getVolumeslider() { return volumeslider; }

    // Gibt die Label zurück
    public Label getTime() { return time; }
    public Label getTimeZwei() { return timeZwei; }
    public Label getTitle() { return title; }
    public Label getInterpret() { return interpret; }

    // Gibt die ImageView zurück
    public ImageView getImgview() { return imgview; }

}
