package organism;

import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D.Double;

import support.Options;
import world.Drawable;
import world.Dynamic;
import world.Positioned;
import world.Shape;

/**
 * Ein Ei speichert die Gene eines Organismus, der später schlüpfen wird.
 */
public class Egg extends Positioned implements Dynamic, Drawable {
    Shape shape;
    Genes genes;
    int timeToHatch;

    int time = 0;

    final int scale = Options.eggScale;

    /**
     * Erstellt ein Ei mit bestimmten Genen und einer Zeit, bis der Organismus schlüpfen soll.
     * @param genes
     * @param timeToHatch
     */
    public Egg(Genes genes, int timeToHatch) {
        this.genes = genes;
        this.timeToHatch = timeToHatch;

        shape = new Shape(scale);
        boolean[] color = {true, true, false};
        shape.addSquare(1, 0, color);
        shape.addSquare(0, 1, color);
        shape.addSquare(2, 1, color);
        shape.addSquare(1, 2, color);
        shape.addSquare(1, 1, new boolean[]{false, false, false});
        shape.setCenter(1, 1);
        shape.setPositionKind(Shape.CENTER);
    }

    /**
     * Zählt die Zeit hoch
     */
    public void update() {
        time++;
    }

    /**
     * Gibt aus, ob der Organismus schlüpfen kann
     * @return true, wenn die Zeit zum Schlüpfen verstrichen ist
     */
    public boolean canHatch() {
        return time > timeToHatch;
    }

    /**
     * Lässt den Organismus schlüpfen
     * @return den Organismus
     */
    public Organism hatch() {
        Organism organism = new Organism(genes);
        organism.setPosition(position.x, position.y);
        return organism;
    }

    @Override
    public Image getSprite() {
        return shape.getSprite();
    }

    @Override
    public Point getDrawPosition() {
        Double translation = shape.getRelativePosition();
        double x = position.x + translation.x;
        double y = position.y + translation.y;

        return new Point((int) x, (int) y);
    }

    @Override
    public Shape getShape() {
        return shape;
    }
}