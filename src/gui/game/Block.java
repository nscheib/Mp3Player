package gui.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block {

    private Rectangle block;
    private int posx, posy;
    private int type;

    public Block(int posx, int posy) {

        this.block = new Rectangle(50, 50);
        this.block.setFill(Color.BLUE);
        this.posx = posx;
        this.posy = posy;
    }

    public int getType() {
        return type;
    }

    public void setNewPos(int x, int y) {
        this.posx = x;
        this.posy = y;
    }

}