package overlay;

import java.awt.Color;
import java.awt.geom.Point2D.Double;

public class Rect {
    Double a;
    Double b;
    Double c;
    Double d;
    Color color;

    public Rect(Double a, Double b, Double c, Double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.color = Color.RED;
    }

    public Rect(Double a, Double b, Double c, Double d, Color color) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.color = color;
    }
}
