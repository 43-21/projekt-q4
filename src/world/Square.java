package world;

import java.awt.Point;

public class Square {
    int x, y;
    boolean[] color;    

    public Square(int x, int y, boolean[] color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public boolean[] getColor() {
        return color;
    }

    public Point getPosition() {
        return new Point(x, y);
    }
}