package support;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D.Double;

public class Functionality {
    public static double distance(Point p1, Point p2) {
        return p1.distance(p2);
    }

    //Schnittpunkt zwischen zwei Strecken AB und CD oder null wenn kein Schnittpunkt
    //funktioniert eigentlich aber evtl probleme mit 0 und vorzeichen
    public static Double getIntersectionPointIntern(Double a, Double b, Double c, Double d) {
        double denominator = (d.x - c.x) * (b.y - a.y) - (b.x - a.x) * (d.y - c.y);
        double r = ((double) ((b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y))) / (double) denominator;
        if(r < 0 || r > 1) return null;
        double s = ((double) ((a.x - c.x) * (d.y - c.y) - (d.x - c.x) * (a.y - c.y))) / (double) denominator;
        if(s < 0 || s > 1) return null;
        return new Double(
            (s * (b.x - a.x) + a.x),
            (s * (b.y - a.y) + a.y)
        ); //neu schreiben
    }

    public static boolean areVectorsLinearlyDependent(double[] vector1, double[] vector2) {
        // Überprüfen, ob die Determinante der Matrix gleich 0 ist
        // | x1 y1 |
        // | x2 y2 |
        double determinant = vector1[0] * vector2[1] - vector1[1] * vector2[0];

        return determinant == 0;
    }

    static class DoubleBoolTuple {
        private Double first;
        private boolean second;
    
        public DoubleBoolTuple(Double first, boolean second) {
            this.first = first;
            this.second = second;
        }
    
        public Double getFirst() {
            return first;
        }
    
        public boolean getSecond() {
            return second;
        }
    }

    public static DoubleBoolTuple getIntersectionPoint(Double a, Double b, Double c, Double d) {
        double[] vector1 = {(b.x - a.x), (b.y - a.y)};
        double[] vector2 = {(d.x - c.x), (d.y - c.y)};
        if(!areVectorsLinearlyDependent(vector1, vector2)){
            return new DoubleBoolTuple(getIntersectionPointIntern(a, b, c, d), false);
        } else {
            return new DoubleBoolTuple(getIntersectionPointIntern(a, b, c, d), true);
        }
    }

    public static boolean checkForCollision(Double point1, double radius1, Double point2, double radius2) {
        boolean horizontalCollision = false;
        boolean verticalCollision = false;
        if(point1.x <= point2.x && point1.x + radius1 + radius2 >= point2.x) horizontalCollision = true;
        else if(point1.x >= point2.x && point1.x - radius1 - radius2 <= point2.x) horizontalCollision = true;
        if(point1.y <= point2.y && point1.y + radius1 + radius2 >= point2.y) verticalCollision = true;
        else if(point1.y >= point2.y && point1.y - radius1 - radius2 <= point2.y) verticalCollision = true;

        return horizontalCollision && verticalCollision;
    }

    //true wenn Punkt point auf Strecke AB ist, sonst false
    public static boolean pointIsOnLine(Double a, Double b, Double point) {
        double angle1 = getAngle(a, b) + Math.PI / 2.0;
        double angle2 = getAngle(a, b) - Math.PI / 2.0;
        Double d1 = getDestinationPoint(point, angle1, 10.0);
        Double d2 = getDestinationPoint(point, angle2, 10.0);
        Double intersection1 = getIntersectionPoint(a, b, point, d1).getFirst();
        Double intersection2 = getIntersectionPoint(a, b, point, d2).getFirst();
        return intersection1 != null && intersection2 != null;
    }

    public static Double getDestinationPoint(Double startingPoint, double angle, double length) {
        return new Double(
            startingPoint.x + (length * Math.cos(angle)),
            startingPoint.y + (length * -Math.sin(angle))
        );
    }

    public static double getAngle(Double a, Double b) {
        double x = b.x - a.x;
        double y = b.y - a.y;
        double scalar = x + y;
        double length = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double angle = Math.acos(scalar / length);

        return angle;
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