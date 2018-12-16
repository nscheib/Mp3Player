package sample;

import gui.playerview.Mp3Controller;
import gui.playerview.PlaylistEditorController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {


    Mp3Controller mp3Controller;
    PlaylistEditorController playlistEditorController;
    private Mp3Player mp3Player ;
    Stage window;


    public void init(){
        mp3Player = new Mp3Player();
    }

    @Override
    public void start(Stage primaryStage){
        window = primaryStage;
        mp3Controller = new Mp3Controller(this, mp3Player);
        playlistEditorController = new PlaylistEditorController(this);
        window.setScene(new Scene (mp3Controller.getView(), 400, 400));
        window.show();
    }

    public void switchScene(String sceneName){

        switch (sceneName){

            case "PlaylistEditor":
                window.getScene().setRoot(playlistEditorController.getView());
                break;

            case "MP3player":
                window.getScene().setRoot(mp3Controller.getView());
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);

        /*TrackVerwalter trackListe = new TrackVerwalter();
        trackListe.songListe();*/
    }


}
