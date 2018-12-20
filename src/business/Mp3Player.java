package business;

import ddf.minim.AudioMetaData;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Verwaltet die typischen Funktionen eines MP3-Players
 * wie abspielen, pausieren, stoppen, skippen etc.
 * Zum Abspielen wird Minim-Player verwendet.
 */
public class Mp3Player {

    SimpleMinim minim = new SimpleMinim(true);
    SimpleAudioPlayer audioPlayer = minim.loadMP3File("tracks/01_LastMembrane.mp3");
    private AudioMetaData meta;
    private ArrayList<String> liste = new ArrayList<String>();
    private SimpleIntegerProperty timeProperty = new SimpleIntegerProperty();
    Track track = new Track();
    private int milliS;

    public void playSelected(String titel){
        if (audioPlayer.isPlaying()){
            audioPlayer.pause();
        }
        audioPlayer = minim.loadMP3File("tracks/" + titel +".mp3"); // + ".mp3"
        audioPlayer.play(); // hier eigentlich noch time
    }

    public void play (int time)
    {
        audioPlayer.play();
    }

    public void play (String titel, int time){
        audioPlayer = minim.loadMP3File("tracks/" + titel + ".mp3"); //
        audioPlayer.play(); // hier eigentlich noch time
    }

    public void play()
    {
        audioPlayer.play();
    }

    public void pause()
    {
        audioPlayer.pause();
    }

    public void stop()
    {
        audioPlayer.rewind();
        audioPlayer.pause();
    }

    public int getTime()
    {
        return audioPlayer.position();
    }

    /**
     * Verwaltet die Funktion eines Lautstaerkenreglers,
     * sodass die Lautstaerke eines Liedes
     * individuell veraendert werden kann
     * @param lautstaerke
     */
    public void volume(float lautstaerke)
    {
        audioPlayer.setGain( (float) Math.log(lautstaerke/100) * 20);
    }

    /**
     * Verwalet den Status des Players.
     *
     * Moeglich ist, dass der Player gerade ein Lied spielt
     * oder eben nicht.
     * @return
     */
    public boolean getPlaying()
    {
        return audioPlayer.isPlaying();
    }

    /**
     * Methode von SimpleMinim um an die Metadaten
     * der einzelnen Trackobjekte zu kommen.
     *
     * Werden dann im Mp3ControllerView in den erstellten
     * Labels angezeigt.
     * @return Metadaten der Lieder
     */
    public AudioMetaData getData (){
        meta = this.audioPlayer.getMetaData();
        return meta;
    }

    public Mp3Player getMp3Player () {
        return getMp3Player();
    }



    public SimpleIntegerProperty getTimeProperty() {
        return timeProperty;
    }


}

/*
public void play (String titel, int time)
    {
        System.out.print("play titel");
        audioPlayer = minim.loadMP3File("tracks/" + titel + ".mp3");
        audioPlayer.play(time);
    }
 */
