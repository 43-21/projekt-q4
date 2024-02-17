package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import support.Functionality;

public class Shape {
    public static int CORNER = 0;
    public static int CENTER = 1;

    Square centerSquare = null;
    int centerPositionKind = CORNER;
    int scale;
    int width = 0;
    int height = 0;
    ArrayList<Square> squares;

    public Shape(int scale) {
        this.scale = scale;
        squares = new ArrayList<>();
    }

    public Image getSprite() {
        BufferedImage image = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        
        graphics.setColor(new Color(1f, 1f, 1f, 1f));
        graphics.fillRect(0, 0, width * scale, height * scale);

        for(Square square : squares) {
            graphics.setColor(Functionality.getDisplayColorFromEncoding(square.color));
            graphics.fillRect(square.x * scale, square.y * scale, scale, scale);
        }

        graphics.dispose();
        return image;

    }

    public void addSquare(int x, int y, boolean[] color) {
        squares.add(new Square(x, y, color));
        if(x + 1 > width) width = x + 1;
        if(y + 1 > height) height = y + 1;
    }

    public void setCenter(int x, int y) {
        for(Square s : squares) {
            if(s.x != x || s.y != y) continue;
            centerSquare = s;
            break;
        }
    }

    public void setPositionKind(int positionKind) {
        centerPositionKind = positionKind;
    }

    public Double getRelativePosition() {
        if(centerSquare == null) return new Double();
        Double position = new Double(-centerSquare.x * scale, -centerSquare.y * scale);
        if(centerPositionKind == CENTER) {
            int translation = -scale / 2 + 1;
            position.setLocation(position.x + translation, position.y + translation);
        }
        return position;
    }

    public ArrayList<Square> getSquares() {
        return squares;
    }

    public int getScale() {
        return scale;
    }
}

class Square {
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