package businessLogic;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class Track {

    private String title;
    private String interpret;
    //private String albumTitle;
    private String soundFile;
    private Mp3File song;
    private byte[] picture;
    private long lenght;
    private String fileName;


    public Track(String title) {
        soundFile = "tracks/" + title;
        fileName = title;
        try {
            this.soundFile = new File(soundFile).getAbsolutePath();
            this.song = new Mp3File(soundFile);
            this.lenght = song.getLengthInMilliseconds();
            this.title = song.getId3v2Tag().getTitle();
            this.interpret = song.getId3v2Tag().getArtist();
            //this.albumTitle = song.getId3v2Tag().getAlbum();
            this.picture = song.getId3v2Tag().getAlbumImage();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }

    }

    public String getFileName(){return fileName;}

    public String getTitle(){return title;}

    public String getInterpret(){return interpret;}

    public String getSoundFile() { return soundFile; }

    public float getLenght(){ return lenght; }

    public String getLenghtAsString(){
        int minutes = (int)lenght / (60 * 1000);
        int seconds = (int)(lenght / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public Image getImage(){
        if(picture != null) {
            Image imageOutput = new Image(new ByteArrayInputStream(picture));
            return imageOutput;
        } else {
            Image image = new Image("gui/images/song.png");
            return image;
        }
    }

    public boolean verifyPicture() {
        if(song.getId3v2Tag().getAlbumImage() != null) {
            return true;
        } else if (song.getId3v2Tag().getAlbumImage() == null) {
            return false;
        }
        return false;
    }

}
