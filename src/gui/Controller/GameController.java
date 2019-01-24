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
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

//import java.awt.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;

public class GameController {

    private GameView view;
    private Block block = new Block(0, 0,1);
    private Block[][] spielFeld = new Block[20][20];
    private String mapName;
    private int sizeX=0,sizeY=0;
    private int fillx,filly = 0;

    private String draggedData;
    private TranslateTransition translateTransition;

    public GameController(Main application, Mp3Player player) {

        int x = 10, y = 10;

        this.view = new GameView();
        //block = new Block(0, 0,1);


        // Textdatei einlesen und map aufbauen
        einlesen("Worlds/world1.txt");
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



        translateTransition = new TranslateTransition(Duration.seconds(0.2),block.getBlock());



        view.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                // ToDo
                // schauen das neue position kein block oder rand des spiels ist
                if (event.getCode() == KeyCode.UP) {
                    //System.out.println("up");
                    if (block.gety() != 0&& spielFeld[block.getx()/50][(block.gety()-50)/50].getType() != 120) {

                        translateTransition.setFromY(block.gety());
                        translateTransition.setToY(block.gety()-50);
                        translateTransition.setCycleCount(1);
                        translateTransition.play();
                        //block.getBlock().setTranslateY(block.gety()-50);
                        block.setNewPos(block.getx(),block.gety()-50);
                    }else System.out.println("OBERER RAND");


                } else if (event.getCode() == KeyCode.DOWN) {
                    //System.out.println("down");
                    if(block.gety()!=450 && spielFeld[block.getx()/50][(block.gety()+50)/50].getType() != 120){

                        translateTransition.setFromY(block.gety());
                        translateTransition.setToY(block.gety()+50);
                        translateTransition.setCycleCount(1);
                        translateTransition.play();
                        //block.getBlock().setTranslateY(block.gety()+50);
                        block.setNewPos(block.getx(),block.gety()+50);
                        System.out.println(block.getx());
                        System.out.println(block.gety());
                        System.out.println(spielFeld[block.getx()/50][block.gety()/50].getType());
                    }

                    if (spielFeld[block.getx()/50][(block.gety()/50)+1].getType() != 45){
                        System.out.println("NEIN HIER IST EINE WAND!");
                    }




                } else if (event.getCode() == KeyCode.LEFT) {
                    //System.out.println("left");
                    if(block.getx() != 0&& spielFeld[(block.getx()-50)/50][block.gety()/50].getType() != 120){
                        translateTransition.setFromY(block.gety());
                        translateTransition.setToX(block.getx()-50);
                        translateTransition.setCycleCount(1);
                        translateTransition.play();
                        //block.getBlock().setTranslateX(block.getx()-50);
                        block.setNewPos(block.getx()-50,block.gety());
                    }


                } else if (event.getCode() == KeyCode.RIGHT) {
                    //System.out.println("right");
                    if(block.getx() != 450&& spielFeld[(block.getx()+50)/50][block.gety()/50].getType() != 120){
                        translateTransition.setFromY(block.gety());
                        translateTransition.setToX(block.getx()+50);
                        translateTransition.setCycleCount(1);
                        translateTransition.play();
                        //block.getBlock().setTranslateX(block.getx()+50);
                        block.setNewPos(block.getx()+50,block.gety());
                    }


                }
            }
        });

        view.getPane().setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != view.getPane()
                        && event.getDragboard().hasFiles()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        view.getPane().setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    draggedData = db.getFiles().toString();
                    draggedData = draggedData.substring(1,draggedData.length()-1);
                    einlesen(draggedData);
                    ausgeben();
                    view.getPane().getChildren().clear();
                    mapBau();
                    view.getPane().getChildren().add(block.getBlock());
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
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
                }else if (spielFeld[i][j].getType()==45) {
                    insert.setFill(Color.GREEN);
                }else if (spielFeld[i][j].getType()==111){
                    insert.setFill(Color.YELLOW);
                    //ToDo
                    // Translatetransition irgendwie richtig machen
//                    translateTransition2 = new TranslateTransition(Duration.seconds(1),block.getBlock());
//                    translateTransition2.setFromY(block.gety());
//                    translateTransition2.setToX(j*50);
//                    translateTransition2.play();
                    block.setNewPos(i*50,j*50);

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
                if (spielFeld[i][j].getType()==111){
                    System.out.print("o ");
                }

            }
            System.out.println();
        }
    }

    private void einlesen(String worldname) {

        this.mapName = worldname;

        int counterY = 0;
        int posX,posY;
        this.sizeX=0;
        this.sizeY=0;

        try {
            BufferedReader bReader = new BufferedReader(new FileReader(worldname));
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
