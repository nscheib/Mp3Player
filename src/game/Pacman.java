package game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Pacman {

    private int x, y, type, size = 50;
    private Rectangle pacman;


    public Pacman(int posx, int posy) {
        this.pacman = new Rectangle(size, size);
        Image img = new Image("game/images/pacman.png");
        pacman.setFill(new ImagePattern(img));
        this.x = posx;
        this.y = posy;
        this.type = 0;
    }

    public Rectangle getfigure() {
        return pacman;
    }

    public int gety() { return y; }

    public int getx() { return x; }

    public void setNewPos(int posx, int posy) {
        this.x = posx;
        this.y = posy;
    }

    public int getPosition(){
        return x;
    }
}
