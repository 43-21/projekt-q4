package world;

import java.util.ArrayList;

import organism.Egg;
import organism.Genes;
import organism.Organism;
import support.*;

public class World {
    Matrix objects;
    Food food;

    int width, height;
    public int time = 0;

    public World(int width, int height) {
        this.width = width;
        this.height = height;

        this.food = new Food(Options.amountOfFood, 0);

        objects = new Matrix(16, 10, width, height);

        for(int i = 0; i < Options.amountOfOrganisms; i++) {
            Organism organism = new Organism(new Genes(8, 6));
            organism.setRandomPosition(width, height);
            objects.add(organism);
        }
    }

    public void update() {
        time++;
        //abfolge:
        //1. inputs ermitteln
        //2. alle updaten, essen, koordinaten etc

        for(Positioned o : objects) {
            if(o instanceof Organism) {
                Positioned nextObject = objects.searchRay(o.getPosition(), ((Organism) o).getRotation(), 60.0);
                double distance = Functionality.distance(nextObject.position, o.getPosition());
                boolean[] color = o.getShape().getSquares().get(0).getColor();
                boolean[] inputs = new boolean[8]; //3 für farbe, 2 für entfernung, 3 für kommunikation
                for(int i = 0; i < color.length; i++) {
                    inputs[i] = color[i];
                }
                if(distance <= 40) inputs[3] = true;
                if(distance <= 20) inputs[4] = true;
                
                ((Organism) o).setInputs(inputs);
            }
        }

        //evtl muss das verschoben werden
        food.update();

        for(Positioned o : objects) {
            if(o instanceof Dynamic) {
                ((Dynamic) o).update();
            }

            if(o.position.x < 0) o.position.x = 0;
            else if(o.position.x >= width) o.position.x = width - 1;
            if(o.position.y < 0) o.position.y = 0;
            else if(o.position.y >= height) o.position.y = height - 1;

            if(o instanceof Egg) {
                if(((Egg) o).canHatch()) {
                    objects.add(((Egg) o).hatch());
                    objects.remove(o);
                }
            }

            if(o instanceof Organism) {
                ArrayList<Integer> indices = food.checkForCollision(o.getPosition(), 15.0);
                for(int index : indices) {
                    ((Organism) o).eat(food.removeEnergy(index));
                }

                if(((Organism) o).getEnergy() < Options.requiredEnergy) {
                    objects.remove(o);
                }

                else if(((Organism) o).getEnergy() >= Options.reproductionEnergy) {
                    Egg child = ((Organism)o).layEgg();
                    objects.add(child);
                }
            }
        }
    }

    public ArrayList<Drawable> getDrawables() {
        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.add(food);

        for(Positioned thing : objects) {
            if(thing instanceof Drawable) {
                drawables.add((Drawable) thing);
            }
        }

        return drawables;
    }
}