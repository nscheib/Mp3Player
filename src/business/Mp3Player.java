package business;

import ddf.minim.AudioMetaData;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.SimpleIntegerProperty;
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
    SimpleAudioPlayer audioPlayer = minim.loadMP3File("tracks/01_LastMembrane.mp3");
    private AudioMetaData meta;
    private ArrayList<String> liste = new ArrayList<String>();
    private SimpleIntegerProperty timeProperty = new SimpleIntegerProperty();
    private int milliS;
    private ObservableList<Track> aktuellePlaylist;
    private Track aktuellerTrack;
    private int aktPositionTrack;
    private boolean repeat = false;
    private boolean shuffle = false;






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


    public void setShuffle(){
        if (shuffle){
            shuffle = false;
        } else shuffle = true;
    }

    public void setRepeat(){
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




    public SimpleIntegerProperty getTimeProperty() {
        return timeProperty;
    }


    public void setAktuellePlaylist(ObservableList<Track> inhaltListViewR) {
        this.aktuellePlaylist = inhaltListViewR;
    }

    public void setAktuellerTrack(Track newValue) {
        this.aktuellerTrack = newValue;
    }

    public ObservableList<Track> getAktuellePlaylist() {return aktuellePlaylist; }

    public Track getAktuellerTrack() { return aktuellerTrack; }

    public void playNext() {

//        System.out.println("AKTTRACK: "+aktuellerTrack.getFileName());
//        System.out.println("AKTPOSTRACK: "+aktPositionTrack);
//        System.out.println("GROESSEPLAYLIT: "+ (aktuellePlaylist.size() -1)); // size zeigt richtige größe an, also 2 für 2 elemente anstatt 1...

        // Randomizer an ?
        if (shuffle == true ){
            int random_trackposition = new Random().nextInt(aktuellePlaylist.size()-1);
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




//        System.out.println("AKTTRACK2: "+aktuellerTrack.getFileName());
//        System.out.println("AKTPOSTRACK2: "+aktPositionTrack);
        
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
}

/*
public void play (String titel, int time)
    {
        System.out.print("play titel");
        audioPlayer = minim.loadMP3File("tracks/" + titel + ".mp3");
        audioPlayer.play(time);
    }
 */
