package organism;

import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D.Double;

import support.Options;
import world.*;

public class Organism extends Positioned implements Dynamic, Drawable {
    private Brain brain;
    private Genes genes;
    private Shape shape;

    private boolean[] color = {true, false, false};

    private double rotation;

    private double energy = Options.initialEnergy;
    private int age = 0;

    public static final int scale = Options.organismScale;

    public Organism(Genes genes) {
        this.position = new Double();
        this.genes = genes;
        brain = genes.brain();

        shape = new Shape(scale);
        shape.addSquare(0, 0, color);
        shape.addSquare(0, 1, color);
        shape.addSquare(1, 0, color);
        shape.addSquare(1, 1, color);
        shape.setCenter(1, 1);
        shape.setPositionKind(Shape.CORNER);

        rotation = 0.0; //Math.PI / (4.0) * 3.0;
    }

    public void setInputs(boolean[] inputs) {
        brain.setInputs(inputs);
    }

    public void update() {
        brain.update();
        boolean[] outputs = brain.getOutputs();

        // if(outputs[0]) {
        //     position.x += Math.cos(rotation) * Options.speed;
        //     position.y += -Math.sin(rotation) * Options.speed;
        // }

        if(outputs[1]) {
            rotation += Math.PI / 90.0;
        }
        if(outputs[2]) {
            rotation -= Math.PI / 90.0;
        }
        
        if(rotation < 0.0) {
            rotation = 2.0 * Math.PI - rotation;
        }

        else if(rotation >= Math.PI * 2.0) {
            rotation = rotation - Math.PI * 2.0;
        }

        energy = energy - Options.energyConsumptionAtDeathAge/Options.deathAge * (double) age;
        age++;
    }

    public boolean[] getOutputs() {
        return brain.getOutputs();
    }

    public Egg layEgg() {
        energy -= Options.reproductionEnergy;
        Genes copy = new Genes(genes);
        for(int i = 0; i < Options.mutationsOnReproduction; i++) {
            copy.mutate();
        }
        Egg egg = new Egg(copy, 300);
        egg.setPosition(position.x, position.y);
        return egg;
    }

    public double getRotation() {
        return rotation;
    }

    public boolean[] getColor() {
        return color;
    }

    public double getEnergy() {
        return energy;
    }

    public void eat(double energy) {
        this.energy += energy;
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