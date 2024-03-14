package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import support.Functionality;

/**
 * Eine Form, die aus farbigen Quadraten besteht.
 */
public class Shape {
    public static int CORNER = 0;
    public static int CENTER = 1;

    private Square centerSquare = null;
    private int centerPositionKind = CORNER;
    private int scale;
    private int width = 0;
    private int height = 0;
    private ArrayList<Square> squares;

    /**
     * Erstellt eine Form ohne Quadrate
     * @param scale die Seitenlänge der Quadrate in Pixel
     */
    public Shape(int scale) {
        this.scale = scale;
        squares = new ArrayList<>();
    }

    /**
     * Erstellt das Bild der Form
     * @return das Bild
     */
    public BufferedImage getSprite() {
        BufferedImage image = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        
        graphics.setColor(new Color(1f, 1f, 1f, 0f));
        graphics.fillRect(0, 0, width * scale, height * scale);

        for(Square square : squares) {
            graphics.setColor(Functionality.getDisplayColorFromEncoding(square.color));
            graphics.fillRect(square.x * scale, square.y * scale, scale, scale);
        }

        graphics.dispose();
        return image;
    }

    /**
     * Fügt ein Quadrat hinzu
     * @param x die x-Position
     * @param y die y-Position
     * @param color die Welt-Farbe, also ein Array aus drei booleans
     */
    public void addSquare(int x, int y, boolean[] color) {
        squares.add(new Square(x, y, color));
        if(x + 1 > width) width = x + 1;
        if(y + 1 > height) height = y + 1;
    }

    /**
     * Bestimmt ein Quadrat als Mitte
     * @param x die x-Position des Quadrats
     * @param y die y-Position des Quadrats
     */
    public void setCenter(int x, int y) {
        for(Square s : squares) {
            if(s.x != x || s.y != y) continue;
            centerSquare = s;
            break;
        }
    }

    /**
     * Legt die Genaue Mitte fest.
     * <ul>
     * <li> CORNER: die linke obere Ecke des mittleren Quadrats
     * <li> CENTER: die Mitte des mittleren Quadrats
     * </ul>
     * @param positionKind
     */
    public void setPositionKind(int positionKind) {
        centerPositionKind = positionKind;
    }

    /**
     * Gibt die Position des Quadrats bei (0|0) wieder im Vergleich zum Mittelpunkt
     *  - dieser ist dann also bei (0|0)
     * @return die Verschiebung in Pixel
     */
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

    /**
     * @return Breite in Pixel
     */
    public double getWidth() {
        return width * scale;
    }

    /**
     * @return Höhe in Pixel
     */
    public double getHeight() {
        return height * scale;
    }
}