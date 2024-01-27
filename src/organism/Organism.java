package organism;

import java.awt.geom.Point2D.Float;

public class Organism {
    private Brain brain;
    private Genes genes;

    private boolean colorA = true;
    private boolean colorB = false;
    private boolean colorC = false;

    private Float position;
    private float rotation;

    private float energy;

    public Organism(Float position, Brain brain, Genes genes) {
        this.brain = brain;
        this.position = position;
        this.genes = genes;

        energy = 0.0f;
        rotation = 0.0f;
    }

    //sollte wohl am besten mal die World als parameter bekommen
    //dann kann er sich die relevanten informationen selber rausziehen
    public void update() {
        //notwendige informationen ermitteln

        // 3 strahlen (lang - mittel - kurz) mit 3 neuronen für "farbe";
        // 3 Pheromone
        // = 12 input Neuronen

        boolean[] inputs = new boolean[12];

        
        brain.update(inputs);
        //output spikes entnehmen
        //bewegung umsetzen

        rotation = (float) Math.min(2.0 * Math.PI, Math.max(0.0, rotation));
    }

    public Organism reproduce(Organism partner) {
        Genes offspringGenes = genes.recombine(partner.genes);
        Brain offspringBrain = offspringGenes.brain();

        return new Organism(position, offspringBrain, offspringGenes);
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