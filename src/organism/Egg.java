package organism;

public class Egg {
    Genes genes;
    int timeToHatch;

    int time = 0;
    boolean fertilised = false;

    public Egg(Genes g, int t) {
        genes = g;
        timeToHatch = t;
    }

    public void fertilise(Genes partner) {
        genes = genes.recombine(partner);
        fertilised = true;
    }

    public boolean canHatch() {
        return time > timeToHatch;
    }

    public void update() {
        if(fertilised) time++;
    }
}