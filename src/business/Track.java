package business;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Klasse für einzelne Track-Objekte,
 * die dann in einer Playlist gesammelt werden koennen.
 */
public class Track {

    private String title;
    private int length;
    private String albumTitle;
    private String interpret;
    private String soundFile;
    private byte[] image;
    private String fileName;


    public Track() {}


    public Track(String title)
    {
        Mp3File song;
        soundFile = "tracks/" + title + ".mp3";
        fileName = title;
        try {
            song = new Mp3File(soundFile);
            if (song.hasId3v2Tag()){
                ID3v2 iDv2tag = song.getId3v2Tag();
                this.title = iDv2tag.getTitle();
                this.length = iDv2tag.getLength();
                this.albumTitle = iDv2tag.getAlbum();
                this.interpret = iDv2tag.getArtist();
                this.image = iDv2tag.getAlbumImage(); // mit byterrayinputstream umwandelnin in normales image und dann ist es dasrstellbar
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }
        System.out.println(this.title + length + albumTitle + interpret + image);
    }

    public String getFileName(){return fileName;}
    public String getTitle(){return title;}
    public String getInterpret(){return interpret;}
    public Image getImage(){
        Image imageOutput = new Image(new ByteArrayInputStream(image));
        return imageOutput;
    }

    public double getLength() { return length;
    }
}
