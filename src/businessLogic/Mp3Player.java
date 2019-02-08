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
    private ArrayList<String> currentPlaylist;
    private int random;
    private SimpleObjectProperty<Track> track = new SimpleObjectProperty();
    private Track currentTrack;
    private ObservableList<Track> allChoosablePlaylistSongs;
    private int currentPosPlaylist = -1;
    private boolean loop;
    private SimpleIntegerProperty currentTime;
    private Thread playingThread;
    private boolean pause, initialized = false;
    private SimpleIntegerProperty currentMode;      // 0 mp3player, 1 game

    /**
     * Default-Konstruktor
     */
    public Mp3Player() {
        // Start des Spiels setzt, da man im mp3Player startet, CurrentMode auf 0
        currentMode = new SimpleIntegerProperty(0);
        this.playListManager = new PlayListManager();
        this.minim = new SimpleMinim(true);
        this.currentPlaylist = playListManager.loadChoosenPlayList("Playlisten/Standart.m3u");
        this.allChoosablePlaylistSongs = playListManager.returnPlaylistSongs("Playlisten/Standart.m3u");
        this.currentTime = new SimpleIntegerProperty();
        setRadomSong(false);
        this.pause = false;
    }


    /**
     * Methode startet den mp3player und spielt das gewaehlte Lied ab.
     */
    public void play() {
        if(!initialized) {
            loadSong();
        }
        initialized = true;
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
                if ((currentPlaylist != null) && !playingThread.isInterrupted()) {
                    Platform.runLater( ()-> setNextTrack(loop));
                }
            });
            this.playingThread.start();
        } else {
            playingThread.interrupt();
        }
    }

    /**
     * Methode die ein bestimmtes ausgewaehltes Lied im mp3player speichert und dieses abspielt.
     * @param selectedTrack gewaehlter Track
     */
    public void playSelected(Track selectedTrack) {
        if(initialized) {
            stopPlayer();
            currentTrack = selectedTrack;
            loadSong();
            play();
        } else {
            currentTrack = selectedTrack;
            loadSong();
            play();
        }
    }

    /**
     * Methode stoppt den Mp3player.
     */
    public void stopPlayer() {
        if(initialized) {
            pause = true;
            playingThread.interrupt();
        }
        loadSong();
    }

    /**
     * Methode pausiert den Mp3player.
     */
    public void pause() {
        pause = true;
        playingThread.interrupt();
        player.pause();
        System.out.println("Pausiert!");
    }

    /**
     * Methode um  in einer Playlist nach rechts zu skippen
     * Abfrage ob Position schon gesetzt wurde. Zieht einen Zaehler von der Position ab. Wenn die Position >= 0 , loop==true,
     * Position nicht groeßer als die aktuelle Playlistgroeße, dann gehe in den PLManager, rufe die Methode getSongFromList auf
     * in dieser erhaelt man ein Object des Typs Track. Den richtigen Track erhält man durch das mitgeben eines Strings den wir
     * aus unserer aktuellen Playlist mit dem bestimmten Positionsindex erhalten.
     * Das Track-Object wird im mp3player gespeichert und in der Methode loadSong() geladen. Mit play() wird der Song abgespielt.
     */
    public void skipright() {
        if(initialized) { playingThread.interrupt(); }
        if(currentPosPlaylist == -1 && !initialized) { setAktuellePosInPlayList(); }
        currentPosPlaylist++;
        if((currentPosPlaylist >= 0)  && (currentPosPlaylist <= allChoosablePlaylistSongs.size()-1)) {
            this.currentTrack = playListManager.getOneSongFromList(currentPlaylist.get(currentPosPlaylist));
            loadSong();
            play();
        } else if(currentPosPlaylist >= allChoosablePlaylistSongs.size()-1) {
            currentPosPlaylist = 0;
            this.currentTrack = playListManager.getOneSongFromList(currentPlaylist.get(currentPosPlaylist));
            loadSong();
            play();
        }
    }

    /**
     * Methode um nach in einer Playlist nach links zu skippen
     * Abfrage ob Position schon gesetzt wurde. Zieht einen Zähler von der Position ab. Wenn die Position >= 0 , loop==true,
     * Position nicht groeßer als die aktuelle Playlistgroeße, dann gehe in den PLManager, rufe die Methode getSongFromList auf
     * in dieser erhaelt man ein Object des Typs Track. Den richtigen Track erhaelt man durch das mitgeben eines Strings den wir
     * aus unserer aktuellen Playlist mit dem bestimmten Positionsindex erhalten.
     * Das Track-Object wird im mp3player gespeichert und in der Methode loadSong() geladen. Mit play() wird der Song abgespielt.
     */
    public void skipleft() {
        if(initialized) { playingThread.interrupt(); }
        if(currentPosPlaylist == 0) { setAktuellePosInPlayList(); }
        currentPosPlaylist--;
        if((currentPosPlaylist >= 0) && (currentPosPlaylist <= allChoosablePlaylistSongs.size()-1)) {
            this.currentTrack = playListManager.getOneSongFromList(currentPlaylist.get(currentPosPlaylist));
            loadSong();
            play();
        }  else if(currentPosPlaylist < 0) {
            currentPosPlaylist = allChoosablePlaylistSongs.size()-1;
            this.currentTrack = playListManager.getOneSongFromList(currentPlaylist.get(currentPosPlaylist));
            loadSong();
            play();
        }
    }

    /**
     * Methode veraendert die Zeit des Players mit dem erhaltenen Wert.
     * Wird fuer den Slider benoetigt.
     * @param milis  die Milisekunden
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
        for(int i = 0; i < allChoosablePlaylistSongs.size()-1; i++) {
            if (allChoosablePlaylistSongs.get(i) == currentTrack) {
                currentPosPlaylist = i;
            }
        }
    }

    /**
     * Methode setzt den loop auf true/false, je nachdem wie der Button gedrueckt wurde
     */
    public void loop() {
        this.loop = !loop;
    }

    /**
     * Methode die eine PlayList laedt und daraus ein Random Track abspielt
     */
    public void setRadomSong(boolean loop) {
        if(!loop) {
            random = new Random().nextInt(currentPlaylist.size()-1);
            currentPosPlaylist = random;
            this.currentTrack = playListManager.getOneSongFromList(currentPlaylist.get(random));
            loadSong();
        }
    }

    /**
     * Methode überprüft ob loop aktiviert ist und spielt entweder das gleiche Lied oder das nächste Lieb ab
     * @param loop
     */
    public void setNextTrack(boolean loop) {
        if(loop) {
            this.currentTrack = playListManager.getOneSongFromList(currentPlaylist.get(currentPosPlaylist));
            loadSong();
            play();
        } else if(!loop) {
            skipright();
        }
    }

    /**
     * Methode um die Lautstaerke des Players zwischen 0-100 zu bestimmen
     */
    public void volume(float vol) {
        player.setGain((float) (Math.log(vol/100)*20));
    }

    /**
     * Methode laedt einen Track mit dem Pfad(SoundFile) des Track Objekts
     * @param
     */
    private void loadSong() {
        minim.stop();
        track.set(currentTrack);
        player = minim.loadMP3File(this.currentTrack.getSoundFile());
    }

    /**
     * Methode gibt den im mp3player stellten PlayListmanager zurueck
     * @return playListManager
     */
    public PlayListManager getPlayListManager() {
        return playListManager;
    }

    /**
     * Methode ueberprueft, ob der Player am spielen ist.
     * @return true or false
     */
    public boolean isPlaying() {
        return player.isPlaying();
    }

    /**
     * Methode speichert die aktuelle Playlist im mp3player
     * @param choosablePlaylist gewaehlte Playlist
     */
    public void setCurrentPlaylist(ObservableList<Track> choosablePlaylist) {
        this.allChoosablePlaylistSongs = choosablePlaylist;
    }

    /**
     * Methode gibt die currentTime zurueck
     * @return aktuelle Zeit
     */
    public SimpleIntegerProperty currentTimeProperty() { return currentTime; }

    /**
     * Methode gibt ein Track Objekt als SimpleObjectProperty zurueck
     * @return
     */
    public final SimpleObjectProperty<Track> trackProperty() {
        return this.track;
    }

    /**
     * Methode gibt den aktuellen Track (obj) zurueck
     * @return
     */
    public Track getTrack() { return currentTrack; }

    /**
     * Methode gibt zurück ob der Player pausiert ist oder abspielt
     * @return
     */
    public boolean getpause() { return pause; }

    /**
     * Setzt die aktuelle Zeit auf einen aktuellen Wert
     * @param currentTime aktuelle Zeit
     */
    public void setCurrentTime(int currentTime) {
        this.currentTime.setValue(currentTime);
    }

    /**
     * Setzt den Wert der momentanen Scene
     * @param scene Szene der Applikation
     */
    public void setCurrentMode(int scene) {
        currentMode.setValue(scene);
    }

    /**
     * Rueckgabe des aktuellen Modus
     * @return aktueller Modus
     */
    public SimpleIntegerProperty getCurrentMode() {
        return currentMode;
    }
}