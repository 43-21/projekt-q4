package organism;

import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D.Double;

import world.Drawable;
import world.Dynamic;
import world.Positioned;
import world.Shape;

public class Egg extends Positioned implements Dynamic, Drawable {
    Shape shape;
    Genes genes;
    int timeToHatch;

    int time = 0;
    boolean fertilised = true; //false

    public Egg(Genes genes, int timeToHatch) {
        this.genes = genes;
        this.timeToHatch = timeToHatch;

        shape = new Shape(10);
        boolean[] color = {true, true, false};
        shape.addSquare(1, 0, color);
        shape.addSquare(0, 1, color);
        shape.addSquare(2, 1, color);
        shape.addSquare(1, 2, color);
        shape.addSquare(1, 1, new boolean[]{false, false, false});
        shape.setCenter(1, 1);
        shape.setPositionKind(Shape.CENTER);
    }

    public void update() {
        if(fertilised) time++;
    }

    // public void fertilise(Genes partner) {
    //     genes = genes.recombine(partner);
    //     fertilised = true;
    // }

    public boolean canHatch() {
        return time > timeToHatch;
    }

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

    public Shape getShape() {
        return shape;
    }
}