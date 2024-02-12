package organism;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import support.Options;
import world.*;

public class Organism extends Positioned implements Dynamic, Drawable {
    private Brain brain;
    private Genes genes;
    private Shape shape;

    private boolean[] colors = {true, false, false};
    private boolean[] pheromones = new boolean[3];

    private float rotation;

    private float energy = Options.initialEnergy;
    private int age = 0;

    public Organism(Genes genes) {
        this.position = new Point();
        this.genes = genes;
        brain = genes.brain();

        shape = new Shape(30);
        shape.addSquare(1, 0, Color.BLACK);
        shape.addSquare(0, 1, Color.RED);
        shape.addSquare(2, 1, Color.RED);
        shape.addSquare(1, 2, Color.BLACK);

        energy = 0.0f;
        rotation = (float) (Math.PI / (4.0));
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
        rotation = (float) Math.min(2.0 * Math.PI, Math.max(0.0, rotation));

        energy = energy - Options.energyConsumptionAtDeathAge/(float) Options.deathAge * (float) age;
    }

    public Egg layEgg() {
        energy -= Options.reproductionEnergy;
        return new Egg(genes, 50);
    }

    public float getRotation() {
        return rotation;
    }

    public boolean[] getColors() {
        return colors;
    }

    public float getEnergy() {
        return energy;
    }

    public void eat(float energy) {
        this.energy += energy;
    }

    @Override
    public Image getSprite() {
        return shape.getSprite();
    }
}