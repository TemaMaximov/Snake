package game;

import javax.swing.*;
import java.awt.*;

public class Unit {
    static public final int UNIT_SIZE = 16;
    public int x;
    public int y;
    private Image image;
    public Unit(){
    }
    public Unit(int x, int y, ImageIcon icon){
        this.x = x;
        this.y = y;
        this.image = icon.getImage();
    }
    public Unit(int x, int y, Image image){
        this.x = x;
        this.y = y;
        this.image = image;
    }
    public void setImage(Image image){
        this.image = image;
    }
    public void setImage(ImageIcon icon){
        this.image = icon.getImage();
    }
    public Image getImage(){
        return image;
    }
    public void copy(Unit unit){
        this.x = unit.x;
        this.y = unit.y;
        this.image = unit.image;
    }
}
