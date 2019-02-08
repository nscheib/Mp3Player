package businessLogic;

import gui.game.GameController;
import gui.mp3player.Mp3Controller;
import gui.playlists.PlayListController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    private Stage stage;
    private Mp3Controller mp3Controller;
    private PlayListController playListController;
    private Mp3Player player;
    private GameController gameController;
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[31m";
    public static final String ANSI_GREEN = "\033[36m";
    public static final String ANSI_RESET = "\u001B[0m";


    /**
     * Hiermit wird das Programm gestartet bzw die Applikation verwaltet.
     * @param args
     */
    public static void main(String[]args) { launch(args); }

    /**
     * Methode initialisiert einen Mp3Player,
     * dient generell der Initialisierung von Businessobjekten.
     */
    public void init() {
        player = new Mp3Player();

    }

    /**
     * Hier werden die eigentlichen Views bzw. die jeweiligen Controller erzeugt.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) {
        welcome();
        mp3Controller = new Mp3Controller(this, player);
        playListController = new PlayListController(this, player);
        gameController = new GameController(this, player);
        stage = primaryStage;
        stage.setScene( new Scene ( mp3Controller.getView(), 600, 800));
        stage.setMinHeight(800);
        stage.setMinWidth(600);
        stage.show();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.out.println( ANSI_CYAN + "<<=================================>>\n" + ANSI_RESET +
                                ANSI_BLUE + "    Wird Beendet!" + ANSI_RESET + "\n" +
                                ANSI_CYAN + "<<=================================>>\n" + ANSI_RESET );
            System.exit(0);
        });
    }

    /**
     * Methode zum switchen zwischen den Scenen.
     * @param sceneName
     */
    public void switchScene(String sceneName) {

        switch(sceneName) {
            case "PlayListEditor": stage.getScene().setRoot(playListController.getView()); break;
            case "MP3Player": stage.getScene().setRoot(mp3Controller.getView()); break;
            case "game": stage.getScene().setRoot(gameController.getView()); break;
            //case "Settings": stage.getScene().setRoot(settingsController.getView()); break;
        }
    }

    /**
     * Methode die einen Willkommenstext ausgibt.
     */
    public void welcome() {
        System.out.println( ANSI_GREEN + "<<=================================>>\n" + ANSI_RESET +
                            ANSI_BLUE + "    Willkommen!\n" + ANSI_RESET +
                            ANSI_BLUE + "    Der Mp3 Player startet ...\n" + ANSI_RESET +
                            ANSI_GREEN + "<<=================================>>\n" + ANSI_RESET);
    }
}
