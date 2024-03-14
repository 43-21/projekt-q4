package support;

import java.awt.Color;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import world.Shape;
import world.Square;

public class Functionality {
    /**
     * Berechnet die Distanz zwischen zwei Punkten im Quadrat. Nützlich für effiziente Distanzvergleiche
     * @param p1 der erste Punkt
     * @param p2 der zweite Punkt
     * @return die Distanz im Quadrat
     */
    public static double squareDistance(Double p1, Double p2) {
        return (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y);
    }

    /**
     * Berechnet den Schnittpunkt zwischen zwei Strcken. Gibt null zurück, wenn
     * es keinen Schnittpunkt gibt oder die Strecken linear abhängig sind.
     * @param a gehört zur ersten Strecke
     * @param b gehört zur ersten Strecke
     * @param c gehört zur zweiten Strecke
     * @param d gehört zur zweiten Strecke
     * @return den Schnittpunkt oder null
     */
    public static Double getIntersectionPoint(Double a, Double b, Double c, Double d) {
        double denominator = (d.x - c.x) * (b.y - a.y) - (b.x - a.x) * (d.y - c.y);

        if(denominator == 0) {
            return null;
        }

        double r = ((double) ((b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y))) / (double) denominator;
        if(r < 0 || r > 1) return null;
        double s = ((double) ((a.x - c.x) * (d.y - c.y) - (d.x - c.x) * (a.y - c.y))) / (double) denominator;
        if(s < 0 || s > 1) return null;
        return new Double(
            (s * (b.x - a.x) + a.x),
            (s * (b.y - a.y) + a.y)
        );
    }

    /**
     * Überprüft, ob zwei Rechtecke kollidieren.
     * Die Punkte müssen im oder gegen den Uhrzeigersinn angeordnet sein.
     * @param a gehört zum ersten Rechteck
     * @param b gehört zum ersten Rechteck
     * @param c gehört zum ersten Rechteck
     * @param d gehört zum ersten Rechteck
     * @param e gehört zum zweiten Rechteck
     * @param f gehört zum zweiten Rechteck
     * @param g gehört zum zweiten Rechteck
     * @param h gehört zum zweiten Rechteck
     * @return true, wenn die Rechtecke kollidieren
     */
    public static boolean rectsColliding(Double a, Double b, Double c, Double d, Double e, Double f, Double g, Double h) {
        if(allNegativeOrAllGreater(a, b, e, f, g, h)) return false;
        if(allNegativeOrAllGreater(a, d, e, f, g, h)) return false;
        if(allNegativeOrAllGreater(e, f, a, b, c, d)) return false;
        if(allNegativeOrAllGreater(e, h, a, b, c, d)) return false;
        return true;
    }

    private static boolean allNegativeOrAllGreater(Double a, Double b, Double e, Double f, Double g, Double h) {
        boolean lookingForNegative = true;

        Double ab = new Double(b.x - a.x, b.y - a.y);
        double squareDistance = ab.x * ab.x + ab.y * ab.y;

        double sea = projection(e, a, b);
        if(sea > squareDistance) lookingForNegative = false;
        else if(sea >= 0) return false;

        double sfa = projection(f, a, b);
        if(lookingForNegative && sfa >= 0) return false;
        else if(!lookingForNegative && sfa <= squareDistance) return false;

        double sga = projection(g, a, b);
        if(lookingForNegative && sga >= 0) return false;
        else if(!lookingForNegative && sga <= squareDistance) return false;

        double sha = projection(h, a, b);
        if(lookingForNegative && sha >= 0) return false;
        else if(!lookingForNegative && sha <= squareDistance) return false;

        return true;
    }

    //scalar projection * |AB|
    private static double projection(Double p, Double a, Double b) {
        Double r = new Double(b.x - a.x, b.y - a.y);
        Double t = new Double(p.x - a.x, p.y - a.y);
        double s = (t.x * r.x + t.y * r.y);
        return s;
    }
    
    /**
     * Bestimmt, ob sich ein Punkt in einem Rechteck befindet.
     * @param p der Punkt
     * @param a gehört zum Rechteck
     * @param b gehört zum Rechteck
     * @param c gehört zum Rechteck
     * @param d gehört zum Rechteck
     * @return true, wenn der Punkt sich im Rechteck befindet.
     */
    public static boolean pointInRect(Double p, Double a, Double b, Double c, Double d) {
        //vektoren
        double apx = p.x - a.x;
        double abx = b.x - a.x;
        double adx = d.x - a.x;

        double apy = p.y - a.y;
        double aby = b.y - a.y;
        double ady = d.y - a.y;

        //skalarprodukte
        double apab = apx * abx + apy * aby;
        double abab = abx * abx + aby * aby;
        double apad = apx * adx + apy * ady;
        double adad = adx * adx + ady * ady;

        return 0 <= apab && apab <= abab && 0 <= apad && apad <= adad;
    }

    //rechteck - linie
    // abcd:  Rechteck
    // ef: Linie
    /**
     * Bestimmt alle Schnittpunkte zwischen einer Strecke und einem Rechteck
     * @param a gehört zum Rechteck
     * @param b gehört zum Rechteck
     * @param c gehört zum Rechteck
     * @param d gehört zum Rechteck
     * @param e gehört zur Strecke
     * @param f gehört zur Strecke
     * @return eine Liste mit allen Schnittpunkten
     */
    public static ArrayList<Double> getIntersectionPoints(Double a, Double b, Double c, Double d, Double e, Double f) {
        ArrayList<Double> intersections = new ArrayList<>();
        Double p1 = getIntersectionPoint(a, b, e, f);
        Double p2 = getIntersectionPoint(b, c, e, f);
        Double p3 = getIntersectionPoint(c, d, e, f);
        Double p4 = getIntersectionPoint(d, a, e, f);

        if(p1 != null) intersections.add(p1);
        if(p2 != null) intersections.add(p2);
        if(p3 != null) intersections.add(p3);
        if(p4 != null) intersections.add(p4);

        return intersections;
    }

    /**
     * Bestimmt den nächsten Schnittpunkt zwischen einer Shape mit Position und einer Strecke.
     * @param shape
     * @param position
     * @param a
     * @param b
     * @return den nächsten Schnittpunkt oder null, wenn es keinen gibt.
     */
    public static Double getClosestIntersection(Shape shape, Double position, Double a, Double b) {
        int scale = shape.getScale();
        Double translation = shape.getRelativePosition();

        Double current = null;
        double currentSquareDistance = java.lang.Double.POSITIVE_INFINITY;

        for(Square square : shape.getSquares()) {
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
                    current = intersection;
                }
            }
        }
        return current;
    }

    /**
     * Berechnet, ob sich zwei Kreise schneiden.
     * @param p1 der Mittelpunkt des ersten Kreises
     * @param r1 der Radius des ersten Kreises
     * @param p2 der Mittelpunkt des zweiten Kreises
     * @param r2 der Radius des zweiten Kreises
     * @return true, wenn die Kreise sich schneiden
     */
    public static boolean circleCollision(Double p1, double r1, Double p2, double r2) {
        return (r1 + r2) * (r1 + r2) >= squareDistance(p1, p2);
    }

    //quadrate
    /**
     * Überprüft zwei unrotierte Quadrate auf Kollision.
     * @param point1 der Mittelpunkt des ersten Quadrats
     * @param length1 die Seitenlänge des ersten Quadrats
     * @param point2 der Mittelpunkt des zweiten Quadrats
     * @param length2 die Seitenlänge des zweiten Quadrats
     * @return
     */
    public static boolean checkForCollision(Double point1, double length1, Double point2, double length2) {
        boolean horizontalCollision = false;
        boolean verticalCollision = false;
        if(point1.x <= point2.x && point1.x + length1 / 2.0 + length2 / 2.0 >= point2.x) horizontalCollision = true;
        else if(point1.x >= point2.x && point1.x - length1 / 2.0 - length2 / 2.0 <= point2.x) horizontalCollision = true;
        if(point1.y <= point2.y && point1.y + length1 / 2.0 + length2 / 2.0 >= point2.y) verticalCollision = true;
        else if(point1.y >= point2.y && point1.y - length1 / 2.0 - length2 / 2.0 <= point2.y) verticalCollision = true;

        return horizontalCollision && verticalCollision;
    }

    /**
     * Berechnet aus dem Anfangspunkt, dem Winkel und der Länge den Punkt, an dem eine Strecke endet
     * @param startingPoint
     * @param angle
     * @param length
     * @return den Endpunkt
     */
    public static Double getDestinationPoint(Double startingPoint, double angle, double length) {
        return new Double(
            startingPoint.x + (length * Math.cos(angle)),
            startingPoint.y + (length * -Math.sin(angle))
        );
    }

    /**
     * Ordnet einem Array aus 3 booleans [der Repräsentation von Farben in der Welt]
     * eine RGB-Farbe zu.
     * @param color der Array aus 3 booleans
     * @return die RGB-Farbe
     */
    public static Color getDisplayColorFromEncoding(boolean[] color) {
        int switchValue = 0;
        for(int i = 0; i < color.length; i++) {
            switchValue += color[i] ? 1 << 2 - i : 0;
        }
        switch(switchValue) {
            //000
            case 0:
                return Color.WHITE;
            //001
            case 1:
                return Color.GRAY;
            //011
            case 3:
                return Color.ORANGE;
            //100
            case 4:
                return Color.BLACK;
            //110
            case 6:
                return Color.GREEN;
            //111
            case 7:
                return Color.RED;

            //?
            default:
                return Color.LIGHT_GRAY;
        }
    }
}