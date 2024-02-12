package support;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D.Double;

public class Functionality {
    public static double distance(Point p1, Point p2) {
        return p1.distance(p2);
    }

    //a und b für die linie
    //c und d für den strahl
    public static Double getIntersectionPoint(Double a, Double b, Double c, Double d) {
        double denominator = (d.x - c.x) * (b.y - a.y) - (b.x - a.x) * (d.y - c.y);
        double r = ((double) ((b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y))) / (double) denominator;
        if(r < 0) return null;
        double s = ((double) ((a.x - c.x) * (d.y - c.y) - (d.x - c.x) * (a.y - c.y))) / (double) denominator;
        if(s < 0 || s > 1) return null;
        return new Double(
            (s * (b.x - a.x) + a.x),
            (s * (b.y - a.y) + a.y)
        );
    }

    public static Double getDestinationPoint(Double startingPoint, double angle, double length) {
        return new Double(
            startingPoint.x + (length * Math.cos(angle)),
            startingPoint.y + (length * -Math.sin(angle))
        );
    }

    public static Color getDisplayColorFromEncoding(boolean[] color) {
        int switchValue = 0;
        for(int i = 0; i < color.length; i++) {
            switchValue += color[i] ? 1 << 2 - i : 0;
        }
        //1 0 0 = 4 + 0 + 0
        switch(switchValue) {
            //000
            case 0:
                return Color.WHITE;
            //001
            case 1:
                return Color.GRAY;
            //010
            case 2:
                return Color.RED;
            //011
            case 3:
                return Color.ORANGE;
            //100
            case 4:
                return Color.BLACK;
            //110
            case 6:
                return Color.GREEN;

            //?
            default:
                return Color.LIGHT_GRAY;
        }
    }
}