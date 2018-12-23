package presentation;

import business.Track;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import business.Main;
import business.Mp3Player;

/**
 *
 */
public class Mp3Controller {

    static Mp3Player mp3Player;
    Mp3ControllerView view;

    public Mp3Controller(Main application, Mp3Player mp3Player) {
        Mp3Controller.mp3Player = mp3Player;
        view = new Mp3ControllerView();

        view.getChangeWindowButton().setOnAction(e -> application.switchScene("PlaylistEditor"));
        view.getStopButton().setOnAction(e -> mp3Player.stop());
        view.getPlayButton().setOnAction(e -> play());
        view.getSkipRightButton().setOnAction(e -> mp3Player.playNext());
        view.getSkipLeftButton().setOnAction(e-> mp3Player.playPrevious());
        view.getRepeat().setOnAction(e-> mp3Player.setRepeat());
        view.getShuffle().setOnAction(e->mp3Player.setShuffle());

        view.getVolumeslider().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mp3Player.volume(newValue.floatValue());
            }
        });
        view.getStylesheets().add(getClass().getResource("playerview.css").toExternalForm());

        view.getImgview().imageProperty().addListener(new ChangeListener<Image>() {
            @Override
            public void changed(ObservableValue<? extends Image> observable, Image oldValue, Image newValue) {
                view.getImgview().setImage(newValue);
            }
        });


        mp3Player.currentTimeProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(
                        () -> view.getTime().setText(newValue.toString())
                );
            }

        });

        view.getTimeSlider().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                view.getTimeSlider().setValue(newValue.floatValue());
            }
        });

        view.getTitle().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                view.getTitle().setText(newValue);
            }
        });

    }

    public static Mp3Player getMp3Player() {
        return mp3Player;
    }
    public Pane getView() {
        return view;
    }

    /**
     * Methode, die den Zustand des Play und Pause Buttons verwaltet.
     * <p>
     * Sodass, wenn ein Lied spielt der Play Button dargestellt wird
     * und falls Lieder pausiert werden
     * wird der Pause Button abgebildet.
     */
    public void play() {
        if (!mp3Player.getPlaying()) {
            mp3Player.play();
            System.out.println("abspielen");
            view.getPlayButton().getStyleClass().remove("playbutton");
            view.getPlayButton().getStyleClass().add("pauseButton");
        } else {
            mp3Player.pause();
            System.out.println("pause");
            view.getPlayButton().getStyleClass().remove("pauseButton");
            view.getPlayButton().getStyleClass().add("playbutton");
        }
        System.out.println("play2");
    }
}