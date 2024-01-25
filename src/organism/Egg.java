package organism;

public class Egg {
    Organism organism;
    int timeToHatch;

    int time = 0;
    boolean fertilised = false;

    public Egg(Organism o, int t) {
        organism = o;
        timeToHatch = t;
    }

    public void setFertilised() {
        fertilised = true;
    }

    public boolean canHatch() {
        return time > timeToHatch;
    }

    public void update() {
        if(fertilised) time++;
    }
}