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
        objects.add(egg);
    }

    public void update() {
        time++;
        //abfolge:
        //1. inputs ermitteln
        //2. alle updaten, essen, koordinaten etc

        for(Positioned o : objects) {
            if(o instanceof Organism) {
                ArrayList<Positioned> candidates = objects.searchRay(((Organism) o).position, ((Organism) o).getRotation(), 50f);
                Positioned visible;
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
                    //vorher schlüpfen mit hatch()
                    objects.remove(o);
                }
                o.position.translate(1, 1);
            }

            //spiegelt das vernünftig?
            boolean changedX = true, changedY = true;
            if(o.position.x >= width) {
                o.position.x = o.position.x - width;
            }

            else if(o.position.x < 0) {
                o.position.x = width + o.position.x;
            }

            else changedX = false;

            if(o.position.y >= height) {
                o.position.y = o.position.y - height;
            }

            else if(o.position.y < 0) {
                o.position.y = height + o.position.y;
            }

            else changedY = false;

            if(changedX && !changedY) {
                o.position.y = height - o.position.y;
            }

            else if(changedY && !changedX) {
                o.position.x = width - o.position.x;
            }
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