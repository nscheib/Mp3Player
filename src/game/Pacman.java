package game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Pacman {

    private int x, y, type, size = 50;
    private Rectangle pacman;


    public Pacman(int posx, int posy) {
        this.pacman = new Rectangle(size, size);
        pacman.setFill(new ImagePattern(new Image("game/images/pacman_l.png")));
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

    public void changepic(String richtung) {
        switch (richtung) {
            case "o":
                pacman.setFill(new ImagePattern(new Image("game/images/pacman_o.png")));
                break;
            case "u":
                pacman.setFill(new ImagePattern(new Image("game/images/pacman_u.png")));
                break;
            case "l":
                pacman.setFill(new ImagePattern(new Image("game/images/pacman_l.png")));
                break;
            case "r":
                pacman.setFill(new ImagePattern(new Image("game/images/pacman_r.png")));
                break;
        }
    }

    public int getPosition(){
        return x;
    }
}
