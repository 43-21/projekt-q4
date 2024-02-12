package world;

import java.util.ArrayList;

import organism.Egg;
import organism.Genes;
import organism.Organism;
import support.*;

public class World {
    Matrix objects;
    int width, height;
    public int time = 0;

    public World(int width, int height) {
        this.width = width;
        this.height = height;

        Egg egg = new Egg(new Genes(), 9000);
        egg.setRandomPosition(width, height);

        objects = new Matrix(16, 10, width, height);
        //objects.add(egg);

        Organism organism = new Organism(new Genes());
        organism.setRandomPosition(width, height);
        objects.add(organism);
    }

    public void update() {
        time++;
        //abfolge:
        //1. inputs ermitteln
        //2. alle updaten, essen, koordinaten etc

        for(Positioned o : objects) {
            if(o instanceof Organism) {
                // ArrayList<Positioned> candidates = objects.searchRay(((Organism) o).position, ((Organism) o).getRotation(), 50f);
                // Positioned visible;
                boolean[] inputs = new boolean[8]; //3 für farbe, 2 für entfernung, 3 für kommunikation
                ((Organism) o).setInputs(inputs);
            }   
        }

        for(Positioned o : objects) {
            if(o instanceof Dynamic) {
                ((Dynamic) o).update();
            }

            if(o instanceof Egg) {
                if(((Egg) o).canHatch()) {
                    objects.add(((Egg) o).hatch());
                    objects.remove(o);
                }
            }

            if(o.position.x < 0) o.position.x = 0;
            else if(o.position.x >= width) o.position.x = width - 1;
            if(o.position.y < 0) o.position.y = 0;
            else if(o.position.y >= height) o.position.y = height - 1;
        }
    }

    public ArrayList<Drawable> getDrawables() {
        ArrayList<Drawable> drawables = new ArrayList<>();
        for(Positioned thing : objects) {
            if(thing instanceof Drawable) {
                drawables.add((Drawable) thing);
            }
        }
        return drawables;
    }
}