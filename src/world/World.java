package world;

import java.util.ArrayList;

import organism.Egg;
import organism.Organism;
import support.*;

public class World {
    Matrix objects;
    int width, height;

    public void update() {
        //abfolge:
        //1. inputs ermitteln
        //2. alle updaten, essen, koordinaten etc

        for(Positioned o : objects) {
            if(o instanceof Organism) {
                ArrayList<Positioned> candidates = objects.searchRay(((Organism) o).position, ((Organism) o).getRotation(), 50f);
                Positioned visible;
            }
        }

        for(Positioned o : objects) {
            if(o instanceof Dynamic) {
                ((Dynamic) o).update();
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
