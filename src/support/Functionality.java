package support;

import java.awt.Point;

public class Functionality {
    public static double distance(Point p1, Point p2) {
        return p1.distance(p2);
    }

    //a und b für die linie
    //c und d für den strahl
    public static Point getIntersectionPoint(Point a, Point b, Point c, Point d) {
        int denominator = (d.x - c.x) * (b.y - a.y) - (b.x - a.x) * (d.y - c.y);
        double r = ((double) ((b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y))) / (double) denominator;
        if(r < 0) return null;
        double s = ((double) ((a.x - c.x) * (d.y - c.y) - (d.x - c.x) * (a.y - c.y))) / (double) denominator;
        if(s < 0 || s > 1) return null;
        return new Point(
            (int) (s * (b.x - a.x) + a.x),
            (int) (s * (b.y - a.y) + a.y)
        );
    }

    public static Point getDestinationPoint(Point startingPoint, double angle, double length) {
        return new Point(
            startingPoint.x + (int) (length * Math.cos(angle)),
            startingPoint.y + (int) (length * -Math.sin(angle))
        );
    }
}