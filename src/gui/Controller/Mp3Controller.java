package gui.Controller;

import businessLogic.Main;
import businessLogic.Mp3Player;
import businessLogic.Track;
import gui.view.Mp3View;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;

import java.time.format.DateTimeFormatter;


/**
 * Klasse Mp3Controller händelt die einzelen Funktionen von Gui-Elementen und verwaltet Listener,
 * dient als Schnittstelle zwischen Gui und der Businesslogic
 */
public class Mp3Controller {

    private Mp3View view;

    // Constructor
    public Mp3Controller(Main application, Mp3Player player) {

        // Erstellt eine neue View
        view = new Mp3View();

        // Stylesheet, aus welcher Datei die Einstellung gelesen werden sollen
        view.getStylesheets().add(getClass().getResource("/gui/css/style.css").toExternalForm());

        // Button Funktionen
        view.getMp3PlayerButton().setOnAction(e -> application.switchScene("MP3Player"));
        view.getPlayListButton().setOnAction(e -> application.switchScene("PlayListEditor"));
        view.getGameButton().setOnAction(e -> application.switchScene("Game"));
        //view.getSettingButton().setOnAction(e -> application.switchScene("Settings"));
        view.getStopButton().setOnAction(e -> stop(player));
        view.getPlayButton().setOnAction(e -> play(player));

        view.getSkipright().setOnAction(e -> skipright(player));
        view.getSkipleft().setOnAction(e -> skipleft(player));
        view.getTimeslider().setOnMouseReleased(event -> player.skip((int) view.getTimeslider().getValue()));

        view.getLoop().setOnAction(e -> player.loop());
        //view.getShuffle().setOnAction(e -> player.shuffle());
        //view.getRepeat().setOnAction(e -> player.repeate());

        // Setzt die ersten Eigenschaften
        view.getTitle().setText(player.getTrack().getTitle());
        view.getInterpret().setText(player.getTrack().getInterpret());
        view.getImgview().setImage(player.getTrack().getImage());
        view.getTimeslider().setMin(0);
        view.getTimeslider().setMax(player.getTrack().getLenght());
        view.getTimeslider().setMajorTickUnit(Math.round(player.getTrack().getLenght()));
        view.getTimeZwei().setText(player.getTrack().getLenghtAsString());


        // TimeLabel Listener
        player.currentTimeProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(
                        () -> {
                            view.getTime().setText(setTime(newValue));
                            view.getTimeslider().setValue(newValue.doubleValue());
                        }
                );
            }
        });

        // Volumeslider Listener
        view.getVolumeslider().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(
                        () -> player.volume(newValue.floatValue())
                );
            }
        });

        // Track Listener
        player.trackProperty().addListener(new ChangeListener<Track>() {
            @Override
            public void changed(ObservableValue<? extends Track> observable, Track oldTrack, Track newTrack) {
                Platform.runLater(
                        () -> aktualisieren(newTrack, player)
                );
            }
        });
    }

    /**
     * Methode verarbeitet den Klick auf den rechten Skipbutton
     * @param player
     */
    private void skipright(Mp3Player player) {
        player.skipright();
        changePlayButton(player);
    }

    /**
     * Methode verarbeitet den Klick auf den linken Skipbutton
     * @param player
     */
    private void skipleft(Mp3Player player) {
        player.skipleft();
        changePlayButton(player);
    }

    /**
     * Methode setzt die neuen Werte ein, wenn ein neues Trackobject geladen wird.
     * @param newTrack
     */
    private void aktualisieren(Track newTrack,Mp3Player player) {
        view.getTimeslider().setMax(newTrack.getLenght());
        view.getTitle().setText(newTrack.getTitle());
        view.getInterpret().setText(newTrack.getInterpret());
        view.getTimeZwei().setText(setTime(newTrack.getLenght()));
        view.getImgview().setImage(newTrack.getImage());
        changePlayButton(player);
    }

    /**
     * Methode setzt die Zeit in in spezielles Format
     * @param newValue
     * @return
     */
    private String setTime(Number newValue) {
        int minutes = newValue.intValue() / (60 * 1000);
        int seconds = (newValue.intValue() / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Methode wechselt den PlayButton beim benutzen in den PauseButton um und hält den Mp3player an
     * oder führt diesen fort.
     * @param player gibt den Player an die Methode weiter
     */
    public void play(Mp3Player player) {
        if(!player.isPlaying()) {
            player.play();
        } else {
            player.pause();
        }
        changePlayButton(player);
    }

    /**
     * Methode wechelt beim benutzen des StopButtons, den PauseButton in einen Playbutton
     * @param player
     */
    public void stop(Mp3Player player) {
            player.stopPlayer();
            changePlayButton(player);
    }


    /**
     * Methode überprüft ob der Playbutton oder Pausebutton angezeigt werden muss
     * @param player
     */
    public void changePlayButton(Mp3Player player) {
        if(player.getpause()) {
            view.getPlayButton().getStyleClass().remove("pauseButton");
            view.getPlayButton().getStyleClass().add("playButton");
        } else {
            view.getPlayButton().getStyleClass().remove("playButton");
            view.getPlayButton().getStyleClass().add("pauseButton");
        }
    }

    /**
     * Methode gibt die View zurück
     * @return
     */
    public Pane getView() { return view; }

}
