package gui.Game;

import businessLogic.Main;
import businessLogic.Mp3Player;
import game.Block;
import game.Ground;
import game.Pacman;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.io.*;

public class GameController {

    private GameView view;
    private Pacman pacman = new Pacman(0, 0);
    private Block block = new Block(0, 0, 1);
    private Ground ground = new Ground(0, 0, 1);
    private Block[][] spielFeld = new Block[20][20];
    private String mapName;
    private int sizeX=0,sizeY=0;
    private int fillx,filly = 0;

    private String draggedData;
    private TranslateTransition translateTransition;
    private int count;

    public GameController(){}
    public GameController(Main application, Mp3Player player) {

        this.view = new GameView();


        // Textdatei einlesen und map aufbauen
        einlesen("Worlds/map.txt");
        mapBau();
        ausgeben();


        // Stylesheet, aus welcher Datei die Einstellung gelesen werden sollen
        view.getStylesheets().addAll(
                getClass().getResource("/gui/css/style.css").toExternalForm(),
                getClass().getResource("/gui/css/game.css").toExternalForm()
        );

        // Menü
        view.getMp3playerButton().setOnAction(e -> application.switchScene("MP3Player"));
        view.getplayListButton().setOnAction(e -> application.switchScene("PlayListEditor"));
        view.getgameButton().setOnAction(e -> application.switchScene("Game"));
        //view.getSettingsButton().setOnAction(e-> application.switchScene("Settings"));



        Pacman insert = new Pacman(0,0);
        pacman = insert;
        insert.setNewPos(insert.getx(),insert.gety());


        //Setzte Spielerfigur
        view.getPane().getChildren().add(insert.getfigure());



        translateTransition = new TranslateTransition(Duration.seconds(0.2),pacman.getfigure());



        view.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                Rectangle bl;
                // ToDo
                // schauen das neue position kein block oder rand des spiels ist
                if (event.getCode() == KeyCode.UP) {
                    //System.out.println("up");
                    if (pacman.gety() != 0&& spielFeld[pacman.getx()/50][(pacman.gety()-50)/50].getType() != 120) {
                        translateTransition.setFromY(pacman.gety());
                        translateTransition.setToY(pacman.gety()-50);
                        translateTransition.setCycleCount(1);
                        translateTransition.play();
                        //block.getBlock().setTranslateY(block.gety()-50);
                        pacman.setNewPos(pacman.getx(),pacman.gety()-50);
                    }else System.out.println("OBERER RAND");
                    checktype();

                } else if (event.getCode() == KeyCode.DOWN) {
                    //System.out.println("down");
                    if(pacman.gety()!=450 && spielFeld[pacman.getx()/50][(pacman.gety()+50)/50].getType() != 120){

                        translateTransition.setFromY(pacman.gety());
                        translateTransition.setToY(pacman.gety()+50);
                        translateTransition.setCycleCount(1);
                        translateTransition.play();
                        //block.getBlock().setTranslateY(block.gety()+50);
                        pacman.setNewPos(pacman.getx(),pacman.gety()+50);
                        System.out.println(pacman.getx());
                        System.out.println(pacman.gety());
                        System.out.println(spielFeld[pacman.getx()/50][pacman.gety()/50].getType());
                    }

                    if (spielFeld[pacman.getx()/50][(pacman.gety()/50)+1].getType() != 45){
                        System.out.println("NEIN HIER IST EINE WAND!");
                    }
                    checktype();




                } else if (event.getCode() == KeyCode.LEFT) {
                    //System.out.println("left");
                    if(pacman.getx() != 0&& spielFeld[(pacman.getx()-50)/50][pacman.gety()/50].getType() != 120){
                        translateTransition.setFromY(pacman.gety());
                        translateTransition.setToX(pacman.getx()-50);
                        translateTransition.setCycleCount(1);
                        translateTransition.play();
                        //block.getBlock().setTranslateX(block.getx()-50);
                        pacman.setNewPos(pacman.getx()-50,pacman.gety());
                    }
                    checktype();


                } else if (event.getCode() == KeyCode.RIGHT) {
                    //System.out.println("right");
                    if(pacman.getx() != 450&& spielFeld[(pacman.getx()+50)/50][pacman.gety()/50].getType() != 120){
                        translateTransition.setFromY(pacman.gety());
                        translateTransition.setToX(pacman.getx()+50);
                        translateTransition.setCycleCount(1);
                        translateTransition.play();
                        //block.getBlock().setTranslateX(block.getx()+50);
                        pacman.setNewPos(pacman.getx()+50,pacman.gety());
                    }
                    checktype();

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

    }

    private void mapBau() {
        Rectangle insert;
        //insert = new Rectangle(50,50, Color.BLUE);
        for (int i = 0; i < sizeY;i++){
            for (int j= 0; j < sizeX;j++){
                insert = new Rectangle(50,50,Color.BLUE);
                if (spielFeld[i][j].getType()==120 /*x*/){
                    insert.setFill(Color.RED);
                }else if (spielFeld[i][j].getType()==45 /*-*/) {
                    insert.setFill(Color.GREEN);
                }else if (spielFeld[i][j].getType()==111 /*o*/){
                    insert.setFill(Color.YELLOW);
                    //ToDo
                    // Translatetransition irgendwie richtig machen
//                    translateTransition2 = new TranslateTransition(Duration.seconds(1),block.getBlock());
//                    translateTransition2.setFromY(block.gety());
//                    translateTransition2.setToX(j*50);
//                    translateTransition2.play();
                    block.setNewPos(i*50,j*50);

                } else if (spielFeld[i][j].getType() == 43 /*+*/){
                    Image img = new Image("game/images/circle.png");
                    insert.setFill(new ImagePattern(img));

                    // ToDo Bild muss kleiner Scaliert werden
                    // Bild selbst kleiner machen hilft nicht
                    //   insert.setHeight();  && insert.setWidth();
                    // funktioniert zwar aber das Bild wird auf eine
                    // falsche Pos gesetzt
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

    public void checktype() {
        if(spielFeld[(pacman.getx())/50][pacman.gety()/50].getType() == 43 ) {
            Rectangle newblock = new Rectangle(50,50,Color.BLUE);
            count++;
            newblock.setFill(Color.GREEN);
            newblock.toBack();
            newblock.relocate(pacman.getx(),pacman.gety());
            view.getPane().getChildren().add(newblock);
            //ToDO newblock muss hinter pacman gesetzt werden
        }
    }

    private void rechtklick(int x, int y) {
        block.setNewPos(x + 1, y + 1);
    }

    public Pane getView() { return view; }
}