package organism;

import world.Thing;
import java.awt.Point;

public class Egg implements Thing {
    Genes genes;
    int timeToHatch;

    int time = 0;
    Point position;
    boolean fertilised = false;

    public Egg(Genes genes, int timeToHatch) {
        this.genes = genes;
        this.timeToHatch = timeToHatch;
    }

    @Override
    public void update() {
        if(fertilised) time++;
    }

    public void fertilise(Genes partner) {
        genes = genes.recombine(partner);
        fertilised = true;
    }

    public boolean canHatch() {
        return time > timeToHatch;
    }

    public Organism hatch() {
        return new Organism(position, genes);
    }
}