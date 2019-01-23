package gui.Controller;

import businessLogic.Main;
import businessLogic.Mp3Player;
import gui.game.Block;
import gui.view.GameView;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

//import java.awt.*;

public class GameController {

    private GameView view;
    private Block block;

    public GameController(Main application, Mp3Player player) {

        int x = 10, y = 10;

        this.view = new GameView();
        block = new Block(0, 0,1);

        // Stylesheet, aus welcher Datei die Einstellung gelesen werden sollen
        view.getStylesheets().add(getClass().getResource("/gui/css/style.css").toExternalForm());

        // Button Funktionen
        view.getMp3playerButton().setOnAction(e -> application.switchScene("MP3Player"));
        view.getplayListButton().setOnAction(e -> application.switchScene("PlayListEditor"));
        view.getgameButton().setOnAction(e -> application.switchScene("Game"));
        //view.getSettingsButton().setOnAction(e-> application.switchScene("Settings"));



        Block insert = new Block(0,0,0);
        block = insert;
        insert.setNewPos(insert.getx(),insert.gety());



        view.getPane().getChildren().add(insert.getBlock());



        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.2),block.getBlock());


        view.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                // ToDo
                // schauen das neue position kein block oder rand des spiels ist
                if (event.getCode() == KeyCode.UP) {
                    //System.out.println("up");
                    translateTransition.setFromY(block.gety());
                    translateTransition.setToY(block.gety()-50);
                    translateTransition.setCycleCount(1);
                    translateTransition.play();
                    //block.getBlock().setTranslateY(block.gety()-50);
                    block.setNewPos(block.getx(),block.gety()-50);

                } else if (event.getCode() == KeyCode.DOWN) {
                    //System.out.println("down");
                    translateTransition.setFromY(block.gety());
                    translateTransition.setToY(block.gety()+50);
                    translateTransition.setCycleCount(1);
                    translateTransition.play();
                    //block.getBlock().setTranslateY(block.gety()+50);
                    block.setNewPos(block.getx(),block.gety()+50);

                } else if (event.getCode() == KeyCode.LEFT) {
                    //System.out.println("left");
                    translateTransition.setFromY(block.gety());
                    translateTransition.setToX(block.getx()-50);
                    translateTransition.setCycleCount(1);
                    translateTransition.play();
                    //block.getBlock().setTranslateX(block.getx()-50);
                    block.setNewPos(block.getx()-50,block.gety());

                } else if (event.getCode() == KeyCode.RIGHT) {
                    //System.out.println("right");
                    translateTransition.setFromY(block.gety());
                    translateTransition.setToX(block.getx()+50);
                    translateTransition.setCycleCount(1);
                    translateTransition.play();
                    //block.getBlock().setTranslateX(block.getx()+50);
                    block.setNewPos(block.getx()+50,block.gety());

                }
            }
        });

//        view.getPane().setOnKeyPressed(new EventHandler<KeyEvent>() {
//
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.UP) {
//                    System.out.println("up");
//                } else if (event.getCode() == KeyCode.DOWN) {
//                    System.out.println("down");
//                } else if (event.getCode() == KeyCode.LEFT) {
//                    System.out.println("left");
//                } else if (event.getCode() == KeyCode.RIGHT) {
//                    System.out.println("right");
//                    rechtklick(x, y);
//                }
//            }
//        });



    }


    private void rechtklick(int x, int y) {
        block.setNewPos(x + 1, y + 1);
    }

    public Pane getView() { return view; }
}
