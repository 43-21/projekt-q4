package world;

import java.awt.Point;
import java.awt.geom.Point2D.Double;
import java.util.concurrent.ThreadLocalRandom;

import support.Functionality;

// Objekte dieser Art haben eine Position
public abstract class Positioned implements WithShape {
    protected Double position = new Double();

    public Double getPosition() {
        return position;
    }

    public void setPosition(double x, double y) {
        position.setLocation(x, y);
    }

    // Objekt wird auf eine zufällige Position gesetzt
    public void setRandomPosition(int width, int height) {
        double x = ThreadLocalRandom.current().nextDouble(width);
        double y = ThreadLocalRandom.current().nextDouble(height);

        position.setLocation(x, y);
    }
    
    //collision
    public boolean colliding(Positioned other) {
        //großen test mit rechtecken ausführen
        //dann für alle squares wie ein volidiot überprüfen ob sie kollidieren, aufwand ist ja nur n2...
        return false;
    }

    public boolean colliding(Shape shape, Double position) {
        //dasselbe wie oben :)
        return false;
    }

    //intersection
    public boolean intersecting(Double p) {
        int scale = getShape().getScale();
        Double translation = getShape().getRelativePosition();
        for(Square square : getShape().getSquares()) {
            Point squarePos = square.getPosition();
            double x = position.x + squarePos.x * scale + translation.x;
            double y = position.y + squarePos.y * scale + translation.y;

            Double a = new Double(x, y);
            Double b = new Double(x + scale, y);
            Double c = new Double(x + scale, y + scale);
            Double d = new Double(x, y + scale);

            if(Functionality.pointInRect(p, a, b, c, d)) {
                return true;
            }
        }
        return false;
    }

    public Square intersecting(Double a, Double b) {
        Square closestIntersection = null;
        for(Square square : getShape().getSquares()) {
            //tatsächliche position berechnen
            //eckpunkte bestimmen
            //getIntersectionPoints bestimmen
            //wenn drin: distanz zu a mit früherem vergleichen, wenn kleiner: aktuelles square
        }
        return closestIntersection;
    }
}
