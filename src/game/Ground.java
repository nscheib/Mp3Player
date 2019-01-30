package game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Ground {

    private int x, y, size = 50;
    private int type;
    private Rectangle ground;
    private Image image;

    public Ground(int posx, int posy, int type/*, Mp3Player player*/) {
        this.ground = new Rectangle(size, size);
        this.x = posx;
        this.y = posy;
        this.type = type;
        /*setPicture(getType(type*//*, player*//*));*/
    }


    /*public Image getType(int type*//*, Mp3Player player*//*){

        if(type.equals("s")) {
            // Setzte Cover eines Songs
            //Image img = new Image(player.getTrack().getImage());
            //return img;
        } else if (type.equals("o")) {
            Image img = new Image("/images/circle.png");
            ground.setFill(new ImagePattern(img));
            return img;
        }
        return null;
    }*/

    public void setPicture(Image img) {
        this.image = img;
    }

    public void setNewPos(int posx, int posy) {
        this.x = posx;
        this.y = posy;
    }
}
