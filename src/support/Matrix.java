package support;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Iterator;

import world.Positioned;

/**
 * Eine Matrix, in der die Objekte der Welt gespeichert werden können.
 */
public class Matrix implements Iterable<Positioned> {
    int amountOfHorizontalCells;
    int amountOfVerticalCells;
    int width;
    int height;

    ArrayList<ArrayList<ArrayList<Positioned>>> contents;

    /**
     * Erstellt eine leere Matrix, mit einer bestimmten Anzahl an Zellen
     * und einer bestimmten Höhe und Breite
     * @param amountOfHorizontalCells
     * @param amountOfVerticalCells
     * @param width
     * @param height
     */
    public Matrix(int amountOfHorizontalCells, int amountOfVerticalCells, int width, int height) {
        this.amountOfHorizontalCells = amountOfHorizontalCells;
        this.amountOfVerticalCells = amountOfVerticalCells;
        this.width = width;
        this.height = height;

        contents = new ArrayList<ArrayList<ArrayList<Positioned>>>();

        for (int i = 0; i < amountOfHorizontalCells; i++) {
            contents.add(new ArrayList<ArrayList<Positioned>>());
            for (int j = 0; j < amountOfVerticalCells; j++) {
                contents.get(i).add(new ArrayList<Positioned>());
            }
        }
    }

    @Override
    public Iterator<Positioned> iterator() {
        return new MatrixIterator(this);
    }

    /**
     * Fügt der Matrix ein neues Objekt (Positioned) hinzu.
     * @param content das hinzuzufügende Objekt
     */
    public void add(Positioned content) {
        double x = content.getPosition().x;
        double y = content.getPosition().y;
        if (x < 0 || x >= width)
            return;
        if (y < 0 || y >= height)
            return;

        int horizontalCell = getHorizontalCell(x);
        int verticalCell = getVerticalCell(y);

        contents.get(horizontalCell).get(verticalCell).add(content);
    }

    /**
     * Ermittelt die Zelle, in der das Objekt gespeichert werden sollte
     * und versucht es zu entfernen
     * @param content das zu entfernende Objekt
     * @return true, wenn das Objekt erfolgreich entfernt wurde
     */
    public boolean remove(Positioned content) {
        double x = content.getPosition().x;
        double y = content.getPosition().y;
        if (x < 0 || x >= width)
            return false;
        if (y < 0 || y >= height)
            return false;

        int horizontalCell = getHorizontalCell(x);
        int verticalCell = getVerticalCell(y);

        return contents.get(horizontalCell).get(verticalCell).remove(content);
    }

    /**
     * Überprüft für alle Objekte, ob deren Zelle wegen ihrer Position geändert werden muss
     * und setzt dies ggf. durch.
     */
    public void update() {
        int i = 0;
        ArrayList<Positioned> changed = new ArrayList<>();
        for (ArrayList<ArrayList<Positioned>> row : contents) {
            int j = 0;
            for (ArrayList<Positioned> cell : row) {
                ArrayList<Positioned> toBeRemoved = new ArrayList<>();
                for (Positioned p : cell) {
                    double x = p.getPosition().x;
                    double y = p.getPosition().y;

                    int horizontal = getHorizontalCell(x);
                    int vertical = getVerticalCell(y);

                    if (horizontal == i && vertical == j)
                        continue;
                    changed.add(p);
                    toBeRemoved.add(p);
                }
                cell.removeAll(toBeRemoved);
                j++;
            }
            i++;
        }

        for (Positioned p : changed) {
            add(p);
        }
    }

    /**
     * Versucht eine Liste von Objekten einzeln zu entfernen.
     * @param objects
     */
    public void removeAll(ArrayList<Positioned> objects) {
        for (ArrayList<ArrayList<Positioned>> row : contents) {
            for (ArrayList<Positioned> cell : row) {
                cell.removeAll(objects);
            }
        }
    }

    /**
     * Fügt eine Liste von Objekten einzeln hinzu.
     * @param objects
     */
    public void addAll(ArrayList<Positioned> objects) {
        for (Positioned p : objects) {
            add(p);
        }
    }

    /**
     * Ermittelt die Zellen, die eine Strecke durchquert.
     * @param position der Anfangspunkt der Strecke
     * @param angle der Winkel der Strecke
     * @param distance die Länge der Strecke
     * @return eine Liste mit den Objekten dieser Zellen.
     */
    public ArrayList<Positioned> searchRay(Double position, double angle, double distance) {
        ArrayList<Positioned> relevant = new ArrayList<>();

        int x = getHorizontalCell(distance);
        int y = getVerticalCell(distance);

        int horizontal = getHorizontalCell(position.x);
        int vertical = getVerticalCell(position.y);

        if (angle > 0 && angle < Math.PI) {
            if (angle > Math.PI / 2.0 && angle < Math.PI / 2.0 * 3.0) {
                //oben links
                for(int i = horizontal; i >= horizontal - x && i >= 0; i--) {
                    for(int j = vertical; j >= vertical - y && j >= 0; j--) {
                        relevant.addAll(contents.get(i).get(j));
                    }
                }
            }
            else {
                //oben rechts
                for(int i = horizontal; i <= horizontal + x && i < amountOfHorizontalCells; i++) {
                    for(int j = vertical; j >= vertical - y && j >= 0; j--) {
                        relevant.addAll(contents.get(i).get(j));
                    }
                }
            }
        }
        else {
            if (angle > Math.PI / 2.0 && angle < Math.PI / 2.0 * 3.0) {
                //unten links
                for(int i = horizontal; i >= horizontal - x && i >= 0; i--) {
                    for(int j = vertical; j <= vertical + y && j < amountOfVerticalCells; j++) {
                        relevant.addAll(contents.get(i).get(j));
                    }
                }
            }
            else {
                //unten rechts
                for(int i = horizontal; i <= horizontal + x && i < amountOfHorizontalCells; i++) {
                    for(int j = vertical; j <= vertical + y && j < amountOfVerticalCells; j++) {
                        relevant.addAll(contents.get(i).get(j));
                    }
                }
            }
        }
        return relevant;
    }


    //für sicht erstmal
    public ArrayList<Positioned> searchRayPrecise(Double position, double angle, double distance) {
        double heightAtStart = position.y - getVerticalCell(position.y) * Options.cellLength;
        double distanceCounterXY = position.x - getHorizontalCell(position.x) * Options.cellLength;
        double distanceCounter = 0;
        double xPosInCell = 0; 
        double yPosInCell = 0;
        int horizontalBoundsCounter = getHorizontalCell(position.x);
        int verticalBoundsCounter = getVerticalCell(position.y);
        boolean xyAngleSwitch = false; // true bedeutet delta y != 1 false bedeutet x != 1
        boolean firstIntersect = true; // nach dem 1. Intersect bleibt die Distanz konstant, der Wert beim 1. Intersect ist Positionsabhängig
        boolean isPiOverFour = false; // 45 Grad?
        boolean straightLine = false; // Gerade Linie?
        int angleCaseSwitchX = 0; // Regelt in welche Richtung sich die Sicht ausbreitet
        int angleCaseSwitchY = 0; // Regelt in welche Richtung sich die Sicht ausbreitet in Sonderfällen

        if(Math.cos(angle) == 0 || Math.sin(angle) == 0){ // Regelt Vorzeichen der Rechnung
            angleCaseSwitchX = (int) Math.cos(angle);
            angleCaseSwitchY = (int) Math.sin(angle);
            straightLine = true;

        } else{
            angleCaseSwitchX = (int) (Math.cos(angle) / Math.abs(Math.cos(angle)));
            angleCaseSwitchY = (int) (Math.sin(angle) / Math.abs(Math.sin(angle)));
            if(Math.abs(Math.cos(angle)) == Math.abs(Math.sin(angle))){
                isPiOverFour = true;
            } else if(Math.abs(Math.cos(angle)) > Math.abs(Math.sin(angle))){
                xyAngleSwitch = false;
                distanceCounterXY = position.x - getHorizontalCell(position.x) * Options.cellLength;
                heightAtStart = position.y - getVerticalCell(position.y) * Options.cellLength;
            } else{
                xyAngleSwitch = true;
                distanceCounterXY = position.y - getVerticalCell(position.y) * Options.cellLength;
                heightAtStart = position.x - getHorizontalCell(position.x) * Options.cellLength;
            }
        } 

        
        if(xyAngleSwitch){
            if(angleCaseSwitchX == 1){
                xPosInCell = Options.cellLength - heightAtStart;
            } else if(angleCaseSwitchX == -1){
                xPosInCell = heightAtStart;
                if(!xyAngleSwitch) distanceCounterXY = Options.cellLength - distanceCounterXY;
            } 
            if(angleCaseSwitchY == 1){
                yPosInCell = Options.cellLength - distanceCounterXY;
            } else if(angleCaseSwitchY == -1){
                yPosInCell = distanceCounterXY;
                if(xyAngleSwitch) distanceCounterXY = Options.cellLength - distanceCounterXY;
            } 
        } else{
            if(angleCaseSwitchX == 1){
                xPosInCell = Options.cellLength - distanceCounterXY;
            } else if(angleCaseSwitchX == -1){
                xPosInCell = distanceCounterXY;
                if(!xyAngleSwitch) distanceCounterXY = Options.cellLength - distanceCounterXY;
            } 
            if(angleCaseSwitchY == 1){
                yPosInCell = Options.cellLength - heightAtStart;
            } else if(angleCaseSwitchY == -1){
                yPosInCell = heightAtStart;
                if(xyAngleSwitch) distanceCounterXY = Options.cellLength - distanceCounterXY;
            } 
        }
        
        ArrayList<Positioned> relevant = new ArrayList<>();

        while((distanceCounter < distance) && (horizontalBoundsCounter >= 0) && (horizontalBoundsCounter < amountOfHorizontalCells) && (verticalBoundsCounter >= 0) && (verticalBoundsCounter < amountOfVerticalCells)){
            if(distanceCounter >= distance) break;
            if(isPiOverFour == true){
                if(firstIntersect == true){
                    distanceCounter += Math.sqrt(Math.pow((xPosInCell), 2) + Math.pow(yPosInCell, 2));
                    firstIntersect = false;
                } else{
                    distanceCounter += Math.sqrt(Math.pow(Options.cellLength, 2) * 2);
                }
                horizontalBoundsCounter += angleCaseSwitchX;
                if(horizontalBoundsCounter >= 0 && horizontalBoundsCounter < amountOfHorizontalCells && verticalBoundsCounter >= 0 && verticalBoundsCounter < amountOfVerticalCells){
                    relevant.addAll(contents.get(horizontalBoundsCounter).get(verticalBoundsCounter));
                }
                verticalBoundsCounter += angleCaseSwitchY;
                if(horizontalBoundsCounter >= 0 && horizontalBoundsCounter < amountOfHorizontalCells && verticalBoundsCounter >= 0 && verticalBoundsCounter < amountOfVerticalCells){
                    relevant.addAll(contents.get(horizontalBoundsCounter).get(verticalBoundsCounter));
                }
            } else if(straightLine == true){
                if(firstIntersect == true){
                    distanceCounter += (xPosInCell) * Math.abs(angleCaseSwitchX);
                    distanceCounter += (yPosInCell) * Math.abs(angleCaseSwitchY);
                    firstIntersect = false;
                } else{
                    distanceCounter+= Options.cellLength;
                }
                horizontalBoundsCounter += angleCaseSwitchX;
                verticalBoundsCounter += angleCaseSwitchY;
                if(horizontalBoundsCounter >= 0 && horizontalBoundsCounter < amountOfHorizontalCells && verticalBoundsCounter >= 0 && verticalBoundsCounter < amountOfVerticalCells){
                    relevant.addAll(contents.get(horizontalBoundsCounter).get(verticalBoundsCounter));
                }
            }else if(xyAngleSwitch == true){
                if(firstIntersect == true){
                    distanceCounter += Math.sqrt(Math.pow((xPosInCell / Math.tan(angle)), 2) + Math.pow(xPosInCell, 2));
                    distanceCounterXY += (xPosInCell / Math.tan(angle));
                    firstIntersect = false;
                } else{
                    distanceCounter += Math.sqrt(Math.pow((Options.cellLength / Math.tan(angle)), 2) + Options.cellLength);
                    distanceCounterXY += Options.cellLength / Math.tan(angle);
                }
                if(distanceCounterXY >= Options.cellLength){
                    distanceCounterXY-= Options.cellLength;
                    horizontalBoundsCounter += angleCaseSwitchX;
                    if(horizontalBoundsCounter >= 0 && horizontalBoundsCounter < amountOfHorizontalCells && verticalBoundsCounter >= 0 && verticalBoundsCounter < amountOfVerticalCells){
                        relevant.addAll(contents.get(horizontalBoundsCounter).get(verticalBoundsCounter));
                    }
                }
                verticalBoundsCounter += angleCaseSwitchY;
                if(horizontalBoundsCounter >= 0 && horizontalBoundsCounter < amountOfHorizontalCells && verticalBoundsCounter >= 0 && verticalBoundsCounter < amountOfVerticalCells){
                    relevant.addAll(contents.get(horizontalBoundsCounter).get(verticalBoundsCounter));
                }
            } else{
                if(firstIntersect == true){
                    distanceCounter += Math.sqrt(Math.pow((yPosInCell / Math.tan(angle)), 2) + Math.pow(yPosInCell, 2));
                    distanceCounterXY += (yPosInCell / Math.tan(angle));
                    firstIntersect = false;
                } else{
                    distanceCounter += Math.sqrt(Math.pow((Options.cellLength / Math.tan(angle)), 2) + Options.cellLength);
                    distanceCounterXY += Options.cellLength / Math.tan(angle);
                }
                if(distanceCounterXY >= Options.cellLength){
                    distanceCounterXY-= Options.cellLength;
                    verticalBoundsCounter += angleCaseSwitchY;
                    if(horizontalBoundsCounter >= 0 && horizontalBoundsCounter < amountOfHorizontalCells && verticalBoundsCounter >= 0 && verticalBoundsCounter < amountOfVerticalCells){
                        relevant.addAll(contents.get(horizontalBoundsCounter).get(verticalBoundsCounter));
                    }
                }
                horizontalBoundsCounter += angleCaseSwitchX;
                if(horizontalBoundsCounter >= 0 && horizontalBoundsCounter < amountOfHorizontalCells && verticalBoundsCounter >= 0 && verticalBoundsCounter < amountOfVerticalCells){
                    relevant.addAll(contents.get(horizontalBoundsCounter).get(verticalBoundsCounter));
                }
            }
        }
        return relevant;
    }

    /**
     * Ermittelt alle Zellen, durch das das Rechteck geht.
     * @param position der Mittelpunkt des Rechtecks
     * @param x die horizontale Distanz zwischen Mittelpunkt und Seiten (halbe Seitenlänge).
     * @param y die vertikale Distanz zwischen Mittelpunkt und Seiten (halbe Seitenlänge).
     * @return den Inhalt dieser Zellen
     */
    public ArrayList<Positioned> searchRect(Double position, double x, double y) {
        ArrayList<Positioned> list = new ArrayList<>();

        double startX = position.x - x;
        double endX = position.x + x;
        if (startX < 0)
            startX = 0;
        if (endX > width)
            endX = width;

        double startY = position.y - y;
        double endY = position.y + y;
        if (startY < 0)
            startY = 0;
        if (endY > height)
            endY = height;
        double distanceH = (double) width / (double) amountOfHorizontalCells;
        double distanceV = (double) height / (double) amountOfVerticalCells;
        if(2 * x < distanceH) distanceH = 2 * x;
        if(2 * y < distanceV) distanceV = 2 * y;

        for (double i = startX; i <= endX; i += distanceH) {
            int horizontalCell = getHorizontalCell(i);

            for (double j = startY; j <= endY; j += distanceV) {
                int verticalCell = getVerticalCell(j);
                list.addAll(contents.get(horizontalCell).get(verticalCell));
            }
        }
        
        return list;
    }

    /**
     * Ermittelt die Zelle an einer bestimmten Position
     * @param position
     * @return deren Inhalt
     */
    public ArrayList<Positioned> getCellContents(Double position) {
        int horizontal = getHorizontalCell(position.x);
        int vertical = getVerticalCell(position.y);

        return contents.get(horizontal).get(vertical);
    }

    private int getHorizontalCell(double x) {
        return (int) Math.floor((x / (double) width) * amountOfHorizontalCells);
    }

    private int getVerticalCell(double y) {
        return (int) Math.floor((y / (double) height) * amountOfVerticalCells);
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
        if (i >= contents.size())
            return false;
        if (j >= contents.get(i).size())
            return false;
        if (k < contents.get(i).get(j).size())
            return true;

        k = 0;
        for (int a = j + 1; a < contents.get(i).size(); a++) {
            if (k < contents.get(i).get(a).size()) {
                j = a;
                return true;
            }
        }

        for (int a = i + 1; a < contents.size(); a++) {
            for (int b = 0; b < contents.get(a).size(); b++) {
                if (k < contents.get(a).get(b).size()) {
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
        if (++k < contents.get(i).get(j).size())
            return item;
        else
            k = 0;
        if (++j < contents.get(i).size())
            return item;
        else
            j = 0;
        i++;
        return item;
    }
}