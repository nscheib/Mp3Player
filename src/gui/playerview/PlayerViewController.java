package gui.playerview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sample.Mp3Player;
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

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class PlayerViewController extends Application implements EventHandler<ActionEvent> {


    private Label interpret, title, information;
    private Label label1;
    private BorderPane ground;
    private BorderPane ground2;
    private VBox topVBox, centerVBox;
    private VBox topVBox2, centerVBoxZwei;
    private HBox buttonHBox, centerHBox;
    private HBox centerHBoxZwei, buttonHBox2;
    private Slider timeslider;
    private Button stop, skipleft, skipright;
    private Button changeWindow, changeWindow2, choose;
    private ToggleButton play; // kann mit einer Aktion belegt werden Play/Pause
    private Image img;
    private ImageView imgview;
    private Mp3Player player;
    private final DateFormat TIMEFORMAT = new SimpleDateFormat(("mm:ss"));

    private Stage window;
    private Scene scene1,scene2;


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

        window = primaryStage;


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
        topVBox.getChildren().addAll(changeWindow, title, interpret, information );
        changeWindow.setOnAction(e ->window.setScene(scene2));
        topVBox.setAlignment(Pos.CENTER);

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
        ground.setTop(topVBox);
        ground.setCenter(centerVBox);
        ground.setBottom(buttonHBox);
        ground.getStyleClass().add("ground");
        ground.setMaxSize(600,600);



        //___for Scene 2

        // Top
        topVBox2.getChildren().addAll(changeWindow2, title, interpret );
        changeWindow2.setOnAction(e->window.setScene(scene1));
        topVBox2.setAlignment(Pos.CENTER);

        // Center
        centerHBoxZwei.setAlignment(Pos.CENTER);
        centerHBoxZwei.getChildren().addAll(information,timeslider);
        centerVBoxZwei.setAlignment(Pos.CENTER);
        centerVBoxZwei.getChildren().addAll(centerHBox);


        choose.getStyleClass().add("choose");
        buttonHBox2.getChildren().addAll(choose);
        buttonHBox2.getStyleClass().add("hbox2");
        buttonHBox2.setAlignment(Pos.CENTER);

        // Background Scene
        ground2.setTop(topVBox2);
        ground2.setCenter(centerVBoxZwei);
        ground2.setBottom(buttonHBox2);
        ground2.getStyleClass().add("ground2");
        ground2.setMaxSize(600,600);



        // create scene with width and height
        scene1.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        scene2.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());





        // set title from stage, load scene in stage, show stage
        //_______________________________________________HANS SHIT
        window.setTitle("Mp3 Player");
        window.setScene(scene1);
        window.show();
        //_______________________________________________HANS SHIT

        // scene2



//        ground2.setTop(topVBox);
//        ground2.setCenter(centerVBox);
//        ground2.setBottom(buttonHBox);
//        ground2.getStyleClass().add("ground2");
//        ground2.setMaxSize(600,600);
//
//        Scene scene2 = new Scene(ground2, 500,500);
//        scene2.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());

//        changeWindow2.getStyleClass().add("changeWindow2");
//        changeWindow2.setOnAction(e-> window.setScene(scene1));
//
//        ground.setTop(topVBox);
//        ground.setCenter(centerVBox);
//        ground.setBottom(buttonHBox);
//        ground.getStyleClass().add("ground");
//        ground.setMaxSize(600,600);
//
//        topVBox2.getChildren().addAll(title, interpret, information);
//        topVBox2.setAlignment(Pos.CENTER);
//
//        ground2 = new BorderPane();
//
//        Scene scene2 = new Scene(ground2, 500,500);
//
//        scene2.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());

//        window.setTitle("Mp3 Player Seite 2");
//        window.setScene(scene1);
//        window.show();


    }

   @Override
   public void handle(ActionEvent event){

        // auch ersetzbar durch lambdas also
       // stop.getStyleClass().add("stopbutton");
       //            stop.setOnAction(this); mit übergabe in diese Funktion
       // zu stop.setOnAction(e -> System.out.println("TEST"));

        if (event.getSource() == stop ){

            System.out.println("HEY FASS MICH NICHT AN!");

        }

        if (event.getSource() == changeWindow){

            Scene scene = new Scene(ground, 500,500);
            scene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());


        }


   }


    public void guiElemente(){

        ground = new BorderPane();
        topVBox = new VBox();
        topVBox2 = new VBox(20);

        label1 = new Label("Fenster2");
        ground2 = new BorderPane();
        changeWindow2 = new Button("Go to Scene 1");
        changeWindow = new Button("Go to Scene 2");

        centerVBoxZwei = new VBox();
        centerHBoxZwei = new HBox();
        buttonHBox2 = new HBox();

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

        scene1 = new Scene(ground, 500,500);
        scene2 = new Scene(ground2, 500,500);

    }

}
