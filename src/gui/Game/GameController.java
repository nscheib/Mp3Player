package gui.Game;

import businessLogic.Main;
import businessLogic.Mp3Player;
import game.Block;
import game.Ground;
import game.Pacman;
import javafx.animation.TranslateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.paint.Color;

import java.io.*;

public class GameController {

    private GameView view;
    private Pacman pacman = new Pacman(0, 0);
    private Block block = new Block(0, 0, 1);
    private Ground ground = new Ground(0, 0, 1);
    private Block[][] spielFeld = new Block[10][10];
    private String mapName;
    private int sizeX=0,sizeY=0;
    private int fillx,filly = 0;
    private Mp3Player player;

    private String draggedData;
    private TranslateTransition translateTransition;
    private IntegerProperty score = new SimpleIntegerProperty(0);
    private IntegerProperty punkte = new SimpleIntegerProperty(0);
    private IntegerProperty maxPlayLength = new SimpleIntegerProperty(0);
    private int songSnippet = 0;


    public GameController(){}
    public GameController(Main application, Mp3Player player) {

        this.view = new GameView();
        this.player = player;


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



//        Pacman insert = new Pacman(0,0);
//        pacman = insert;
//        insert.setNewPos(insert.getx(),insert.gety());
//
//
//        //Setzte Spielerfigur
//        view.getPane().getChildren().add(insert.getfigure());







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
                    }
//                    translateTransition.setFromY(pacman.gety());
//                    translateTransition.setToY(pacman.gety());
//                    translateTransition.setCycleCount(1);
//                    translateTransition.play();
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
//                        System.out.println(pacman.getx());
//                        System.out.println(pacman.gety());
//                        System.out.println(spielFeld[pacman.getx()/50][pacman.gety()/50].getType());
                    }

//                    translateTransition.setFromY(pacman.gety());
//                    translateTransition.setToY(pacman.gety());
//                    translateTransition.setCycleCount(1);
//                    translateTransition.play();
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
//                    translateTransition.setFromY(pacman.gety());
//                    translateTransition.setToX(pacman.getx());
//                    translateTransition.setCycleCount(1);
//                    translateTransition.play();
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
//                    translateTransition.setFromY(pacman.gety());
//                    translateTransition.setToX(pacman.getx());
//                    translateTransition.setCycleCount(1);
//                    translateTransition.play();
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

        // SCORE LISTENER
        score.addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                view.setScore(score.get());
            }
        });

        punkte.addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                view.setPunkte(punkte.get());
            }
        });


        // Listener die schauen dass pausiert wenn laenge von maxlaenge erreicht ist und weiterspielt wenn die erhöht wird und es pausiert ist.

        player.currentTimeProperty().addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (player.currentTimeProperty().get() >= maxPlayLength.get()){
                    player.pause();
                }
//                System.out.println("song: "+(int)player.getTrack().getLenght() + "     aktuell: "+player.currentTimeProperty().get());
//                System.out.println("BERECHNUNG : "+((int)player.getTrack().getLenght() - player.currentTimeProperty().get()) );
                if ((int)player.getTrack().getLenght() - player.currentTimeProperty().get() < 100 ){
                    player.stopPlayer();
                }
//                translateTransition.setFromY(pacman.gety());
//                translateTransition.setToX(pacman.getx());
//                translateTransition.setCycleCount(1);
//                translateTransition.play();
            }
        });

        maxPlayLength.addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (!player.isPlaying() && player.currentTimeProperty().get() <= maxPlayLength.get() ){
                    player.play();
                }
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
                    pacman = new Pacman(i*50,j*50);
                    block.setNewPos(i*50,j*50);

                } else if (spielFeld[i][j].getType() == 43 /*+*/){
                    Image img = new Image("game/images/circle.png");
                    insert.setFill(new ImagePattern(img));
                    punkte.set( punkte.getValue()+1 );

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
        view.getPane().getChildren().add(pacman.getfigure());
        translateTransition = new TranslateTransition(Duration.seconds(0.2),pacman.getfigure());
        translateTransition.setToX(pacman.getx());
        translateTransition.setToY(pacman.gety());
        translateTransition.setCycleCount(1);
        translateTransition.play();

        // berechnen der songSnippet
        songSnippet = (int)player.getTrack().getLenght() / punkte.get();

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
            spielFeld[(pacman.getx())/50][pacman.gety()/50].changeType("-");
            //spielFeld[(pacman.getx())/50][pacman.gety()/50].getBlock().setFill(Color.GREEN);
            punkte.setValue(punkte.get()-1);
            maxPlayLength.setValue(maxPlayLength.getValue()+ songSnippet);
            mapAktualisieren();




//            Rectangle newblock = new Rectangle(50,50,Color.BLUE);
//            count++;
//            newblock.setFill(Color.GREEN);
//            newblock.toBack();
//            newblock.relocate(pacman.getx(),pacman.gety());
//            view.getPane().getChildren().add(newblock);
//            //ToDO newblock muss hinter pacman gesetzt werden
        }
    }

    private void mapAktualisieren() {

        view.getPane().getChildren().clear();

        Rectangle insert;

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
                } else if (spielFeld[i][j].getBlock().getFill() == Color.ORANGE){
                    insert.setFill(Color.ORANGE);

                }
                insert.relocate(spielFeld[i][j].getx(),spielFeld[i][j].gety());
                view.getPane().getChildren().add(insert);


            }
        }
        view.getPane().getChildren().add(pacman.getfigure());

        score.setValue(score.get() + 1 );


    }

    private void rechtklick(int x, int y) {
        block.setNewPos(x + 1, y + 1);
    }

    public Pane getView() { return view; }
}