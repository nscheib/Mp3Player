package sample;

import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import de.hsrm.mi.prog.util.StaticScanner;

import java.io.File;
import java.util.ArrayList;

public class Mp3Player {

    private ArrayList<String> liste = new ArrayList<String>();


    SimpleMinim minim = new SimpleMinim(true);
    SimpleAudioPlayer audioPlayer = minim.loadMP3File("tracks/laenger.mp3");;


    public void play (String titel, int time){
        System.out.print("play titel");

        audioPlayer = minim.loadMP3File("tracks/" + titel + ".mp3");
        audioPlayer.play(time);
    }

    public void play (int time){
        audioPlayer.play();
    }

    public void play(){
        // Hier noch eine Auswahl implementieren
        audioPlayer.play();

    }

    public void pause(){
        audioPlayer.pause();
    }

    public void stop(){
        audioPlayer.rewind();
        audioPlayer.pause();


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

    public boolean getPlaying(){
        return audioPlayer.isPlaying();
    }

}
