package overlay;

import java.awt.Color;
import java.awt.geom.Point2D.Double;

public class Line {
    Double a;
    Double b;
    Color color;

    public Line(Double a, Double b) {
        this.a = a;
        this.b = b;
        this.color = Color.RED;
    }

    public Line(Double a, Double b, Color color) {
        this.a = a;
        this.b = b;
        this.color = color;
    }
}