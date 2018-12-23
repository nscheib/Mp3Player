package business;

import com.mpatric.mp3agic.Mp3File;
import ddf.minim.AudioMetaData;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Verwaltet die typischen Funktionen eines MP3-Players
 * wie abspielen, pausieren, stoppen, skippen etc.
 * Zum Abspielen wird Minim-Player verwendet.
 */
public class Mp3Player {

    SimpleMinim minim = new SimpleMinim(true);
    SimpleAudioPlayer audioPlayer = minim.loadMP3File("tracks/hurt.mp3");
    private AudioMetaData meta;
    private ArrayList<String> liste = new ArrayList<String>();
    private SimpleIntegerProperty timeProperty = new SimpleIntegerProperty();
    private ObservableList<Track> aktuellePlaylist;
    private Track aktuellerTrack;
    private int aktPositionTrack;
    private boolean repeat = false;
    private boolean shuffle = false;
    private SimpleIntegerProperty currentTime;


    public Mp3Player() { currentTime = new SimpleIntegerProperty();}

    public void playSelected(String titel){
        if (audioPlayer.isPlaying()){
            currentTime = null;
            audioPlayer.pause();
        }

        Thread timer = new Thread() {
            public void run() {

                for (int i = 0; i < 2000; i++) {
                    currentTime.set(i);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        timer.setDaemon(true);
        timer.start();

        audioPlayer = minim.loadMP3File("tracks/" + titel +".mp3"); // + ".mp3"
        audioPlayer.play();
    }


    public void play() {

        audioPlayer.play();


    }

    public void pause()
    {
        audioPlayer.pause();
    }

    public void stop() {
        audioPlayer.rewind();
        audioPlayer.pause();
    }

    public int getTime()
    {
        return audioPlayer.position();
    }


    public void setShuffle() {
        if (shuffle){
            shuffle = false;
        } else shuffle = true;
    }

    public void setRepeat() {
        if (repeat){
            repeat = false;
        }else repeat = true;
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


    public void setAktuellePlaylist(ObservableList<Track> inhaltListViewR) {
        this.aktuellePlaylist = inhaltListViewR;
    }

    public void setAktuellerTrack(Track newValue) {
        this.aktuellerTrack = newValue;
    }

    public void playNext() {

        // Randomizer an ?
        if (shuffle == true ){
            int random_trackposition = new Random().nextInt(aktuellePlaylist.size());
            System.out.println(random_trackposition);
            Track random_track = aktuellePlaylist.get(random_trackposition);
            setAktuellerTrack(random_track);
            setAktuellePosition(random_trackposition);
            stop();
            playSelected(aktuellerTrack.getFileName());

            // Loop aus ? also ende der liste == ende der liste
        } else if (repeat == false && aktPositionTrack == aktuellePlaylist.size()-1){
            setAktuellerTrack(aktuellePlaylist.get(aktuellePlaylist.size()-1));
            setAktuellePosition(aktuellePlaylist.size()-1);
            stop();
            playSelected(aktuellerTrack.getFileName());
        } else {

            // Ende der Liste ==> anfang der liste
            if (aktPositionTrack == aktuellePlaylist.size() - 1) {
                setAktuellerTrack(aktuellePlaylist.get(0));
                setAktuellePosition(0);
                stop();
                playSelected(aktuellerTrack.getFileName());

            } else {

                // Einfach nächsten Song spielen...
                for (int i = 0; i <= aktuellePlaylist.size() - 1; i++) {

                    if (aktuellePlaylist.get(i).getFileName() == aktuellerTrack.getFileName()) {

                        setAktuellerTrack(aktuellePlaylist.get(i + 1));
                        setAktuellePosition(i + 1);
                        stop();
                        playSelected(aktuellerTrack.getFileName());
                        break;

                    }
                }
            }
        }
    }

    public void playPrevious() {

        if (shuffle == true ){
            int random_trackposition = new Random().nextInt(aktuellePlaylist.size()-1);
            Track random_track = aktuellePlaylist.get(random_trackposition);
            setAktuellerTrack(random_track);
            setAktuellePosition(random_trackposition);
            stop();
            playSelected(aktuellerTrack.getFileName());

            // Loop aus ? also anfang der liste == anfang der liste
        } else if (repeat == false && aktPositionTrack == 0) {
            setAktuellerTrack(aktuellePlaylist.get(0));
            setAktuellePosition(0);
            stop();
            playSelected(aktuellerTrack.getFileName());
        }

        else if (aktPositionTrack == 0 ){
            setAktuellerTrack(aktuellePlaylist.get(aktuellePlaylist.size()-1));
            setAktuellePosition(aktuellePlaylist.size()-1);
            stop();
            playSelected(aktuellerTrack.getFileName());

        }
        else {

            for (int i = 0; i <= aktuellePlaylist.size() -1;i++){

                if (aktuellePlaylist.get(i).getFileName() == aktuellerTrack.getFileName()){

                    setAktuellerTrack(aktuellePlaylist.get(i-1));
                    setAktuellePosition(i-1);
                    stop();
                    playSelected(aktuellerTrack.getFileName());
                    break;

                }
            }
        }
    }

    public void setAktuellePosition(int aktPositionTrack) {
        this.aktPositionTrack = aktPositionTrack;
    }

    public SimpleIntegerProperty currentTimeProperty() {
        return currentTime;
    }
}

