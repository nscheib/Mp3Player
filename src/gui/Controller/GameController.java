package gui.Controller;

import businessLogic.Main;
import businessLogic.Mp3Player;
import gui.game.Block;
import gui.view.GameView;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class GameController {

    private GameView view;
    private Block block;

    public GameController(Main application, Mp3Player player) {

        int x = 10, y = 10;

        this.view = new GameView();
        block = new Block(x, y);

        // Stylesheet, aus welcher Datei die Einstellung gelesen werden sollen
        view.getStylesheets().add(getClass().getResource("/gui/css/style.css").toExternalForm());

        // Button Funktionen
        view.getMp3playerButton().setOnAction(e -> application.switchScene("MP3Player"));
        view.getplayListButton().setOnAction(e -> application.switchScene("PlayListEditor"));
        view.getgameButton().setOnAction(e -> application.switchScene("Game"));
        //view.getSettingsButton().setOnAction(e-> application.switchScene("Settings"));

        view.getPane().setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP) {
                    System.out.println("up");
                } else if (event.getCode() == KeyCode.DOWN) {
                    System.out.println("down");
                } else if (event.getCode() == KeyCode.LEFT) {
                    System.out.println("left");
                } else if (event.getCode() == KeyCode.RIGHT) {
                    System.out.println("right");
                    rechtklick(x, y);
                }
            }
        });

    }


    private void rechtklick(int x, int y) {
        block.setNewPos(x + 1, y + 1);
    }

    public Pane getView() { return view; }
}
