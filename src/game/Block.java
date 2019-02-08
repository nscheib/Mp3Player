package game;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Klasse Block, die zum Erstellen des Spielfeldes genutzt wird.
 */
public class Block {

    private Rectangle block;
    private int posx, posy;
    private int type;

    /**
     * Initialisierungskonstruktor
     * @param posx x-Koordinate
     * @param posy y-Koordinate
     * @param type der jeweilige Typ
     */
    public Block(int posx, int posy, int type) {
        this.block = new Rectangle(50, 50);
        this.posx = posx;
        this.posy = posy;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setNewPos(int x, int y) {
        this.posx = x;
        this.posy = y;
    }

    public Rectangle getBlock(){
        return block;
    }

    /**
     *
     * @return X-Koordinate
     */
    public int getx() {
        return posx;
    }

    /**
     *
     * @return Y-Koordinate
     */
    public int gety() {
        return posy;
    }

    /**
     * Der Typ wird konvertiert,
     * um im Programm besser damit arbeiten zu können
     * @param type Wert, der in Text-Datei geschrieben ist,
     *             um das Spielfeld zu bauen.
     */
    public void changeType(String type){
        if (type.equals("x")){
            this.type = 120;
        }
        if (type.equals("-")){
            this.type = 45;
        }
    }
}