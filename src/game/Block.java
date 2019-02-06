package game;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Block {

    private Rectangle block;
    private int posx, posy;
    private int type;

    public Block(int posx, int posy, int type) {

        this.block = new Rectangle(50, 50);
        //this.block.setFill(new ImagePattern(new Image("/game/images/wall-texture.png")));
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

    public int getx() {
        return posx;
    }

    public int gety() {
        return posy;
    }

    public void changeType(String type){
        if (type.equals("x")){
            this.type = 120;
        }
        if (type.equals("-")){
            this.type = 45;
        }
    }

}