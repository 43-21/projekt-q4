package support;

import java.awt.Point;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Iterator;

import support.Functionality.DoubleBoolTuple;
import world.Positioned;
import world.Shape;
import world.Square;

public class Matrix implements Iterable<Positioned> {
    int amountOfHorizontalCells;
    int amountOfVerticalCells;
    int width;
    int height;

    ArrayList<ArrayList<ArrayList<Positioned>>> contents;

    public Matrix(int amountOfHorizontalCells, int amountOfVerticalCells, int width, int height) {
        this.amountOfHorizontalCells = amountOfHorizontalCells;
        this.amountOfVerticalCells = amountOfVerticalCells;
        this.width = width;
        this.height = height;

        contents = new ArrayList<ArrayList<ArrayList<Positioned>>>();

        for(int i = 0; i < amountOfHorizontalCells; i++) {
            contents.add(new ArrayList<ArrayList<Positioned>>());
            for(int j = 0; j < amountOfVerticalCells; j++) {
                contents.get(i).add(new ArrayList<Positioned>());
            }
        }
    }

    @Override
    public Iterator<Positioned> iterator() {
        return new MatrixIterator(this);
    }

    public void add(Positioned content) {
        double x = content.getPosition().x;
        double y = content.getPosition().y;
        if(x < 0 || x >= width) return;
        if(y < 0 || y >= height) return;

        int horizontalCell = getHorizontalCell(x);
        int verticalCell = getVerticalCell(y);

        // System.out.println(contents.get(horizontalCell).get(verticalCell).add(content));

        contents.get(horizontalCell).get(verticalCell).add(content);
    }

    public boolean remove(Positioned content) {
        double x = content.getPosition().x;
        double y = content.getPosition().y;
        if(x < 0 || x >= width) return false;
        if(y < 0 || y >= height) return false;

        int horizontalCell = getHorizontalCell(x);
        int verticalCell = getVerticalCell(y);

        return contents.get(horizontalCell).get(verticalCell).remove(content);
    }

    public void update(Double oldPosition, Positioned content) {
        double x = content.getPosition().x;
        double y = content.getPosition().y;
        if(x < 0 || x >= width) return;
        if(y < 0 || y >= height) return;
        if(oldPosition.x < 0 || oldPosition.x >= width) return;
        if(oldPosition.y < 0 || oldPosition.y >= height) return;

        int horizontalCell = getHorizontalCell(x);
        int verticalCell = getVerticalCell(y);

        if(getHorizontalCell(oldPosition.x) == horizontalCell && getVerticalCell(oldPosition.y) == verticalCell) {
            return;
        }

        remove(content);
        add(content);
    }

    private class PositionDistanceTuple {
        private Positioned inSquare;
        private double distance;
    
        public PositionDistanceTuple(Positioned inSquare, double distance) {
            this.inSquare = inSquare;
            this.distance = distance;
        }
    
        public double getDistance() {
            return distance;
        }
    
        public Positioned getInSquare() {
            return inSquare;
        }
    }

    //für sicht erstmal
    public Positioned searchRay(Double position, double angle, double distance) {
        ArrayList<PositionDistanceTuple> relevant = new ArrayList<>();
        double distanceCounterXY = 0;
        double distanceCounter = 0;
        int horizontalBoundsCounter = getHorizontalCell(position.x);
        int verticalBoundsCounter = getVerticalCell(position.y);
        boolean xy = true; // true bedeutet x false bedeutet y
        boolean firstIntersect = true;
        boolean isPiOverFour = false;
        boolean straightLine = false;
        int angleCaseSwitch = 1;
        int angleCaseSwitchSpecialCase = 0;
        double amountOfCells = (double) Options.amountOfHorizontalCells;
        distance = (distance / width) * amountOfCells;
        Double destination = new Double();
        destination.x = position.x + distance * Math.cos(angle);
        destination.y = position.y + distance * Math.sin(angle);
        if(angle == 0){
            straightLine = true;
            angleCaseSwitch = 1;
            angleCaseSwitchSpecialCase = 0;
        } else if(angle == Math.PI / 2.0){
            straightLine = true;
            angleCaseSwitch = 0;
            angleCaseSwitchSpecialCase = 1;
        } else if(angle == Math.PI){
            straightLine = true;
            angleCaseSwitch = -1;
            angleCaseSwitchSpecialCase = 0;
        } else if(angle == (Math.PI * 3.0) / 2.0){
            straightLine = true;
            angleCaseSwitch = 0;
            angleCaseSwitchSpecialCase = -1;
        } else if(((angle > Math.PI / 4.0) && (angle < (Math.PI * 3.0)/ 4.0))){
            xy = false;
            angleCaseSwitch = 1;
        } else if(((angle > (Math.PI * 5.0 ) / 4.0) && (angle < (Math.PI * 7.0) / 4.0))){
            xy = false;
            angleCaseSwitch = -1;
            angle -= Math.PI;
        } else if(((angle > (Math.PI * 3.0 ) / 4.0) && (angle < (Math.PI * 5.0) / 4.0))){
            xy = true;
            angleCaseSwitch = -1;
            angle -= Math.PI;
        } else if((angle == Math.PI / 4.0)){
            isPiOverFour = true;
            angleCaseSwitchSpecialCase = 1;
            angleCaseSwitch = 1;
        } else if((angle == (Math.PI * 3.0)/ 4.0)){
            isPiOverFour = true;
            angleCaseSwitchSpecialCase = -1;
            angleCaseSwitch = 1;
        } else if((angle == (Math.PI * 5.0 ) / 4.0)){
            isPiOverFour = true;
            angleCaseSwitchSpecialCase = -1;
            angleCaseSwitch = -1;
        } else if((angle == (Math.PI * 7.0) / 4.0)){
            isPiOverFour = true;
            angleCaseSwitchSpecialCase = 1;
            angleCaseSwitch = -1;
        }

        // System.out.println(distance);

        while(distanceCounter < distance && horizontalBoundsCounter >= 0 && horizontalBoundsCounter < amountOfHorizontalCells && verticalBoundsCounter >= 0 && verticalBoundsCounter < amountOfVerticalCells){
            if(isPiOverFour == true){
                for(Positioned i : contents.get(horizontalBoundsCounter).get(verticalBoundsCounter)){
                    relevant.add(new PositionDistanceTuple(i, distanceCounter));
                }
                if(firstIntersect == true){
                    distanceCounter += Math.sqrt(2.0) / 2.0;
                    firstIntersect = false;
                } else{
                    distanceCounter += Math.sqrt(2.0);
                }
                horizontalBoundsCounter += angleCaseSwitch;
                verticalBoundsCounter += angleCaseSwitchSpecialCase;
            } else if(straightLine == true){
                for(Positioned i : contents.get(horizontalBoundsCounter).get(verticalBoundsCounter)){
                    relevant.add(new PositionDistanceTuple(i, distanceCounter));
                }
                if(firstIntersect == true){
                    distanceCounter += 1.0 / 2.0;
                    firstIntersect = false;
                } else{
                    distanceCounter++;
                }
                horizontalBoundsCounter += 1*angleCaseSwitch;
                verticalBoundsCounter += 1*angleCaseSwitchSpecialCase;
            }else if(xy == true){
                if(distanceCounterXY >= 1){
                    for(Positioned i : contents.get(horizontalBoundsCounter).get(verticalBoundsCounter)){
                        relevant.add(new PositionDistanceTuple(i, distanceCounter));
                    }
                    distanceCounterXY--;
                    distanceCounterXY += 1 / Math.tan(angle);
                    horizontalBoundsCounter += 1*angleCaseSwitch;
                    distanceCounter += Math.sqrt(Math.pow((1 / Math.tan(angle)), 2) + 1);
                } else{
                    for(Positioned i : contents.get(horizontalBoundsCounter).get(verticalBoundsCounter)){
                        relevant.add(new PositionDistanceTuple(i, distanceCounter));
                    }
                    if(firstIntersect == true){
                        distanceCounter += Math.sqrt(Math.pow((1 / Math.tan(angle)) / 2, 2) + Math.pow(1 / 2, 2));
                        distanceCounterXY += (1 / Math.tan(angle)) / 2;
                        firstIntersect = false;
                    } else{
                        distanceCounter += Math.sqrt(Math.pow((1 / Math.tan(angle)), 2) + 1);
                        distanceCounterXY += 1 / Math.tan(angle);
                    }
                    verticalBoundsCounter += 1*angleCaseSwitch;
                }
            } else{
                if(distanceCounterXY >= 1){
                    for(Positioned i : contents.get(horizontalBoundsCounter).get(verticalBoundsCounter)){
                        relevant.add(new PositionDistanceTuple(i, distanceCounter));
                    }
                    distanceCounterXY--;
                    verticalBoundsCounter += 1*angleCaseSwitch;
                    distanceCounterXY += 1 / Math.tan(angle);
                    distanceCounter += Math.sqrt(Math.pow((1 / Math.tan(angle)), 2) + 1);
                } else{
                    for(Positioned i : contents.get(horizontalBoundsCounter).get(verticalBoundsCounter)){
                        relevant.add(new PositionDistanceTuple(i, distanceCounter));
                    }
                    if(firstIntersect == true){
                        distanceCounter += Math.sqrt(Math.pow((1 / Math.tan(angle)) / 2, 2) + Math.pow(1 / 2, 2));
                        distanceCounterXY += 1 / Math.tan(angle) / 2;
                        firstIntersect = false;
                    } else{
                        distanceCounter += Math.sqrt(Math.pow((1 / Math.tan(angle)), 2) + 1);
                        distanceCounterXY += 1 / Math.tan(angle);
                    }
                    horizontalBoundsCounter += 1*angleCaseSwitch;
                }
            }
        }

        if(horizontalBoundsCounter >= 0 && horizontalBoundsCounter < amountOfHorizontalCells && verticalBoundsCounter >= 0 && verticalBoundsCounter < amountOfVerticalCells){
            for(Positioned i : contents.get(horizontalBoundsCounter).get(verticalBoundsCounter)){
                relevant.add(new PositionDistanceTuple(i, distanceCounter));
            }
        }

        PositionDistanceTuple currentInRay = null;
        //schauen was sich schneidet
        for(PositionDistanceTuple p : relevant) {
            if(p.getInSquare().getPosition() == position);
            Shape shape = p.getInSquare().getShape();

            for(Square s : shape.getSquares()) {
                Point[][] lines = s.getLines(shape.getScale());
                for(int i = 0; i < 4; i++) {
                    double xa = lines[i][0].x + position.x;
                    double ya = lines[i][0].y + position.y;
                    double xb = lines[i][1].x + position.x;
                    double yb = lines[i][1].y + position.y;
                    Double a = new Double(xa, ya);
                    Double b = new Double(xb, yb);
                    DoubleBoolTuple intersectionPoint = Functionality.getIntersectionPoint(a, b, position, destination);
                    if(intersectionPoint.getDouble() != null){
                        if(currentInRay == null) {
                            currentInRay = p;
                            continue;
                        }
                        if(p.getDistance() < currentInRay.getDistance()){
                            currentInRay = p;
                            continue;
                        }
                    }
                }
            }
        }
        if(currentInRay == null) return null;
        return currentInRay.getInSquare();
    }

    //für kollision oÄ
    public ArrayList<Positioned> searchRect(Double position, int x, int y) {
        ArrayList<Positioned> list = new ArrayList<>();

        for(int i = (int) position.x - x; x < position.x + x; i += (int) ((double) width / (double) amountOfHorizontalCells)) {
            int horizontalCell = getHorizontalCell(i);
            for(int j = (int) position.y - y; y < position.y + y; j += (int) ((double) width / (double) amountOfVerticalCells)) {
                int verticalCell = getVerticalCell(j);
                list.addAll(contents.get(horizontalCell).get(verticalCell));
            }
        }

        return list;
    }

    private int getHorizontalCell(double x) {
        return (int) ((x / (double) width) * amountOfHorizontalCells);
    }

    private int getVerticalCell(double y) {
        return (int) ((y / (double) height) * amountOfVerticalCells);
    }
}

class MatrixIterator implements Iterator<Positioned> {
    int i = 0;
    int j = 0;
    int k = 0;
    ArrayList<ArrayList<ArrayList<Positioned>>> contents;

    public MatrixIterator(Matrix matrix) {
        contents = matrix.contents;
    }

    @Override
    public boolean hasNext() {
        if(i >= contents.size()) return false;
        if(j >= contents.get(i).size()) return false;
        if(k < contents.get(i).get(j).size()) return true;


        k = 0;
        for(int a = j + 1; a < contents.get(i).size(); a++) {
            if(k < contents.get(i).get(a).size()) {
                j = a;
                return true;
            }
        }


        for(int a = i + 1; a < contents.size(); a++) {
            for(int b = 0; b < contents.get(a).size(); b++) {
                if(k < contents.get(a).get(b).size()) {
                    i = a;
                    j = b;
                    return true;
                }
            }
        }


        return false;
    }

    @Override
    public Positioned next() {
        Positioned item = contents.get(i).get(j).get(k);
        // System.out.println(i + " " + j + " " + k);
        if(++k < contents.get(i).get(j).size()) return item;
        else k = 0;
        // System.out.println(k);
        if(++j < contents.get(i).size()) return item;
        else j = 0;
        // System.out.println(j);
        i++;
        // System.out.println(i);
        return item;
    }
}