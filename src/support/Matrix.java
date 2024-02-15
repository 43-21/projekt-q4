package support;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Iterator;

import world.Positioned;

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

    //für sicht erstmal
    public ArrayList<Positioned> searchRay(Double position, float angle, float distance) {
        int horizontal = getHorizontalCell(position.x);
        int vertical = getVerticalCell(position.y);
        ArrayList<Positioned> relevant = new ArrayList<>();

        double distanceWalkedX = 0;
        while(distanceWalkedX < distance || horizontal < 0 || horizontal >= amountOfHorizontalCells) {
            double distanceWalkedY = 0;
            while(distanceWalkedY < distance || vertical < 0 || vertical >= amountOfVerticalCells) {
                relevant.addAll(contents.get(horizontal).get(vertical));
                if(angle < Math.PI) vertical--;
                else vertical++;
                distanceWalkedY += (double) width / (double) amountOfVerticalCells;
            }

            if(angle < Math.PI / 2.0 && angle > Math.PI / 2.0 * 3.0) horizontal++;
            else horizontal--;
            distanceWalkedX += (double) width / (double) amountOfHorizontalCells;
        }

        ArrayList<Positioned> inRay = new ArrayList<>();

        for(Positioned p : relevant) {
            //if(Functionality.getIntersectionPoint(position, position, position, position))
        }

        return inRay;
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
        return (int) (x / (double) width) * amountOfHorizontalCells;
    }

    private int getVerticalCell(double y) {
        return (int) (y / (double) height) * amountOfVerticalCells;
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
        if(k >= contents.get(i).get(j).size()) return false;
        return true;
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