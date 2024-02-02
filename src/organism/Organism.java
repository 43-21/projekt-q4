package organism;

import java.awt.Point;

import support.Options;
import world.Positioned;
import world.Dynamic;

public class Organism extends Positioned implements Dynamic {
    private Brain brain;
    private Genes genes;

    private boolean[] colors = {true, false, false};
    private boolean[] pheromones = new boolean[3];

    private float rotation;

    private float energy = Options.initialEnergy;
    private int age = 0;

    public Organism(Point position, Genes genes) {
        this.position = position;
        this.genes = genes;
        brain = genes.brain();

        energy = 0.0f;
        rotation = 0.0f;
    }

    public void setInputs(boolean[] inputs) {
        brain.setInputs(inputs);
    }

    public void update() {
        age++;

        //notwendige informationen ermitteln

        // 3 strahlen (lang - mittel - kurz) mit 3 neuronen für "farbe";
        // 3 Pheromone
        // = 12 input Neuronen

        brain.update();
        boolean[] outputs = brain.getOutputs();

        for(int i = 0; i < 3; i++) {
            pheromones[i] = outputs[i + 3];
        }

        // nach vorne - drehen - drehen - 3 pheromone
        // = 6 output Neuronen
        
        if(outputs[0]) {
            position.x += Math.cos(rotation) * Options.speed;
            position.y += Math.sin(rotation) * Options.speed;
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

    public void setPosition(float x, float y) {
        position.setLocation(x, y);
    }

    public Point getPosition() {
        return position;
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
}