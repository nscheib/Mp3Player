package gui.game;

import businessLogic.Main;
import businessLogic.Mp3Player;
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
    private ArrayList<String> maps = new ArrayList<String>(Arrays.asList(new File ("Worlds").list()));
    private String aktuelleMap = maps.get(0);
    private BooleanProperty spielEnde = new SimpleBooleanProperty(false);
    private BooleanProperty spielStart = new SimpleBooleanProperty(true);
    private boolean nextLevel = false;

//    private final Image pacmanAnimation = new Image("pacmanAnimation.png");
//    private final ImageView imageView = new ImageView(pacmanAnimation);
//    private final Animation animation = new SpriteAnimation(

    /**
     * Initialisierungskontroller
     * @param application Applikation
     * @param player Mp3Player
     */
    public GameController(Main application, Mp3Player player) {
        this.view = new GameView();
        this.player = player;
        System.out.println("\n"+maps.get(0));
        System.out.println("\n"+maps.get(1));

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
        view.getgameButton().setOnAction(e -> application.switchScene("game"));
        //view.getSettingsButton().setOnAction(e-> application.switchScene("Settings"));

        view.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Rectangle bl;
                if((event.getCode() == KeyCode.ENTER) && spielStart.get() ){
                    //System.out.println("YES");
                    view.setCenter(view.getPane());
                    readIn(aktuelleMap);
                    mapBau();
                    print();
                    spielStart.set(false);
                    spielEnde.set(false);
                }else if ((event.getCode() == KeyCode.ENTER) && spielEnde.get()){
                    spielStart.set(true);
                    spielEnde.set(false);
                    score.set(0);
                    beginGame();
                }
                if (!spielEnde.get() && !spielStart.get()) {
                    if (event.getCode() == KeyCode.UP) {
                        if (pacman.gety() != 0 && spielFeld[pacman.getx() / 50][(pacman.gety() - 50) / 50].getType() != 120) {
                            translateTransition.setFromY(pacman.gety());
                            translateTransition.setToY(pacman.gety() - 50);
                            translateTransition.setCycleCount(1);
                            translateTransition.play();
                            pacman.setNewPos(pacman.getx(), pacman.gety() - 50);
                            pacman.changepic("o");
                        }
                        checktype();
                    } else if (event.getCode() == KeyCode.DOWN) {
                        if (pacman.gety() != 450 && spielFeld[pacman.getx() / 50][(pacman.gety() + 50) / 50].getType() != 120) {
                            translateTransition.setFromY(pacman.gety());
                            translateTransition.setToY(pacman.gety() + 50);
                            translateTransition.setCycleCount(1);
                            translateTransition.play();
                            pacman.setNewPos(pacman.getx(), pacman.gety() + 50);
                            pacman.changepic("u");
                        }
                        checktype();
                    } else if (event.getCode() == KeyCode.LEFT) {
                        if (pacman.getx() != 0 && spielFeld[(pacman.getx() - 50) / 50][pacman.gety() / 50].getType() != 120) {
                            translateTransition.setFromY(pacman.gety());
                            translateTransition.setToX(pacman.getx() - 50);
                            translateTransition.setCycleCount(1);
                            translateTransition.play();
                            pacman.setNewPos(pacman.getx() - 50, pacman.gety());
                            pacman.changepic("l");
                        }
                        checktype();
                    } else if (event.getCode() == KeyCode.RIGHT) {
                        if (pacman.getx() != 450 && spielFeld[(pacman.getx() + 50) / 50][pacman.gety() / 50].getType() != 120) {
                            translateTransition.setFromY(pacman.gety());
                            translateTransition.setToX(pacman.getx() + 50);
                            translateTransition.setCycleCount(1);
                            translateTransition.play();
                            pacman.setNewPos(pacman.getx() + 50, pacman.gety());
                            pacman.changepic("r");
                        }
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
                    readIn(draggedData);
                    print();
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
                // Punkte == 0 heißt nächste map laden, wenn keine da, dann spielende

                if (newValue.intValue() == 0 && oldValue.intValue() == 1 && !spielEnde.get()){
                    //System.out.println(maps.indexOf(aktuelleMap) + " "+ maps.size());

                    int temp = score.get();
                    if (maps.indexOf(aktuelleMap) < maps.size()-1){
                        System.out.println("NEXT LVL");
                        nextLevel = true;
                        player.skipright();
                        player.pause();
                        aktuelleMap = maps.get(maps.indexOf(aktuelleMap)+1);
                        readIn(aktuelleMap);
                        mapBau();
//                        for (int i = 0; i < sizeX;i++){
//                            for (int j= 0; j < sizeY; j++){
//                                updateBlock(i,j);
//                            }
//                        }
                        score.set(temp);
                        maxPlayLength.set(0);
                    }else {
                        System.out.println("SPIELENDE");
                        spielEnde.set(true);
//                        endGame(temp);

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
                if (((int)player.getTrack().getLenght() - player.currentTimeProperty().get()) < 100 ){
                    player.stopPlayer();
                }
            }
        });

        maxPlayLength.addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (nextLevel == true){
                    player.pause();
                    nextLevel = false;
                }else if ((!player.isPlaying() && player.currentTimeProperty().get() <= maxPlayLength.get()) && player.getCurrentMode().get() == 1 && !spielEnde.get()){
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
                    beginGame();
                }
            }
        });

        spielEnde.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (spielEnde.get() && oldValue.booleanValue() == false){
                    endGame();
                }
            }
        });
    }

    /**
     * Erstellt einen Start-Screen
     */
    private void beginGame() {
        Pane startScreen = new Pane();
        Rectangle startBlock = new Rectangle(300,250);
        Text welcome = new Text("Willkommen zu unserem Spiel\n\nDrücke Enter um das Spiel zu starten!");
        startScreen.getChildren().add(welcome);
        view.setCenter(welcome);
    }

    /**
     * Erstellt einen End-Screen
     */
    private void endGame() {
        Pane endScreen = new Pane();
        Rectangle startBlock = new Rectangle(300,250);
        Text goodbye = new Text("Deine Punktzahl: "+(score.get()+1)+"\n\nDrücke Enter um das Spiel zu neu zu starten!");
        endScreen.getChildren().add(goodbye);
        view.setCenter(goodbye);
        aktuelleMap = maps.get(0);
        player.stopPlayer();

        // DARSTELLUNG SPIELENDE
    }

    /**
     * Methode zum Erstellen des Spielfeldes.
     * Hierbei werden die Textdateien gelesen und je nach Eingabezeichen
     * die passenden Bilder gesetzt,
     * um die Spielatospaere zu verbessern.
     */
    private void mapBau() {
        view.getPane().getChildren().clear();
        Rectangle insert;
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                insert = new Rectangle(50, 50, Color.BLUE);
                if (spielFeld[i][j].getType() == 120 /*x*/) {
                    insert.setFill(new ImagePattern(new Image ("game/images/wall-texture.png")));
                } else if (spielFeld[i][j].getType() == 45 /*-*/) {
                    insert.setFill(new ImagePattern(new Image ("game/images/ground.png")));
                } else if (spielFeld[i][j].getType() == 111 /*o*/) {
                    insert.setFill(new ImagePattern(new Image ("game/images/ground_fahne.png")));

                    pacman = new Pacman(i * 50, j * 50);
                    block.setNewPos(i * 50, j * 50);

                } else if (spielFeld[i][j].getType() == 43 /*+*/) {
                    insert.setFill(new ImagePattern(new Image ("game/images/ground-texture.png")));
                    punkte.set(punkte.getValue() + 1);
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

    /**
     * Methode zur Kontrolle in Form von Konsolenausgabe,
     * die das Spielfeld abbildet.
     */
    private void print() {
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

    /**
     * Methode, die eine Text-Datei einliest,
     * und dann mit der Klasse Block das Spielfeld erstellt.
     * @param worldname Datename, der Map
     */
    private void readIn (String worldname) {
        score.set(0);
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

    /**
     * Methode zum ueberpruefen ob der Spielfeld-Block
     * eine Muenze besitzt, also welcher Typ vorliegt.
     * Verwaltet auch die Aktualisierung
     */
    public void checktype() {
        if(spielFeld[(pacman.getx())/50][pacman.gety()/50].getType() == 43 ) {
            spielFeld[(pacman.getx())/50][pacman.gety()/50].changeType("-");
            punkte.setValue(punkte.get()-1);
            maxPlayLength.setValue(maxPlayLength.getValue()+ songSnippet);
            score.setValue(score.get() + 1 );
            nextLevel = false;
            updateBlock(pacman.getx()/50,pacman.gety()/50);
        }
    }

    /**
     * Aktualisiert die Bloecke und setzt das jeweilige
     * passende Bild ein.
     * @param x x-Koordinate
     * @param y y-Koordinate
     */
    public void updateBlock(int x , int y){
        Rectangle insert = spielFeld[x][y].getBlock();
        view.getPane().getChildren().remove(insert);
        if (spielFeld[x][y].getType()==120 /*x*/){
            insert.setFill(new ImagePattern(new Image ("game/images/wall-texture.png")));
        }else if (spielFeld[x][y].getType()==45 /*-*/) {
            insert.setFill(new ImagePattern(new Image ("game/images/ground.png")));
        }else if (spielFeld[x][y].getType()==111 /*o*/){
            insert.setFill(new ImagePattern(new Image ("game/images/ground_fahne.png")));
            block.setNewPos(x*50,x*50);
        } else if (spielFeld[x][y].getType() == 43 /*+*/){
            insert.setFill(new ImagePattern(new Image ("game/images/ground-texture.png")));
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

    /**
     * Gewaehrt Zugriff auf Pane. (Zeichenflaeche)
     * @return
     */
    public Pane getView() { return view; }
}