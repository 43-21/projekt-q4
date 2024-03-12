package support;

import java.awt.Point;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Iterator;

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

    public void update() {
        int i = 0;
        ArrayList<Positioned> changed = new ArrayList<>();
        for(ArrayList<ArrayList<Positioned>> row : contents) {
            int j = 0;
            for(ArrayList<Positioned> cell : row) {
                ArrayList<Positioned> toBeRemoved = new ArrayList<>();
                for(Positioned p : cell) {
                    double x = p.getPosition().x;
                    double y = p.getPosition().y;

                    int horizontal = getHorizontalCell(x);
                    int vertical = getVerticalCell(y);

                    if(horizontal == i && vertical == j) continue;
                    changed.add(p);
                    toBeRemoved.add(p);
                }
                cell.removeAll(toBeRemoved);
                j++;
            }
            i++;
        }

        for(Positioned p : changed) {
            System.out.print("Changed position: ");
            System.out.println(p);
            add(p);
        }
    }

    public void removeAll(ArrayList<Positioned> objects) {
        for(ArrayList<ArrayList<Positioned>> row : contents) {
            for(ArrayList<Positioned> cell : row) {
                cell.removeAll(objects);
            }
        }
    }

    public void addAll(ArrayList<Positioned> objects) {
        for(Positioned p : objects) {
            add(p);
        }
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
    public ArrayList<PositionDistanceTuple> searchRay(Double position, double angle, double distance) {
        double heightAtStart = position.y - getVerticalCell(position.y) * Options.cellLength;
        double distanceCounterXY = position.x - getHorizontalCell(position.x) * Options.cellLength;
        double distanceCounter = 0;
        int horizontalBoundsCounter = getHorizontalCell(position.x);
        int verticalBoundsCounter = getVerticalCell(position.y);
        boolean xyAngleSwitch = false; // true bedeutet delta x != 1 false bedeutet y != 1
        boolean firstIntersect = true; // nach dem 1. Intersect bleibt die Distanz konstant, der Wert beim 1. Intersect ist Positionsabhängig
        boolean isPiOverFour = false; // 45 Grad?
        boolean straightLine = false; // Gerade Linie?
        int angleCaseSwitchX = 0; // Regelt in welche Richtung sich die Sicht ausbreitet
        int angleCaseSwitchY = 0; // Regelt in welche Richtung sich die Sicht ausbreitet in Sonderfällen

        if(Math.cos(angle) == 0 || Math.cos(angle) == 0){ // Regelt Vorzeichen der Rechnung
            angleCaseSwitchX = (int) Math.cos(angle);
            angleCaseSwitchY = (int) Math.sin(angle);
            straightLine = true;

        } else{
            angleCaseSwitchX = (int) (Math.cos(angle) / Math.abs(Math.cos(angle)));
            angleCaseSwitchY = (int) (Math.sin(angle) / Math.abs(Math.sin(angle)));
            if(Math.abs(Math.cos(angle)) == Math.abs(Math.sin(angle))){
                isPiOverFour = true;
            } else if(Math.abs(Math.cos(angle)) > Math.abs(Math.sin(angle))){
                xyAngleSwitch = true;
                distanceCounterXY = position.y - getVerticalCell(position.y) * Options.cellLength;
                heightAtStart = position.x - getHorizontalCell(position.x) * Options.cellLength;
            } else{
                xyAngleSwitch = false;
                distanceCounterXY = position.x - getHorizontalCell(position.x) * Options.cellLength;
                heightAtStart = position.y - getVerticalCell(position.y) * Options.cellLength;
            }
        } 
        
        ArrayList<PositionDistanceTuple> relevant = new ArrayList<>();

        while(distanceCounter < distance && horizontalBoundsCounter >= 0 && horizontalBoundsCounter < amountOfHorizontalCells && verticalBoundsCounter >= 0 && verticalBoundsCounter < amountOfVerticalCells){
            for(Positioned i : contents.get(horizontalBoundsCounter).get(verticalBoundsCounter)){
                relevant.add(new PositionDistanceTuple(i, distanceCounter));
            }
            if(isPiOverFour == true){
                if(firstIntersect == true){
                    distanceCounter += Math.sqrt(Math.pow((Options.cellLength - distanceCounterXY), 2) + Math.pow(heightAtStart, 2));
                    firstIntersect = false;
                } else{
                    distanceCounter += Math.sqrt(Math.pow(Options.cellLength, 2) * 2);
                }
                horizontalBoundsCounter += angleCaseSwitchX;
                verticalBoundsCounter += angleCaseSwitchY;
            } else if(straightLine == true){
                if(firstIntersect == true){
                    distanceCounter += (Options.cellLength - distanceCounterXY) * Math.abs(angleCaseSwitchX);
                    distanceCounter += (Options.cellLength - heightAtStart) * Math.abs(angleCaseSwitchY);
                    firstIntersect = false;
                } else{
                    distanceCounter+= Options.cellLength;
                }
                horizontalBoundsCounter += angleCaseSwitchX;
                verticalBoundsCounter += angleCaseSwitchY;
            }else if(xyAngleSwitch == true){
                if(firstIntersect == true){
                    distanceCounter += Math.sqrt(Math.pow((heightAtStart / Math.tan(angle)), 2) + Math.pow(heightAtStart, 2));
                    distanceCounterXY += (heightAtStart / Math.tan(angle));
                    firstIntersect = false;
                } else{
                    distanceCounter += Math.sqrt(Math.pow((Options.cellLength / Math.tan(angle)), 2) + Options.cellLength);
                    distanceCounterXY += Options.cellLength / Math.tan(angle);
                }
                if(distanceCounterXY >= Options.cellLength){
                    distanceCounterXY-= Options.cellLength;
                    horizontalBoundsCounter += angleCaseSwitchX;
                }
                verticalBoundsCounter += angleCaseSwitchY;
            } else{
                if(firstIntersect == true){
                    distanceCounter += Math.sqrt(Math.pow((heightAtStart / Math.tan(angle)), 2) + Math.pow(heightAtStart, 2));
                    distanceCounterXY += (heightAtStart / Math.tan(angle));
                    firstIntersect = false;
                } else{
                    distanceCounter += Math.sqrt(Math.pow((Options.cellLength / Math.tan(angle)), 2) + Options.cellLength);
                    distanceCounterXY += Options.cellLength / Math.tan(angle);
                }
                if(distanceCounterXY >= Options.cellLength){
                    distanceCounterXY-= Options.cellLength;
                    verticalBoundsCounter += angleCaseSwitchY;
                }
                horizontalBoundsCounter += angleCaseSwitchX;
            }
        }

        if(horizontalBoundsCounter >= 0 && horizontalBoundsCounter < amountOfHorizontalCells && verticalBoundsCounter >= 0 && verticalBoundsCounter < amountOfVerticalCells){
            for(Positioned i : contents.get(horizontalBoundsCounter).get(verticalBoundsCounter)){
                relevant.add(new PositionDistanceTuple(i, distanceCounter));
            }
        }

        /*PositionDistanceTuple currentInRay = null;
        //schauen was sich schneidet
        for(PositionDistanceTuple p : relevant) {
            if(p.getInSquare().getPosition() == position) continue;
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
                    Double intersectionPoint = Functionality.getIntersectionPoint(a, b, position, destination);
                    if(intersectionPoint != null){
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
        } */
        if(relevant == null) return null;
        return relevant;
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

    public ArrayList<Positioned> getCellContents(Double position) {
        int horizontal = getHorizontalCell(position.x);
        int vertical = getVerticalCell(position.y);

        return contents.get(horizontal).get(vertical);
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
        if(++k < contents.get(i).get(j).size()) return item;
        else k = 0;
        if(++j < contents.get(i).size()) return item;
        else j = 0;
        i++;
        return item;
    }
}