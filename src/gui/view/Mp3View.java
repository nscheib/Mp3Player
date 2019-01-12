package gui.view;

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
    private Button changeButton, changeButtonZwei, changeButtonPlay, stop, play, skipleft, skipright;
    private Slider timeslider, volumeslider;

    private VBox topVBox, centerVBox, buttonVBox;
    private HBox topHBox, centerHBox, centerZweiHBox, buttonHBox, buttonHBoxZwei, centerPicHBox;
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

        changeButton.getStyleClass().add("changeB");
        changeButtonPlay.getStyleClass().add("changeBPlay");
        changeButtonZwei.getStyleClass().add("changeBSettings");
        play.getStyleClass().add("playButton");
        stop.getStyleClass().add("stopbutton");
        skipleft.getStyleClass().add("skipleft");
        skipright.getStyleClass().add("skipright");
        loop.getStyleClass().add("loop");
        //shuffle.getStyleClass().add("shuffle");
        //repeat.getStyleClass().add("repeat");


        // Volumeslider
        volumeslider.setValue(50);


        // Timeslider
        timeslider.setShowTickLabels(true);


        //Picture
        imgview.setImage(img);
        imgview.setFitWidth(350);
        imgview.setFitHeight(350);


        // Children
        topHBox.getChildren().addAll(changeButton, changeButtonPlay, changeButtonZwei);
        centerVBox.getChildren().addAll(title, interpret);
        centerZweiHBox.getChildren().addAll(time, timeslider, timeZwei);
        centerPicHBox.getChildren().addAll(imgview);
        buttonHBox.getChildren().addAll(skipleft, stop, play, skipright, loop);
        buttonHBoxZwei.getChildren().addAll(vol, volumeslider);

        topVBox.getChildren().addAll(topHBox);
        centerVBox.getChildren().addAll(centerHBox, centerPicHBox, centerZweiHBox);
        buttonVBox.getChildren().addAll(buttonHBox, buttonHBoxZwei);


        // Scene
        this.setTop(topVBox);
        this.setCenter(centerVBox);
        this.setBottom(buttonVBox);
        this.setMinSize(400,600);
        this.setMaxSize(500,800);
        this.getStyleClass().add("background");
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
        centerHBox = new HBox();
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
        changeButton = new Button();
        changeButtonZwei = new Button();
        changeButtonPlay = new Button();
        play = new Button();
        stop = new Button();
        skipleft = new Button();
        skipright = new Button();
        loop = new ToggleButton();
        //shuffle = new ToggleButton();
        //repeat = new ToggleButton();
    }

    // Gibt die Buttons zurück
    public Button getChangeWindowButton() { return changeButton; }
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
