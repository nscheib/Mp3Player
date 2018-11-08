package sample;

import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

public class Mp3Player {

    SimpleMinim minim = new SimpleMinim(true);
    SimpleAudioPlayer audioPlayer ;



    public void play (String titel){
        System.out.print("play titel");

        audioPlayer = minim.loadMP3File("tracks\\" + titel + ".mp3");
        audioPlayer.play(0);
    }

    public void play(){
        System.out.print("play");

        audioPlayer = minim.loadMP3File("tracks\\groove.mp3");
        System.out.println("play1");
        audioPlayer.play();
        System.out.println("play2");
    }

    public void pause(){
        audioPlayer.pause();

    }

    public void volume(float lautstärke){
        System.out.println("blablabla");
        System.out.println(lautstärke);
        lautstärke = lautstärke - 60;
        audioPlayer.setGain(lautstärke);
    }

}
