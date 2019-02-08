package game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Klasse Pacman, die den Spieler wiederspiegelt innerhalb des Spiels.
 */
public class Pacman {

    private int x, y, type, size = 50;
    private Rectangle pacman;

    /**
     * Initialisierungskonstruktor
     * @param posx x-Koordinate
     * @param posy y-Koordinate
     */
    public Pacman(int posx, int posy) {
        this.pacman = new Rectangle(size, size);
        pacman.setFill(new ImagePattern(new Image("game/images/pacman_l.png")));
        this.x = posx;
        this.y = posy;
        this.type = 0;
    }

    /**
     * Methode um auf das Spielerobjekt Zugriff zu bekommen
     * @return Rectangle Shape als Spieler
     */
    public Rectangle getfigure() {
        return pacman;
    }

    /**
     * Gewaehrt Zugriff auf y-Koordinate
     * @return y-Koordinate
     */
    public int gety() { return y; }

    /**
     * Gewaehrt Zugriff auf x-Koordinate
     * @return x-Koordinate
     */
    public int getx() { return x; }

    /**
     * Setzt eine neue Position
     * @param posx x-Koordinate
     * @param posy y-Koordinate
     */
    public void setNewPos(int posx, int posy) {
        this.x = posx;
        this.y = posy;
    }

    /**
     * Methode, setzt jeweils ein Bild, das in die Richtung gedreht ist
     * in die der Spieler sich bewegt .
     * @param direction Laufrichtung
     */
    public void changepic(String direction) {
        switch (direction) {
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
