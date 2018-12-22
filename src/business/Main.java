package business;

import javafx.application.Platform;
import presentation.Mp3Controller;
import presentation.PlaylistEditorController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    Mp3Controller mp3Controller;
    PlaylistEditorController playlistEditorController;
    private Mp3Player mp3Player;
    Stage window;

    public void init(){
        mp3Player = new Mp3Player();
    }

    @Override
    public void start(Stage primaryStage){
        window = primaryStage;
        mp3Controller = new Mp3Controller(this, mp3Player);
        playlistEditorController = new PlaylistEditorController(this, mp3Player,mp3Controller);
        window.setScene(new Scene (mp3Controller.getView(), 600, 600));
        window.setMinHeight(600);
        window.setMinWidth(600);
        window.setMaxHeight(700);
        window.setMaxWidth(700);
        window.show();
        //beendet alles sobald das x gedrückt wird um das Fenster zu schließen
        window.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public void switchScene(String sceneName)
    {
        switch (sceneName){
            case "PlaylistEditor":
                window.getScene().setRoot(playlistEditorController.getView());
                break;
            case "MP3player":
                window.getScene().setRoot(mp3Controller.getView());
                break;
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }


}
