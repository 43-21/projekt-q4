package world;

import java.awt.Point;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
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
        Double a = new Double(getPosition().x + getShape().getRelativePosition().x, getPosition().y + getShape().getRelativePosition().y);
        Double b = new Double(a.x + getShape().getWidth(), a.y);
        Double c = new Double(a.x + getShape().getWidth(), a.y + getShape().getHeight());
        Double d = new Double(a.x, a.y + getShape().getHeight());

        Double e = new Double(other.getPosition().x + other.getShape().getRelativePosition().x, other.getPosition().y + other.getShape().getRelativePosition().y);
        Double f = new Double(e.x + other.getShape().getWidth(), e.y);
        Double g = new Double(e.x + other.getShape().getWidth(), e.y + other.getShape().getHeight());
        Double h = new Double(e.x, e.y + other.getShape().getHeight());

        if(!Functionality.rectsColliding(a, b, c, d, e, f, g, h)) return false;

        //dann für alle squares wie ein volidiot überprüfen ob sie kollidieren, aufwand ist ja nur n2...
        int scale = getShape().getScale();
        Double translation = getShape().getRelativePosition();
        int otherScale = other.getShape().getScale();
        Double otherTranslation = other.getShape().getRelativePosition();
        for(Square square : getShape().getSquares()) {
            double x = square.x * scale + translation.x + getPosition().x;
            double y = square.y * scale + translation.y + getPosition().y;

            Double first = new Double(x, y);
            Double second = new Double(x + scale - 1, y);
            Double third = new Double(x + scale - 1, y + scale - 1);
            Double fourth = new Double(x, y + scale - 1);

            for(Square otherSquare : other.getShape().getSquares()) {
                double otherX = otherSquare.x * otherScale + otherTranslation.x + other.getPosition().x;
                double otherY = otherSquare.y * otherScale + otherTranslation.y + other.getPosition().y;

                Double otherFirst = new Double(otherX, otherY);
                Double otherSecond = new Double(otherX + otherScale - 1, otherY);
                Double otherThird = new Double(otherX + otherScale - 1, otherY + otherScale - 1);
                Double otherFourth = new Double(otherX, otherY + otherScale - 1);

                if(Functionality.rectsColliding(first, second, third, fourth, otherFirst, otherSecond, otherThird, otherFourth)) return true;
            }
        }
        return false;
    }

    public boolean colliding(Shape shape, Double otherPosition) {
        //großen test mit rechtecken ausführen
        Double a = new Double(getPosition().x + getShape().getRelativePosition().x, getPosition().y + getShape().getRelativePosition().y);
        Double b = new Double(a.x + getShape().getWidth(), a.y);
        Double c = new Double(a.x + getShape().getWidth(), a.y + getShape().getHeight());
        Double d = new Double(a.x, a.y + getShape().getHeight());

        Double e = new Double(otherPosition.x + shape.getRelativePosition().x, otherPosition.y + shape.getRelativePosition().y);
        Double f = new Double(e.x + shape.getWidth(), e.y);
        Double g = new Double(e.x + shape.getWidth(), e.y + shape.getHeight());
        Double h = new Double(e.x, e.y + shape.getHeight());

        if(!Functionality.rectsColliding(a, b, c, d, e, f, g, h)) return false;

        //dann für alle squares wie ein volidiot überprüfen ob sie kollidieren, aufwand ist ja nur n2...
        int scale = getShape().getScale();
        Double translation = getShape().getRelativePosition();
        int otherScale = shape.getScale();
        Double otherTranslation = shape.getRelativePosition();
        for(Square square : getShape().getSquares()) {
            double x = square.x * scale + translation.x + getPosition().x;
            double y = square.y * scale + translation.y + getPosition().y;

            Double first = new Double(x, y);
            Double second = new Double(x + scale - 1, y);
            Double third = new Double(x + scale - 1, y + scale - 1);
            Double fourth = new Double(x, y + scale - 1);

            for(Square otherSquare : shape.getSquares()) {
                double otherX = otherSquare.x * otherScale + otherTranslation.x + otherPosition.x;
                double otherY = otherSquare.y * otherScale + otherTranslation.y + otherPosition.y;

                Double otherFirst = new Double(otherX, otherY);
                Double otherSecond = new Double(otherX + otherScale - 1, otherY);
                Double otherThird = new Double(otherX + otherScale - 1, otherY + otherScale - 1);
                Double otherFourth = new Double(otherX, otherY + otherScale - 1);

                if(Functionality.rectsColliding(first, second, third, fourth, otherFirst, otherSecond, otherThird, otherFourth)) return true;
            }
        }
        return false;
    }

    //intersection
    //punkt
    public boolean intersecting(Double p) {
        int scale = getShape().getScale();
        Double translation = getShape().getRelativePosition();
        for(Square square : getShape().getSquares()) {
            Point squarePos = square.getPosition();
            double x = position.x + squarePos.x * scale + translation.x;
            double y = position.y + squarePos.y * scale + translation.y;

            Double a = new Double(x, y);
            Double b = new Double(x + scale - 1, y);
            Double c = new Double(x + scale - 1, y + scale - 1);
            Double d = new Double(x, y + scale - 1);

            if(Functionality.pointInRect(p, a, b, c, d)) {
                return true;
            }
        }
        return false;
    }

    //linie
    public Square intersecting(Double a, Double b) {
        int scale = getShape().getScale();
        Double translation = getShape().getRelativePosition();

        Square current = null;
        double currentSquareDistance = java.lang.Double.POSITIVE_INFINITY;

        for(Square square : getShape().getSquares()) {
            Point squarePos = square.getPosition();
            double x = position.x + squarePos.x * scale + translation.x;
            double y = position.y + squarePos.y * scale + translation.y;
            
            Double c = new Double(x, y);
            Double d = new Double(x + scale - 1, y);
            Double e = new Double(x + scale - 1, y + scale - 1);
            Double f = new Double(x, y + scale - 1);

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

    //schnittpunkt mit linie
    public Double getIntersectionPoint(Double a, Double b) {
        int scale = getShape().getScale();
        Double translation = getShape().getRelativePosition();

        Double current = null;
        double currentSquareDistance = java.lang.Double.POSITIVE_INFINITY;

        for(Square square : getShape().getSquares()) {
            Point squarePos = square.getPosition();
            double x = position.x + squarePos.x * scale + translation.x;
            double y = position.y + squarePos.y * scale + translation.y;
            
            Double c = new Double(x, y);
            Double d = new Double(x + scale - 1, y);
            Double e = new Double(x + scale - 1, y + scale - 1);
            Double f = new Double(x, y + scale - 1);

            ArrayList<Double> intersections = Functionality.getIntersectionPoints(c, d, e, f, a, b);
            for(Double intersection : intersections) {
                double squareDistance = Functionality.squareDistance(a, intersection);
                if(currentSquareDistance > squareDistance) {
                    currentSquareDistance = squareDistance;
                    current = intersection;
                }
            }
        }
        return current;
    }
}
