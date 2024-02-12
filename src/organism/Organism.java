package organism;

import java.awt.geom.Point2D.Float;

import options.Options;
import world.Thing;

public class Organism implements Thing {
    private Brain brain;
    private Genes genes;

    private boolean colorA = true;
    private boolean colorB = false;
    private boolean colorC = false;

    private boolean[] pheromones = new boolean[3];

    private Float position;
    private float rotation;

    private float energy = Options.initialEnergy;

    private int age = 1; // Damit ab dem ersten Moment Energie verbraucht wird

    public Organism(Float position, Brain brain, Genes genes) {
        this.brain = brain;
        this.position = position;
        this.genes = genes;

        energy = 0.0f;
        rotation = 0.0f;
    }

    @Override
    public void update() {
        age++;

        //notwendige informationen ermitteln

        // 3 strahlen (lang - mittel - kurz) mit 3 neuronen für "farbe";
        // 3 Pheromone
        // = 12 input Neuronen

        boolean[] inputs = new boolean[12];
        brain.setInputs(inputs);
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
        return new Egg(genes, 50);
    }

    public void setPosition(float x, float y) {
        position.setLocation(x, y);
    }

    public Float getPosition() {
        return position;
    }

    public boolean getColorA() {
        return colorA;
    }

    public boolean getColorB() {
        return colorB;
    }

    public boolean getColorC() {
        return colorC;
    }

    public float getEnergy() {
        return energy;
    }
}