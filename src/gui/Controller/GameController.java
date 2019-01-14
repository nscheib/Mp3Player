package gui.Controller;

import businessLogic.Main;
import businessLogic.Mp3Player;
import gui.view.GameView;
import javafx.scene.layout.Pane;

public class GameController {

    private GameView view;

    public GameController(Main application, Mp3Player player) {

        this.view = new GameView();

        // Stylesheet, aus welcher Datei die Einstellung gelesen werden sollen
        view.getStylesheets().add(getClass().getResource("/gui/css/style.css").toExternalForm());

        // Button Funktionen
        view.getMp3playerButton().setOnAction(e -> application.switchScene("MP3Player"));
        view.getplayListButton().setOnAction(e -> application.switchScene("PlayListEditor"));
        view.getgameButton().setOnAction(e -> application.switchScene("Game"));
        //view.getSettingsButton().setOnAction(e-> application.switchScene("Settings"));


    }

    public Pane getView() { return view; }
}
