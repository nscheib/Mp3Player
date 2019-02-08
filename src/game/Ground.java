package game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Ground {

    private int x, y, size = 50;
    private int type;
    private Rectangle ground;
    private Image image;

    public Ground(int posx, int posy, int type) {
        this.ground = new Rectangle(size, size);
        this.x = posx;
        this.y = posy;
        this.type = type;
    }

    public void setPicture(Image img) {
        this.image = img;
    }

    public void setNewPos(int posx, int posy) {
        this.x = posx;
        this.y = posy;
    }
}
