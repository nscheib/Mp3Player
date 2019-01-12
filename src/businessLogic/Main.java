package businessLogic;

import gui.Controller.GameController;
import gui.Controller.Mp3Controller;
import gui.Controller.PlayListController;
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
     * Start Methode
     * @param args
     */
    public static void main(String[]args) { launch(args); }

    /**
     * method initializes the mp3player
     */
    public void init() {
        player = new Mp3Player();
    }

    /**
     * Method which is inherited by the application and processes the stage which is selected.
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
        stage.setScene( new Scene ( mp3Controller.getView(), 500, 800));
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
     * Methode zum switchen zwischen den Scenen
     * @param sceneName
     */
    public void switchScene(String sceneName) {

        switch(sceneName) {
            case "PlayListEditor": stage.getScene().setRoot(playListController.getView()); break;
            case "MP3Player": stage.getScene().setRoot(mp3Controller.getView()); break;
            //case "Game": stage.getScene().setRoot(gameController.getView()); break;
        }
    }

    /**
     * Methode die nur einen WillkommensText ausgibt
     */
    public void welcome() {
        System.out.println( ANSI_GREEN + "<<=================================>>\n" + ANSI_RESET +
                            ANSI_BLUE + "    Willkommen!\n" + ANSI_RESET +
                            ANSI_BLUE + "    Der Mp3 Player startet ...\n" + ANSI_RESET +
                            ANSI_GREEN + "<<=================================>>\n" + ANSI_RESET);
    }



}
