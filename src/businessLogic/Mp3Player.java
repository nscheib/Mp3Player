package businessLogic;

import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Random;


public class Mp3Player extends Thread implements Runnable {

    private SimpleMinim minim;
    private SimpleAudioPlayer player;
    private PlayListManager playListManager;
    private ArrayList<String> aktuellePlayList;
    private int random;
    private SimpleObjectProperty<Track> track = new SimpleObjectProperty<Track>();
    private Track aktuellerTrack;
    private ObservableList<Track> auswahlPlaylistAllSongs;
    private int aktuellePosInPlayList = -1;
    private boolean loop;
    private SimpleIntegerProperty currentTime;
    private Thread playingThread;
    private boolean pause, initialisiert = false;

    private SimpleIntegerProperty currentMode; // 0 mp3player, 1 game

//    private Thread mp3PlayerThread;

    //Constructor
    public Mp3Player() {
        // Start des Spiels setzt, da man im mp3Player startet, CurrentMode auf 0
        currentMode = new SimpleIntegerProperty(0);
        this.playListManager = new PlayListManager();
        this.minim = new SimpleMinim(true);
        this.aktuellePlayList = playListManager.loadChoosenPlayList("Playlisten/Standart.m3u");
        this.auswahlPlaylistAllSongs = playListManager.returnPlaylistSongs("Playlisten/Standart.m3u");
        this.currentTime = new SimpleIntegerProperty();
        setRadomSong(false);
        this.pause = false;
    }


    /**
     * Methode startet den Mp3Player und spielt das ausgewählte Lied ab
     */
    public void play() {
        if(!initialisiert) {
            loadSong();
        }
        initialisiert = true;
        pause = false;

        if(!player.isPlaying() || !pause) {
            player.play();

            // Erstellt einen neuen Thread.
            this.playingThread = new Thread(() -> {

                // Solange der Player spielt und nicht interrupted wurde, wird die Zeit gesetzt
                while (player.isPlaying() && !playingThread.isInterrupted()) {
                    try {
                        // Wird benötigt um die Zeit setzten zu können. 1000 setzt jede Sekunde die Zeit neu
                        playingThread.sleep((1000));

                        Platform.runLater( ()-> currentTime.setValue(player.position()));
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted!");
                        playingThread.interrupt();
                    }
                }
                // Wenn eine Playlist geladen und der Thread nicht interrupted ist, dann lade das nächste Lied oder das
                // gleiche wenn loop==true
                if ((aktuellePlayList != null) && !playingThread.isInterrupted()) {
                    Platform.runLater( ()-> setNextTrack(loop));
                }
            });
            this.playingThread.start();
        } else {
            playingThread.interrupt();
        }
    }

    /**
     * Methode die ein bestimmtes ausgewähltes Lied im Mp3Player speichert und diesen abspielt
     * @param selectedTrack
     */
    public void playSelected(Track selectedTrack) {
        if(initialisiert) {
            stopPlayer();
            aktuellerTrack = selectedTrack;
            loadSong();
            play();
        } else {
            aktuellerTrack = selectedTrack;
            loadSong();
            play();
        }
    }

    /**
     * Methode stoppt den Mp3Player
     */
    public void stopPlayer() {
        if(initialisiert) {
            pause = true;
            playingThread.interrupt();
        }
        loadSong();
    }

    /**
     * Methode pausiert den Mp3Player
     */
    public void pause() {
        pause = true;
        playingThread.interrupt();
        player.pause();
        System.out.println("Pausiert!");
    }

    /**
     * Methode um nach in einer Playlist nach rechts zu skippen
     * Abfrage ob Position schon gesetzt wurde. Zieht einen Zähler von der Position ab. Wenn die Position >= 0 , loop==true,
     * Position nicht größer als die aktuelle Playlistgröße, dann gehe in den PLManager, rufe die Methode getSongFromList auf
     * in dieser erhält man ein Object des Typs Track. Den richtigen Track erhält man durch das mitgeben eines Strings den wir
     * aus unserer aktuellen Playlist mit dem bestimmten Positionsindex erhalten.
     * Das Track-Object wird im Mp3Player gespeichert und in der Methode loadSong() geladen. Mit play() wird der Song abgespielt.
     */
    public void skipright() {
        if(initialisiert) { playingThread.interrupt(); }
        if(aktuellePosInPlayList == -1 && !initialisiert) { setAktuellePosInPlayList(); }

        aktuellePosInPlayList++;

        if((aktuellePosInPlayList >= 0)  && (aktuellePosInPlayList <= auswahlPlaylistAllSongs.size()-1)) {
            this.aktuellerTrack = playListManager.getOneSongFromList(aktuellePlayList.get(aktuellePosInPlayList));
            loadSong();
            play();

        } else if(aktuellePosInPlayList >= auswahlPlaylistAllSongs.size()-1) {
            aktuellePosInPlayList = 0;
            this.aktuellerTrack = playListManager.getOneSongFromList(aktuellePlayList.get(aktuellePosInPlayList));
            loadSong();
            play();
        }
    }

    /**
     * Methode um nach in einer Playlist nach links zu skippen
     * Abfrage ob Position schon gesetzt wurde. Zieht einen Zähler von der Position ab. Wenn die Position >= 0 , loop==true,
     * Position nicht größer als die aktuelle Playlistgröße, dann gehe in den PLManager, rufe die Methode getSongFromList auf
     * in dieser erhält man ein Object des Typs Track. Den richtigen Track erhält man durch das mitgeben eines Strings den wir
     * aus unserer aktuellen Playlist mit dem bestimmten Positionsindex erhalten.
     * Das Track-Object wird im Mp3Player gespeichert und in der Methode loadSong() geladen. Mit play() wird der Song abgespielt.
     */
    public void skipleft() {
        if(initialisiert) { playingThread.interrupt(); }
        if(aktuellePosInPlayList == 0) { setAktuellePosInPlayList(); }

        aktuellePosInPlayList--;

        if((aktuellePosInPlayList >= 0) && (aktuellePosInPlayList <= auswahlPlaylistAllSongs.size()-1)) {
            this.aktuellerTrack = playListManager.getOneSongFromList(aktuellePlayList.get(aktuellePosInPlayList));
            loadSong();
            play();
        }  else if(aktuellePosInPlayList < 0) {
            aktuellePosInPlayList = auswahlPlaylistAllSongs.size()-1;
            this.aktuellerTrack = playListManager.getOneSongFromList(aktuellePlayList.get(aktuellePosInPlayList));
            loadSong();
            play();
        }
    }

    /**
     * Methode verändert die Zeit des Players mit dem erhaltenen Wert. Wird für den Slider benötigt.
     * @param milis
     */
    public void skip(double milis) {
        System.out.println(milis);
        int milSec = (int) milis;
        player.skip(milSec - player.position());

    }

    /**
     * Methode setzt die aktuelle Position in der Playlist wenn diese noch keinen Wert hat
     */
    private void setAktuellePosInPlayList() {
        for(int i = 0; i < auswahlPlaylistAllSongs.size()-1; i++) {
            if (auswahlPlaylistAllSongs.get(i) == aktuellerTrack) {
                aktuellePosInPlayList = i;
            }
        }
    }

    /**
     * Methode setzt den loop auf true/false, je nachdem wie der Button gedrückt wurde
     */
    public void loop() {
        this.loop = !loop;
    }

    /**
     * Methode die eine PlayList läd und daraus ein Random Track abspielt
     */
    public void setRadomSong(boolean loop) {
        if(!loop) {
            random = new Random().nextInt(aktuellePlayList.size()-1);
            aktuellePosInPlayList = random;
            this.aktuellerTrack = playListManager.getOneSongFromList(aktuellePlayList.get(random));
            loadSong();
        }
    }

    /**
     * Methode überprüft ob loop aktiviert ist und spielt entweder das gleiche Lied oder das nächste Lieb ab
     * @param loop
     */
    public void setNextTrack(boolean loop) {

        if(loop) {
            this.aktuellerTrack = playListManager.getOneSongFromList(aktuellePlayList.get(aktuellePosInPlayList));
            loadSong();
            play();
        } else if(!loop) {
            skipright();
        }
    }

    /**
     * Methode um die Lautstärke des Players zwischen 0-100 zu bestimmen
     */
    public void volume(float vol) {
        player.setGain((float) (Math.log(vol/100)*20));
    }

    /**
     * Methode läd einen Track mit dem Pfad(SoundFile) des Track Objekts
     * @param
     */
    private void loadSong() {
        minim.stop();
        track.set(aktuellerTrack);
        player = minim.loadMP3File(this.aktuellerTrack.getSoundFile());
    }

    /**
     * Methode gibt den im Mp3Player stellten PlayListmanager zurück
     * @return playListManager
     */
    public PlayListManager getPlayListManager() {
        return playListManager;
    }

    /**
     * Method checks if the player is still running
     * @return true or false
     */
    public boolean isPlaying() {
        return player.isPlaying();
    }

    /**
     * Methode speichert die aktuelle Playlist im Mp3Player
     * @param auswahlPlaylist
     */
    public void setAktuellePlayList(ObservableList<Track> auswahlPlaylist) {
        this.auswahlPlaylistAllSongs = auswahlPlaylist;
    }

    /**
     * Methode gibt die currentTime zurück
     * @return
     */
    public SimpleIntegerProperty currentTimeProperty() { return currentTime; }

    /**
     * Methode gibt ein Track Objekt als SimpleObjectProperty zurück
     * @return
     */
    public final SimpleObjectProperty<Track> trackProperty() {
        return this.track;
    }

    /**
     * Methode gibt den aktuellen Track (obj) zurück
     * @return
     */
    public Track getTrack() { return aktuellerTrack; }

    /**
     * Methode gibt zurück ob der Player pausiert ist oder abspielt
     * @return
     */
    public boolean getpause() { return pause; }


    public void setCurrentTime(int currentTime) {
        this.currentTime.setValue(currentTime);
    }
}