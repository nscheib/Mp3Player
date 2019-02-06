package gui.Game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GameView extends BorderPane {

    private Button playListButton, mp3playerButton, settingButton, gameButton, buttonleft;
    private VBox centerVBox, buttonVBox;
    private HBox topHBox;
    private Pane pane;
    private Label score,punkte;


    public GameView() {

        guiElemente();

        // Alignment
        buttonVBox.setAlignment(Pos.CENTER);


        //StyleClasses
        topHBox.getStyleClass().add("topHBox");
        buttonVBox.getStyleClass().add("buttonVBox");

        mp3playerButton.getStyleClass().add("playerButton");
        playListButton.getStyleClass().add("playListButton");
        gameButton.getStyleClass().add("gameButton");
        settingButton.getStyleClass().add("settingButton");
        pane.getStyleClass().add("pane");



        //Abstände
        topHBox.setPadding(new Insets(10,10,10,10));


        // Children
        topHBox.getChildren().addAll(mp3playerButton, playListButton, gameButton, settingButton, score,punkte);


        // Scene
        this.setTop(topHBox);
        this.setCenter(pane);
        //this.setBottom(buttonVBox);
        this.setMinSize(500,800);
        this.setMaxSize(700,900);
        this.getStyleClass().add("background");
    }


    /**
     * Methode instanziiert alle Gui-Elemente
     */
    private void guiElemente(){

        // Top
        topHBox = new HBox();
        score = new Label("Score: 0");
        punkte = new Label("Anzahl Punkte: 0");

        // Center
        centerVBox = new VBox();
        pane = new Pane();

        // Down
        buttonVBox = new VBox();

        // Buttons
        mp3playerButton = new Button();
        playListButton = new Button();
        gameButton = new Button();
        settingButton = new Button();
        buttonleft = new Button();
    }

    public Button getMp3playerButton() { return mp3playerButton; }
    public Button getplayListButton() { return playListButton; }
    public Button getgameButton() { return gameButton; }
    public Button getSettingsButton() { return settingButton; }

    public Pane getPane() {
        return pane;
    }

    public void setScore(int i) {
        score.setText("Score: "+i);
    }

    public void setPunkte(int i) {
        punkte.setText("Punkte: " + i);
    }
}
