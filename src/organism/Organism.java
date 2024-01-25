package organism;

import java.awt.geom.Point2D.Float;

public class Organism {
    private Brain brain;
    private Genes genes;

    private int colorA;
    private int colorB;

    private Float position;


    public Organism(Float position) {
        // brain = new Brain();
        this.position = position;
    }

    public Organism(Float position, Brain brain) {
        this.brain = brain;
        this.position = position;
    }

    //sollte wohl am besten mal die World als parameter bekommen
    //dann kann er sich die relevanten informationen selber rausziehen
    public void update() {

    }

    public Organism reproduce(Organism partner) {
        return new Organism(this.position);
    }

    public void setPosition(float x, float y) {
        position.setLocation(x, y);
    }

    public Float getPosition() {
        return position;
    }
}