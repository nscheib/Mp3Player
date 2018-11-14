package sample;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;

public class Track {

    private long id;
    private String title;
    private int length;
    private String albumTitle;
    private String interpret;
    private String soundFile;


    public Track(String titel){
        Mp3File song;
        soundFile = "tracks\\" + titel + ".mp3";
        try {
            song = new Mp3File(soundFile);
            if (song.hasId3v2Tag()){
                ID3v2 iDv2tag = song.getId3v2Tag();
                this.title = iDv2tag.getTitle();
                this.length = iDv2tag.getLength();
                this.albumTitle = iDv2tag.getAlbum();
                this.interpret = iDv2tag.getArtist();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }
        System.out.println(title + length + albumTitle + interpret);


    }
   /* public Track (File song){
        this.title = song.getName();
        this.id = song.hasId
    }
    */

}
