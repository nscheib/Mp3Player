package gui.Controller;

import businessLogic.Main;
import businessLogic.Mp3Player;
import businessLogic.Track;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;

public class GameController {

    private GameView view;
    private Block block;
    private Block[][] spielFeld = new Block[20][20];
    private String mapName;
    private int sizeX=0,sizeY=0;
    private int fillx,filly = 0;

    public GameController(Main application, Mp3Player player) {

        int x = 10, y = 10;

        this.view = new GameView();
        block = new Block(0, 0,1);


        // Textdatei einlesen und map aufbauen
        einlesen("world1");
        mapBau();
        ausgeben();


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

    private void mapBau() {
        Rectangle insert;
        //insert = new Rectangle(50,50, Color.BLUE);
        for (int i = 0; i < sizeY;i++){
            for (int j= 0; j < sizeX;j++){
                insert = new Rectangle(50,50,Color.BLUE);
                if (spielFeld[i][j].getType()==120){
                    insert.setFill(Color.RED);
                }else if (spielFeld[i][j].getType()==45){
                    insert.setFill(Color.GREEN);
                }
                insert.relocate(spielFeld[i][j].getx(),spielFeld[i][j].gety());
                view.getPane().getChildren().add(insert);

            }
        }
    }

    private void ausgeben() {


        System.out.println("\nMap: "+ mapName+"\n");


        for (int i = 0; i < sizeY ; i++){
            for (int j = 0; j < sizeX;j++){
                if (spielFeld[i][j].getType()==120){
                    System.out.print("x ");
                }
                if (spielFeld[i][j].getType()==45){
                    System.out.print("- ");
                }

            }
            System.out.println();
        }
    }

    private void einlesen(String worldname) {

        this.mapName = worldname;

        int counterY = 0;
        int posX,posY;

        try {
            BufferedReader bReader = new BufferedReader(new FileReader("Worlds/"+worldname+".txt"));
            String line = bReader.readLine();
            this.sizeX = line.length();
            while (line != null) {
                posY = this.sizeY * 50;
                this.sizeY++;
                for (int x = 0; x < sizeX; x++){
                    // posX ist positionswert der immer um 50 erhöht wird
                    posX = x * 50;
                    spielFeld[x][counterY] = new Block (posX,posY,line.charAt(x));
                }
                line = bReader.readLine();
                counterY++;

            }
        }catch(java.io.IOException  e){
            e.printStackTrace();
        }
    }


    private void rechtklick(int x, int y) {
        block.setNewPos(x + 1, y + 1);
    }

    public Pane getView() { return view; }
}
