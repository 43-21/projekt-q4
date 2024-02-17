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
    private boolean[] pheromones = new boolean[3];

    private double rotation;

    private double energy = Options.initialEnergy;
    private int age = 1;


    public Organism(Genes genes) {
        this.position = new Double();
        this.genes = genes;
        brain = genes.brain();

        shape = new Shape(15);
        shape.addSquare(0, 0, color);
        shape.addSquare(0, 1, color);
        shape.addSquare(1, 0, color);
        shape.addSquare(1, 1, color);
        shape.setCenter(1, 1);
        shape.setPositionKind(Shape.CORNER);

        energy = 0.0f;
        rotation = Math.PI / (4.0) * 3.0;
    }

    public void setInputs(boolean[] inputs) {
        brain.setInputs(inputs);
    }

    public void update() {
        age++;

        brain.update();
        boolean[] outputs = brain.getOutputs();

        for(int i = 0; i < 3; i++) {
            pheromones[i] = outputs[i + 3];
        }
        
        //MÖGLICHST BALD WEGMACHEN
        outputs[0] = true;
        //outputs[1] = true;

        if(outputs[0]) {
            position.x += Math.cos(rotation) * Options.speed;
            position.y += -Math.sin(rotation) * Options.speed;
        }
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
    }

    public Egg layEgg() {
        energy -= Options.reproductionEnergy;
        return new Egg(genes, 50);
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
}