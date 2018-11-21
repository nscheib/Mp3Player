package gui.playerview;

import businessLogic.Mp3Player;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class PlayerViewController extends Application {


    private Label interpret, title, information;
    private BorderPane ground;
    private VBox topVBox, centerVBox;
    private HBox buttonHBox, centerHBox;
    private Slider timeslider;
    private Button stop, skipleft, skipright;
    private ToggleButton play; // kann mit einer Aktion belegt werden Play/Pause
    private Image img;
    private ImageView imgview;
    private Mp3Player player;
    private final DateFormat TIMEFORMAT = new SimpleDateFormat(("mm:ss"));

    // zum testen
    private double value = 30;

    public PlayerViewController() {}


    public static void main(String [] args) { launch(args);}


    private void setInformation(Integer position) {
        timeslider.setMax(/*player.getMP3Property().getValue().getLenghtInSecounds()*/ 100);
        timeslider.valueProperty().setValue(position/1000);
        information.setText(TIMEFORMAT.format(position));
    }


    @Override
    public void start(Stage primaryStage) {

        //loading all elements
        guiElemente();

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
        timeslider.setShowTickLabels(false);

        // Top
        topVBox.getChildren().addAll(title, interpret, information);
        topVBox.setAlignment(Pos.CENTER);

        // Center
        centerHBox.setAlignment(Pos.CENTER);
        centerHBox.getChildren().addAll(information,timeslider);
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(centerHBox);

        // Bottom and Button
        stop.getStyleClass().add("stopbutton");
        play.getStyleClass().add("playbutton");
        skipleft.getStyleClass().add("skipleft");
        skipright.getStyleClass().add("skipright");
        buttonHBox.getChildren().addAll(skipleft, stop, play, skipright);
        buttonHBox.getStyleClass().add("hbox");
        buttonHBox.setAlignment(Pos.CENTER);

        // Background Scene
        ground.setTop(topVBox);
        ground.setCenter(centerVBox);
        ground.setBottom(buttonHBox);
        ground.getStyleClass().add("ground");
        ground.setMaxSize(600,600);

        // create scene with width and height
        Scene scene = new Scene(ground, 500,500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // set title from stage, load scene in stage, show stage
        primaryStage.setTitle("Mp3 Player");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void guiElemente(){

        ground = new BorderPane();
        topVBox = new VBox();
        centerVBox = new VBox();
        buttonHBox = new HBox();
        centerHBox = new HBox();
        timeslider = new Slider(0,100, value);
        stop = new Button();
        play = new ToggleButton();
        skipleft = new Button();
        skipright = new Button();

    }

}
