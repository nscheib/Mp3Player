package gui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameView extends BorderPane {

    private Button playListButton, mp3playerButton, settingButton, gameButton, left, up, down, right;
    private VBox centerVBox, buttonVBox;
    private HBox topHBox;


    public GameView() {

        guiElemente();

        // Alignment
        buttonVBox.setAlignment(Pos.CENTER);


        //StyleClasses
        mp3playerButton.getStyleClass().add("playerButton");
        playListButton.getStyleClass().add("playListButton");
        gameButton.getStyleClass().add("gameButton");
        settingButton.getStyleClass().add("settingButton");
        left.getStyleClass().add("left");
        up.getStyleClass().add("up");
        down.getStyleClass().add("down");
        right.getStyleClass().add("right");


        //Abstände
        topHBox.setPadding(new Insets(10,10,10,10));


        // Children
        topHBox.getChildren().addAll(mp3playerButton, playListButton, gameButton, settingButton);
        buttonVBox.getChildren().addAll(left, up, down, right);


        // Scene
        this.setTop(topHBox);
        this.setCenter(centerVBox);
        this.setBottom(buttonVBox);
        this.setMinSize(500,800);
        this.setMaxSize(700,900);
        this.getStyleClass().add("background");
    }


    /**
     * Methode instanziiert alle Gui-Elemente
     */
    public void guiElemente(){

        // Top
        topHBox = new HBox();

        // Center
        centerVBox = new VBox();

        // Down
        buttonVBox = new VBox();

        // Buttons
        mp3playerButton = new Button();
        playListButton = new Button();
        gameButton = new Button();
        settingButton = new Button();
        left = new Button("<");
        up = new Button("up");
        down = new Button("down");
        right = new Button(">");
    }

    public Button getMp3playerButton() { return mp3playerButton; }
    public Button getplayListButton() { return playListButton; }
    public Button getgameButton() { return gameButton; }
    public Button getSettingsButton() { return settingButton; }

}
