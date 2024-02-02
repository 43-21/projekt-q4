package organism;

import world.Positioned;
import world.Dynamic;

public class Egg extends Positioned implements Dynamic {
    Genes genes;
    int timeToHatch;

    int time = 0;
    boolean fertilised = false;

    public Egg(Genes genes, int timeToHatch) {
        this.genes = genes;
        this.timeToHatch = timeToHatch;
    }

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