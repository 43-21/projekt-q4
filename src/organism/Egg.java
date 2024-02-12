package organism;

import world.Thing;
import options.Options;

public class Egg implements Thing {
    Genes genes;
    int timeToHatch;

    int time = 0;
    boolean fertilised = Options.singleOrganismReproduction;

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
}