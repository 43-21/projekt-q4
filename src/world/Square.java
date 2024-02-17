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

    public Point[][] getLines(int scale) {
        Point[][] lines = new Point[4][2];

        lines[0][0] = new Point(x * scale, y * scale);
        lines[0][1] = new Point(x * scale + scale - 1, y * scale);

        lines[1][0] = new Point(x * scale, y * scale);
        lines[1][1] = new Point(x * scale, y * scale + scale - 1);

        lines[2][0] = new Point(x * scale + scale - 1, y * scale);
        lines[2][1] = new Point(x * scale + scale - 1, y * scale + scale - 1);

        lines[3][0] = new Point(x * scale, y * scale + scale - 1);
        lines[3][1] = new Point(x * scale + scale - 1, y * scale + scale - 1);

        return lines;
    }
}