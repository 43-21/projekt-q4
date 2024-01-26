package organism;

import java.awt.geom.Point2D.Float;

public class Organism {
    private Brain brain;
    private Genes genes;

    private int colorA;
    private int colorB;

    private Float position;
    private float rotation;

    private float energy;

    public Organism(Float position, Brain brain, Genes genes, int colorA, int colorB) {
        this.brain = brain;
        this.position = position;
        this.genes = genes;
        this.colorA = colorA;
        this.colorB = colorB;

        energy = 0.0f;
        rotation = 0.0f;
    }

    //sollte wohl am besten mal die World als parameter bekommen
    //dann kann er sich die relevanten informationen selber rausziehen
    public void update() {
        //notwendige informationen ermitteln

        // 3 strahle (lang - mittel - kurz) mit 3 neuronen;
        // 3 Pheromone
        // = 12 input Neuronen

        
        //input spikes setzen: 5 * 20 für sicht + 3 für kommunikation
        boolean[] inputs = new boolean[12];
        brain.update(inputs);
        //output spikes entnehmen
        //bewegung umsetzen

        rotation = (float) Math.min(2.0 * Math.PI, Math.max(0.0, rotation));
    }

    public Organism reproduce(Organism partner) {
        Genes offspringGenes = genes.recombine(partner.genes);
        Brain offspringBrain = offspringGenes.brain();
        int offspringColorA = offspringGenes.getColorA();
        int offspringColorB = offspringGenes.getColorB();

        return new Organism(position, offspringBrain, offspringGenes, offspringColorA, offspringColorB);
    }

    public void setPosition(float x, float y) {
        position.setLocation(x, y);
    }

    public Float getPosition() {
        return position;
    }

    public int getColorA() {
        return colorA;
    }

    public int getColorB() {
        return colorB;
    }

    public float getEnergy() {
        return energy;
    }
}