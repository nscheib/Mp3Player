package gui.Game;

import businessLogic.Main;
import businessLogic.Mp3Player;
import businessLogic.Track;
import game.Block;
import game.Ground;
import game.Pacman;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GameController {

    private GameView view;
    private Pacman pacman = new Pacman(0, 0);
    private Block block = new Block(0, 0, 1);
    private Ground ground = new Ground(0, 0, 1);
    private Block[][] spielFeld = new Block[10][10];
//    private String mapName;
    private int sizeX=0,sizeY=0;
    private int fillx,filly = 0;
    private Mp3Player player;

    private String draggedData;
    private TranslateTransition translateTransition;
    private IntegerProperty score = new SimpleIntegerProperty(0);
    private IntegerProperty punkte = new SimpleIntegerProperty(0);
    private IntegerProperty maxPlayLength = new SimpleIntegerProperty(0);
    private int songSnippet = 0;

    private String standardSong = "lovewillbewithyou.mp3";
//    private IntegerProperty loaded = new SimpleIntegerProperty(0); //0 nein, 1 ja
    private ArrayList<String> maps = new ArrayList<String>(Arrays.asList(new File ("Worlds").list()));
    private String aktuelleMap = maps.get(0);
    private BooleanProperty spielEnde = new SimpleBooleanProperty(false);
    private BooleanProperty spielStart = new SimpleBooleanProperty(true);
    private int endergebnis;
    private boolean nextLevel = false;

//    private final Image pacmanAnimation = new Image("pacmanAnimation.png");
//    private final ImageView imageView = new ImageView(pacmanAnimation);
//    private final Animation animation = new SpriteAnimation(


    public GameController(){}
    public GameController(Main application, Mp3Player player) {

        this.view = new GameView();
        this.player = player;
        System.out.println("\n"+maps.get(0));
        System.out.println("\n"+maps.get(1));

        // Animation vorbereiten
//        imageView.setViewport(new Rectangle2D(0,0,50,50));


        // Textdatei einlesen und map aufbauen



        // Stylesheet, aus welcher Datei die Einstellung gelesen werden sollen
        view.getStylesheets().addAll(
                getClass().getResource("/gui/css/style.css").toExternalForm(),
                getClass().getResource("/gui/css/game.css").toExternalForm()
        );

        // Menü
        view.getMp3playerButton().setOnAction(e -> {
            application.switchScene("MP3Player");
            player.setCurrentMode(0);
                }

        );
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

                if((event.getCode() == KeyCode.ENTER) && spielStart.get() ){
                    System.out.println("YES");
                    view.setCenter(view.getPane());
                    einlesen(aktuelleMap);
                    mapBau();
                    ausgeben();
                    spielStart.set(false);
                    spielEnde.set(false);
//                    for (int i = 0; i < sizeX;i++){
//                        for (int j= 0; j < sizeY; j++){
//                            mapAktualisierenBlock(i,j);
//                        }
//                    }

                }else if ((event.getCode() == KeyCode.ENTER) && spielEnde.get()){
                    spielStart.set(true);
                    spielEnde.set(false);
                    score.set(0);
                    spielStart();
                }


                if (!spielEnde.get() && !spielStart.get()) {



                    if (event.getCode() == KeyCode.UP) {
                        //System.out.println("up");
                        if (pacman.gety() != 0 && spielFeld[pacman.getx() / 50][(pacman.gety() - 50) / 50].getType() != 120) {
                            translateTransition.setFromY(pacman.gety());
                            translateTransition.setToY(pacman.gety() - 50);
                            translateTransition.setCycleCount(1);
                            translateTransition.play();
                            //block.getBlock().setTranslateY(block.gety()-50);
                            pacman.setNewPos(pacman.getx(), pacman.gety() - 50);
                        }
//                    translateTransition.setFromY(pacman.gety());
//                    translateTransition.setToY(pacman.gety());
//                    translateTransition.setCycleCount(1);
//                    translateTransition.play();
                        checktype();

                    } else if (event.getCode() == KeyCode.DOWN) {
                        //System.out.println("down");
                        if (pacman.gety() != 450 && spielFeld[pacman.getx() / 50][(pacman.gety() + 50) / 50].getType() != 120) {

                            translateTransition.setFromY(pacman.gety());
                            translateTransition.setToY(pacman.gety() + 50);
                            translateTransition.setCycleCount(1);
                            translateTransition.play();
                            //block.getBlock().setTranslateY(block.gety()+50);
                            pacman.setNewPos(pacman.getx(), pacman.gety() + 50);
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
                        if (pacman.getx() != 0 && spielFeld[(pacman.getx() - 50) / 50][pacman.gety() / 50].getType() != 120) {
                            translateTransition.setFromY(pacman.gety());
                            translateTransition.setToX(pacman.getx() - 50);
                            translateTransition.setCycleCount(1);
                            translateTransition.play();
                            //block.getBlock().setTranslateX(block.getx()-50);
                            pacman.setNewPos(pacman.getx() - 50, pacman.gety());
                        }
//                    translateTransition.setFromY(pacman.gety());
//                    translateTransition.setToX(pacman.getx());
//                    translateTransition.setCycleCount(1);
//                    translateTransition.play();
                        checktype();


                    } else if (event.getCode() == KeyCode.RIGHT) {
                        //System.out.println("right");
                        if (pacman.getx() != 450 && spielFeld[(pacman.getx() + 50) / 50][pacman.gety() / 50].getType() != 120) {
                            translateTransition.setFromY(pacman.gety());
                            translateTransition.setToX(pacman.getx() + 50);
                            translateTransition.setCycleCount(1);
                            translateTransition.play();
                            //block.getBlock().setTranslateX(block.getx()+50);
                            pacman.setNewPos(pacman.getx() + 50, pacman.gety());
                        }
//                    translateTransition.setFromY(pacman.gety());
//                    translateTransition.setToX(pacman.getx());
//                    translateTransition.setCycleCount(1);
//                    translateTransition.play();
                        checktype();

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
                    punkte.set(0);
                    einlesen(draggedData);
                    ausgeben();
                    view.getPane().getChildren().clear();
                    mapBau();
                    view.getPane().getChildren().add(block.getBlock());
//                    for (int i = 0; i < sizeX;i++){
//                        for (int j= 0; j < sizeY; j++){
//                            mapAktualisierenBlock(i,j);
//                        }
//                    }
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
                // Punkte == 0 heißt nächste map laden, wenn keine da, dann spielende


                if (newValue.intValue() == 0 && oldValue.intValue() == 1 && !spielEnde.get()){
                    System.out.println(maps.indexOf(aktuelleMap) + " "+ maps.size());


                    int temp = score.get();
                    if (maps.indexOf(aktuelleMap) < maps.size()-1){
                        System.out.println("NEXT LVL");
                        nextLevel = true;
                        player.skipright();
                        player.pause();
                        aktuelleMap = maps.get(maps.indexOf(aktuelleMap)+1);
                        einlesen(aktuelleMap);
                        mapBau();
//                        for (int i = 0; i < sizeX;i++){
//                            for (int j= 0; j < sizeY; j++){
//                                mapAktualisierenBlock(i,j);
//                            }
//                        }
                        score.set(temp);
                    }else {
                        System.out.println("SPIELENDE");
                        spielEnde.set(true);
//                        spielEnde(temp);
                    }
                }
            }
        });


        // Listener die schauen dass pausiert wenn laenge von maxlaenge erreicht ist und weiterspielt wenn die erhöht wird und es pausiert ist.

        player.currentTimeProperty().addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if ((player.currentTimeProperty().get() >= maxPlayLength.get()) && player.getCurrentMode().get() == 1){
                    player.pause();
                }
//                System.out.println("song: "+(int)player.getTrack().getLenght() + "     aktuell: "+player.currentTimeProperty().get());
//                System.out.println("BERECHNUNG : "+((int)player.getTrack().getLenght() - player.currentTimeProperty().get()) );
                if (((int)player.getTrack().getLenght() - player.currentTimeProperty().get()) < 100 ){
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

                if (nextLevel == true){
                    player.pause();

                }else if ((!player.isPlaying() && player.currentTimeProperty().get() <= maxPlayLength.get()) && player.getCurrentMode().get() == 1){
                    player.play();
                }
            }
        });

        // Baut map neu auf und setzt zeit auf null und berechnet damit die zeit auch neu
        // Hier kann man dann auch noch eventuell schauen dass das Lied geändert wird
        player.getCurrentMode().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() == 1){
                    spielStart.set(true);
                    spielEnde.set(false);
                    aktuelleMap = maps.get(0);
                    punkte.set(0);
                    score.set(0);
                    spielStart();
//
//                    player.playSelected(new Track(standardSong));
////                    player.skipleft();
//                    player.pause();
//                    einlesen(maps.get(0));
//                    mapBau();
//                    mapAktualisieren();
                }
            }
        });

        spielEnde.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (spielEnde.get() && oldValue.booleanValue() == false){
                    spielEnde();

                }
            }
        });







    }

    private void spielStart() {
        Pane startScreen = new Pane();
        Rectangle startBlock = new Rectangle(300,250);
        Text willkommen = new Text("Willkommen zu unserem Spiel\n\nDrücke Enter um das Spiel zu starten!");
        startScreen.getChildren().add(willkommen);
        view.setCenter(willkommen);

    }

    private void spielEnde() {


        Pane endScreen = new Pane();
        Rectangle startBlock = new Rectangle(300,250);
        Text tschau = new Text("Deine Punktzahl: "+(score.get()+1)+"\n\nDrücke Enter um das Spiel zu neu zu starten!");
        endScreen.getChildren().add(tschau);
        view.setCenter(tschau);
        aktuelleMap = maps.get(0);

        // DARSTELLUNG SPIELENDE


    }


    private void mapBau() {
        view.getPane().getChildren().clear();
        Rectangle insert;
        //insert = new Rectangle(50,50, Color.BLUE);
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                insert = new Rectangle(50, 50, Color.BLUE);
                if (spielFeld[i][j].getType() == 120 /*x*/) {
                    insert.setFill(Color.RED);
                } else if (spielFeld[i][j].getType() == 45 /*-*/) {
                    insert.setFill(Color.GREEN);
                } else if (spielFeld[i][j].getType() == 111 /*o*/) {
                    insert.setFill(Color.YELLOW);
                    //ToDo
                    // Translatetransition irgendwie richtig machen
//                    translateTransition2 = new TranslateTransition(Duration.seconds(1),block.getBlock());
//                    translateTransition2.setFromY(block.gety());
//                    translateTransition2.setToX(j*50);
//                    translateTransition2.play();

                    pacman = new Pacman(i * 50, j * 50);
                    block.setNewPos(i * 50, j * 50);

                } else if (spielFeld[i][j].getType() == 43 /*+*/) {
                    Image img = new Image("game/images/circle.png");
                    insert.setFill(new ImagePattern(img));
                    punkte.set(punkte.getValue() + 1);


                    // ToDo Bild muss kleiner Scaliert werden
                    // Bild selbst kleiner machen hilft nicht
                    //   insert.setHeight();  && insert.setWidth();
                    // funktioniert zwar aber das Bild wird auf eine
                    // falsche Pos gesetzt
                }
                insert.relocate(spielFeld[i][j].getx(), spielFeld[i][j].gety());
                view.getPane().getChildren().add(insert);

            }
        }


        if (!spielEnde.get()) {
            view.getPane().getChildren().add(pacman.getfigure());
            translateTransition = new TranslateTransition(Duration.seconds(0.055), pacman.getfigure());
            translateTransition.setToX(pacman.getx());
            translateTransition.setToY(pacman.gety());
            translateTransition.setCycleCount(1);
            translateTransition.play();

            // berechnen der songSnippet
            songSnippet = (int) player.getTrack().getLenght() / punkte.get();

        }

    }

    private void ausgeben() {


        System.out.println("\nMap: "+ aktuelleMap +"\n");


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

        score.set(0);
//        this.mapName = worldname;

        int counterY = 0;
        int posX,posY;
        this.sizeX=0;
        this.sizeY=0;

        try {
            BufferedReader bReader = new BufferedReader(new FileReader("Worlds/"+worldname));
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
            score.setValue(score.get() + 1 );
            nextLevel = false;
//            mapAktualisierenBlock(spielFeld[(pacman.getx())/50][pacman.gety()/50].getBlock());

            mapAktualisierenBlock(pacman.getx()/50,pacman.gety()/50);



//            Rectangle newblock = new Rectangle(50,50,Color.BLUE);
//            count++;
//            newblock.setFill(Color.GREEN);
//            newblock.toBack();
//            newblock.relocate(pacman.getx(),pacman.gety());
//            view.getPane().getChildren().add(newblock);
//            //ToDO newblock muss hinter pacman gesetzt werden
        }
    }

    public void mapAktualisierenBlock(int x , int y){

//        view.getPane().getChildren().clear();
        Rectangle insert = spielFeld[x][y].getBlock();
//        int insertPos = view.getPane().getChildren().indexOf(spielFeld[x][y].getBlock());
        view.getPane().getChildren().remove(insert);
        //view.getPane().getChildren().remove(changeBlockPos);
        if (spielFeld[x][y].getType()==120 /*x*/){
            insert.setFill(Color.RED);
        }else if (spielFeld[x][y].getType()==45 /*-*/) {
            insert.setFill(Color.GREEN);
        }else if (spielFeld[x][y].getType()==111 /*o*/){
            insert.setFill(Color.YELLOW);
            block.setNewPos(x*50,x*50);
        } else if (spielFeld[x][y].getType() == 43 /*+*/){
            Image img = new Image("game/images/circle.png");
            insert.setFill(new ImagePattern(img));
        }
        insert.relocate(x*50,y*50);
        view.getPane().getChildren().add(insert);

        int pos = view.getPane().getChildren().indexOf(pacman.getfigure());
        view.getPane().getChildren().remove(pos);
        view.getPane().getChildren().add(pacman.getfigure());
    }





    private void rechtklick(int x, int y) {
        block.setNewPos(x + 1, y + 1);
    }

    public Pane getView() { return view; }
}