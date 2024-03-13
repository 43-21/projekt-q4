package world;

import java.awt.Point;

/**
 * Ein Quadrat für die Form
 */
public class Square {
    int x, y;
    boolean[] color;    

    /**
     * Erstellt ein Quadrat an der gegebenen Position mit der gegebenen Farbe
     * @param x
     * @param y
     * @param color
     */
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