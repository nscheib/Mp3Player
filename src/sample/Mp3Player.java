package sample;

import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import de.hsrm.mi.prog.util.StaticScanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;

import java.io.File;
import java.util.ArrayList;

public class Mp3Player {



    private ArrayList<String> liste = new ArrayList<String>();

    public String aktSong;

    SimpleMinim minim = new SimpleMinim(true);
    SimpleAudioPlayer audioPlayer = minim.loadMP3File("tracks/laenger.mp3");

    public void playSelected(String titel){
        aktSong = titel;
        if (audioPlayer.isPlaying()){
            audioPlayer.pause();
        }
        audioPlayer = minim.loadMP3File("tracks/" + titel ); // + ".mp3"
        audioPlayer.play(); // hier eigentlich noch time
    }

    public void play (String titel, int time){
        aktSong = titel;
        audioPlayer = minim.loadMP3File("tracks/" + titel ); // + ".mp3"
        audioPlayer.play(); // hier eigentlich noch time
    }

    public void play (int time){
        audioPlayer.pause();
        audioPlayer.play(time); // hier eigentlich noch time
    }

    public void play(){
        // Hier noch eine Auswahl implementieren
        if (audioPlayer.isPlaying()){
            audioPlayer.pause();
        }else audioPlayer.play();

    }


    public void pause(){
        audioPlayer.pause();

    }

    public void stop(){
        audioPlayer.pause();
        audioPlayer.rewind();
    }

    public int getTime(){
        return audioPlayer.position();
    }

    public void volume(float lautstaerke){
        System.out.println("blablabla");
        System.out.println(lautstaerke);
        lautstaerke = lautstaerke - 60;
        audioPlayer.setGain(lautstaerke);
    }

}
