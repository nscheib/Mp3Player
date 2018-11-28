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

    //_______________________________________________HANS SHIT
    private VBox topVBox2, centerVBox2, layout1;
    private Stage window;
    private Scene scene1,scene2;
    private Button changeWindow, changeWindow2;
    private BorderPane ground2;
    //_______________________________________________HANS SHIT

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

        // Change Scene ___________HANS
        changeWindow = new Button("Go to Scene 2");
        changeWindow.setOnAction(e ->window.setScene(scene2));

        changeWindow2 = new Button("Go to Scene 1");
        changeWindow.setOnAction(e->window.setScene(scene1));

        Label label1 = new Label("Fenster2");
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, changeWindow2);

        // Top
        topVBox.getChildren().addAll(title, interpret, information, changeWindow); //______________________ HANS SHIT
        topVBox.setAlignment(Pos.CENTER);





        // Center
        centerHBox.setAlignment(Pos.CENTER);
        centerHBox.getChildren().addAll(information,timeslider);
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(centerHBox);

        // Bottom and Button
        //_______________________________________________HANS SHIT
        stop.getStyleClass().add("stopbutton");
//            stop.setOnAction(e -> window.setScene(scene2));

//        changeWindow.getStyleClass().add("changewindow");
//            changeWindow.setOnAction(e-> window.setScene(scene2));
        //_______________________________________________HANS SHIT





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


        layout1.setTop(topVBox);
        ground.setCenter(centerVBox);
        ground.setBottom(buttonHBox);
        ground.getStyleClass().add("ground");
        ground.setMaxSize(600,600);
        // create scene with width and height


        Scene scene1 = new Scene(ground, 500,500);
        scene1.getStylesheets().add(getClass().getResource("style.css").toExternalForm());



        Scene scene2 = new Scene(layout1, 500,500);
        scene2.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());


//        scene2.getStylesheets().add(getClass().getResource("style.css").toExternalForm());








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

        topVBox2 = new VBox();
        centerVBox2 = new VBox();

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
