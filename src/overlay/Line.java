package overlay;

import java.awt.Color;
import java.awt.geom.Point2D.Double;

public class Line {
    Double a;
    Double b;
    Color color;

    /**
     * Erstellt eine Linie AB
     * @param a
     * @param b
     */
    public Line(Double a, Double b) {
        this.a = a;
        this.b = b;
        this.color = Color.RED;
    }

    /**
     * Erstellt eine Linie AB mit Farbe
     * @param a
     * @param b
     * @param color die Farbe
     */
    public Line(Double a, Double b, Color color) {
        this.a = a;
        this.b = b;
        this.color = color;
    }
}