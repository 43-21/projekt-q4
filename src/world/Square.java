package world;

import java.awt.Point;
import java.awt.geom.Point2D.Double;

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

    private Double[] getPoints(int scale) {
        Double[] points = new Double[4];

        points[0] = new Double(x * scale, y * scale);
        points[1] = new Double(x * scale + scale - 1, y * scale);
        points[2] = new Double(x * scale + scale - 1, y * scale + scale - 1);
        points[3] = new Double(x * scale, y * scale + scale - 1);

        return points;
    }

    /**
     * Bestimmt die tatsächliche Position der Strecken des Quadrats.
     * @param scale die Skalierung
     * @param localTransform die Translation, die lokal vorgenommen werden muss, damit der Mittelpunkt bei (0|0) steht
     * @param globalTransform die globale Translation des Objekts, um es auf die tatsächliche Position zu bringen
     * @param rotation
     * @return die Strecken als Arrays von Punkten
     */
    public Double[] getLinesGlobal(int scale, Double localTransform, Double globalTransform) {
        Double[] localPoints = getPoints(scale);
        Double[] points = new Double[4];

        int i = 0;

        for(Double localPoint : localPoints) {
            double x = localPoint.x;
            double y = localPoint.y;

            x += localTransform.x;
            y += localTransform.y;
            
            x += globalTransform.x;
            y += globalTransform.y;

            points[i] = new Double(x, y);
            
            i++;
        }
        return points;
    }
}