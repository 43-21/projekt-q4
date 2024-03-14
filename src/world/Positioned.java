package world;



import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import support.Functionality;

/**
 * Klasse für Objekte, die eine Position und eine Form haben.
 */
public abstract class Positioned implements WithShape {
    protected Double position = new Double();

    public Double getPosition() {
        return position;
    }

    public void setPosition(double x, double y) {
        position.setLocation(x, y);
    }

    /**
     * Setzt die Position auf einen zufälligen stetigen Punkt zwischen (0|0) und der gegebenen Breite und Höhe
     */
    public void setRandomPosition(int width, int height) {
        double x = ThreadLocalRandom.current().nextDouble(width);
        double y = ThreadLocalRandom.current().nextDouble(height);

        position.setLocation(x, y);
    }

    /**
     * Überprüft, ob eine Kollision mit einem Körper besteht
     * @param shape die Form des Körpers
     * @param otherPosition die Position des Körpes
     * @return true, wenn sie kollidieren
     */
    public boolean colliding(Shape shape, Double otherPosition) {
        //großen test mit Kreisen ausführen (der Radius wird höchstens zweimal zu groß sein,
        // das ist verkraftbar, vor allem bei kleineren Objekten wie es standardmäßig der Fall ist)        
        double width = getShape().getWidth();
        double height = getShape().getHeight();
        double oWidth = shape.getWidth();
        double oHeight = shape.getHeight();

        double radius = width > height ? width : height;
        double otherRadius = oWidth > oHeight ? oWidth : oHeight;
        if(!Functionality.circleCollision(this.position, radius, otherPosition, otherRadius)) return false;

        //dann alle Squares auf Kollision überprüfen (...)
        int scale = getShape().getScale();
        Double translation = getShape().getRelativePosition();

        int otherScale = shape.getScale();
        Double otherTranslation = shape.getRelativePosition();

        for(Square square : getShape().getSquares()) {
            Double[] points = square.getLinesGlobal(scale, translation, position);

            Double first = points[0];
            Double second = points[1];
            Double third = points[2];
            Double fourth = points[3];

            for(Square otherSquare : shape.getSquares()) {
                Double[] otherPoints = otherSquare.getLinesGlobal(otherScale, otherTranslation, otherPosition);

                Double otherFirst = otherPoints[0];
                Double otherSecond = otherPoints[1];
                Double otherThird = otherPoints[2];
                Double otherFourth = otherPoints[3];

                if(Functionality.rectsColliding(first, second, third, fourth, otherFirst, otherSecond, otherThird, otherFourth)) return true;
            }
        }
        return false;
    }

    /**
     * Überprüft, ob das Objekt sich mit einem Punkt schneidet
     * @param p der Punkt
     * @return true, wenn sie sich schneiden
     */
    public boolean intersecting(Double p) {
        int scale = getShape().getScale();
        Double translation = getShape().getRelativePosition();

        for(Square square : getShape().getSquares()) {
            Double[] points = square.getLinesGlobal(scale, translation, position);
            Double a = points[0];
            Double b = points[1];
            Double c = points[2];
            Double d = points[3];

            if(Functionality.pointInRect(p, a, b, c, d)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Überprüft, ob das Objekt sich mit einer Strecke schneidet.
     * @param a der Anfangspunkt der Strecke
     * @param b der Endpunkt der Strecke
     * @return das nächste Square, in dem sie sich schneiden, oder null
     */
    public Square intersecting(Double a, Double b) {
        int scale = getShape().getScale();
        Double translation = getShape().getRelativePosition();

        Square current = null;
        double currentSquareDistance = java.lang.Double.POSITIVE_INFINITY;

        for(Square square : getShape().getSquares()) {                        
            Double[] points = square.getLinesGlobal(scale, translation, position);
            Double c = points[0];
            Double d = points[1];
            Double e = points[2];
            Double f = points[3];

            ArrayList<Double> intersections = Functionality.getIntersectionPoints(c, d, e, f, a, b);
            for(Double intersection : intersections) {
                double squareDistance = Functionality.squareDistance(a, intersection);
                if(currentSquareDistance > squareDistance) {
                    currentSquareDistance = squareDistance;
                    current = square;
                }
            }
        }
        return current;
    }

    /**
     * Überprüft, ob das Objekt sich mit einer Strecke schneidet.
     * @param a der Anfangspunkt der Strecke
     * @param b der Endpunkt der Strecke
     * @return den nächste Punkt, an dem sie sich schneiden, oder null
     */
    public Double getIntersectionPoint(Double a, Double b) {
        return Functionality.getClosestIntersection(getShape(), position, a, b);
    }
}
