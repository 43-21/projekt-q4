package organism;

public class Egg {
    int time = 0;
    boolean fertilised = false;
    Organism organism;

    public Egg(Organism o) {
        organism = o;
    }

    public void isFertilised() {
        fertilised = true;
    }

    public void update() {
        if(fertilised) time++;
    }
}