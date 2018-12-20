package presentation;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import business.Main;
import business.Mp3Player;

/**
 *
 */
public class Mp3Controller {

    static Mp3Player mp3Player;
    //private int time = 0;
    Mp3ControllerView view;
    //private SimpleIntegerProperty timeProperty = new SimpleIntegerProperty();


    //private PlaylistManager playlistManager = new PlaylistManager();
    //private final String [] BEFEHLE = {"", "play", "pause", "stop", "volume", "quit", "playlist"};
    //private boolean pause = false;

    public Mp3Controller(Main application, Mp3Player mp3Player) {
        Mp3Controller.mp3Player = mp3Player;
        view = new Mp3ControllerView();
        view.changeWindow.setOnAction(e -> application.switchScene("PlaylistEditor"));
        //view.changeWindow2.setOnAction(e->application.switchScene("Playlistwahl"));
        view.stop.setOnAction(e -> mp3Player.stop());
        view.play.setOnAction(e -> play());
        view.volumeslider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mp3Player.volume(newValue.floatValue());
            }
        });
        view.getStylesheets().add(getClass().getResource("playerview.css").toExternalForm());
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
            view.play.getStyleClass().remove("playbutton");
            view.play.getStyleClass().add("pauseButton");
        } else {
            mp3Player.pause();
            System.out.println("pause");
            view.play.getStyleClass().remove("pauseButton");
            view.play.getStyleClass().add("playbutton");
        }
        System.out.println("play2");
    }
}

    /*
    public void setStart(int time)
    { //Slider info nötig
        if (!mp3Player.getPlaying()) {
            mp3Player.play(time);
            mp3Player.pause();
        } else {
            mp3Player.play(time);
        }
        this.time = time;
    } //Slider benötigt

    public void volume(float lautstaerke)
    {
        mp3Player.volume(lautstaerke);
    } // zweiter Slider benötigt
}*/





/*
    /*public void console(){

        boolean anAus = false;
        while (anAus == false) {
            System.out.println("Gib einen Befehl ein: ");
            System.out.println("play, pause, stop, volume, quit, playlist");
            String eingabe = StaticScanner.nextString();
            String[] parts = eingabe.split(" ");
            PlaylistManager trackList = new PlaylistManager();
            for (int i = 0; i < BEFEHLE.length; i++) {
                if (BEFEHLE[i].equals(parts[0])) {
                    switch (i) {
                        case 1:
                            if (parts.length > 1) {
                                System.out.println(parts[1]);
                                mp3Player.play(parts[1]);
                            }else{
                                mp3Player.play(0);
                            }
                            break;
                        case 2:
                            System.out.println("pause");
                            mp3Player.pause();
                            break;
                        case 3:
                            System.out.println("stop");
                            mp3Player.pause();
                            break;
                        case 4:
                            System.out.println("volume");
                            if (parts.length > 1) {
                                System.out.println(parts[1]);
                                float lautstaerke = Float.parseFloat(parts[1]);
                                System.out.println(lautstaerke);
                                mp3Player.volume(lautstaerke);
                            }
                            break;
                        case 5:
                            System.out.println("quit");
                            mp3Player.pause();

                            anAus = true;
                            break;
                        case 6:

                            bearbeiten();



                        default:
                            System.out.println("Ende Controller");
                    }
                }
            }
        }
        System.out.println("Ende");
    }
*

    private void bearbeiten(){


        System.out.println("Wollen sie eine Playlist erstellen? <y/n>");
        String eingabe = StaticScanner.nextString();
        if (eingabe.equals("y")){
            System.out.println("Wie soll die Playlist heissen?");
            eingabe = StaticScanner.nextString();
            eingabe = eingabeAbfrage(eingabe);
            String playlistFile = playlistManager.createPlaylist(eingabe);
            System.out.println("Playlist "+ eingabe + " wurde erstellt.");

            //____________________________________________________________________________________

            System.out.println("Welches der zur verfuegung stehenden Lieder moechten Sie der Playlist hinzufuegen?");

            // File Explorer

            for (int i = 0; i < playlistManager.getAllSongs().size(); i++) {
                System.out.println("Song " + (i + 1) + ": " + playlistManager.getAllSongs().get(i));
            } // Ausgabe der Daten
            eingabe= StaticScanner.nextString();
            eingabeAbfrage(eingabe);
            playlistManager.fileBeschreiben(playlistFile, "#EXTINF:" + eingabe + "\n"); // runtime, title

            //____________________________________________________________________________________

            System.out.println("Deine aktuelle Playlist: ");
            // In eine andere Methode auslagern / gar nicht erst im Controller machen...

            playlistManager.printPlaylistSongs(playlistFile);

            //____________________________________________________________________________________



            System.out.println("M�chtest du eines dieser Lieder aus der Playlist l�schen? (y/n)");
            // Nat�rlich auch noch verbessern, sodass falsche eingaben nicht alles zerst�ren
            eingabe = StaticScanner.nextString();
            if (eingabe.equals("y")) {
                System.out.println("Welches Lied soll gel�scht werden?");
                eingabe = StaticScanner.nextString();
                playlistManager.loeschenLied(playlistFile, eingabe); // Immer noch dieselbe playlistFile
                System.out.println("\n\n");
                playlistManager.printPlaylistSongs(playlistFile);


            } else System.out.println("Ja gut, dann net. Viel Spass mit deiner Playlist.");


            //____________________________________________________________________________________

            System.out.println("Möchtest du diese Playlist löschen? (y/n)");
            eingabe = StaticScanner.nextString();
            if (eingabe.equals("y")) {

                playlistManager.deletePlaylist(playlistFile);


            } else System.out.println("Ja gut, dann net. Viel Spass mit deiner Playlist.");


        }

    }


    private void playlist(){

        System.out.println("Welche Playlist moechten Sie laden?: ");
        playlistManager.loadFromFile("ausgabe");
        String eingabe = StaticScanner.nextString();
        playlistManager.loadFromFile(eingabe);

    }

    private String eingabeAbfrage(String eingabe){
        System.out.println("Sie haben "+ eingabe + " eingegeben. \nWenn sie damit einverstanden sind bestaetigen sie mit 'y',");
        System.out.println("anderenfalles geben sie ein 'n' ein.");
        String pruefung = StaticScanner.nextString();
        if (pruefung.equals("y")) {
            return eingabe;
        }else{
            System.out.println("\n Sie sind unzufrieden! Bitte neu eingeben: ");
            eingabe = StaticScanner.nextString();
            return eingabeAbfrage(eingabe);
        }
    }

}*/
