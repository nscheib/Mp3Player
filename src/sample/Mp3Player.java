package sample;

import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import de.hsrm.mi.prog.util.StaticScanner;

import java.io.File;
import java.util.ArrayList;

public class Mp3Player {

    private ArrayList<String> liste = new ArrayList<String>();

    SimpleMinim minim = new SimpleMinim(true);
    SimpleAudioPlayer audioPlayer ;


    public void play (String titel){
        System.out.print("play titel");

        audioPlayer = minim.loadMP3File("tracks/" + titel + ".mp3");
        audioPlayer.play(0);
    }

    public void play(){
        // Hier noch eine Auswahl implementieren
        System.out.print("play");

        audioPlayer = minim.loadMP3File("tracks/groove.mp3");
        System.out.println("play1");
        audioPlayer.play();
        System.out.println("play2");
    }

    public void pause(){
        audioPlayer.pause();

    }

    public void volume(float lautstaerke){
        System.out.println("blablabla");
        System.out.println(lautstaerke);
        lautstaerke = lautstaerke - 60;
        audioPlayer.setGain(lautstaerke);
    }

}
