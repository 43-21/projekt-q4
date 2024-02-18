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

    int counter = 0;

    public World(int width, int height) {
        this.width = width;
        this.height = height;

        this.food = new Food(Options.amountOfFood, 0);

        objects = new Matrix(Options.amountOfHorizontalCells, Options.amountOfVerticalCells, width, height);

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
        // System.out.println("------------------------------------");
        
        for(Positioned o : objects) {
            if(o instanceof Organism) {
                double length = 120.0;
                Positioned nextObject = objects.searchRay(o.getPosition(), ((Organism) o).getRotation(), length);
                boolean[] inputs = new boolean[8]; //3 für farbe, 2 für entfernung, 3 für kommunikation
                if(nextObject == null) {
                    ((Organism) o).setInputs(inputs);
                    continue;
                }

                double distance = Functionality.distance(nextObject.position, o.getPosition());
                boolean[] color = o.getShape().getSquares().get(0).getColor();
                for(int i = 0; i < color.length; i++) {
                    inputs[i] = color[i];
                }

                System.out.println(distance);

                if(distance <= length / 3.0 * 2.0) inputs[3] = true;
                if(distance <= length / 3.0) inputs[4] = true;
                
                ((Organism) o).setInputs(inputs);
            }
        }

        //evtl muss das verschoben werden
        food.update();

        ArrayList<Positioned> toBeRemoved = new ArrayList<>();
        ArrayList<Positioned> toBeAdded = new ArrayList<>();

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
                    Organism child = ((Egg) o).hatch();
                    toBeAdded.add(child);
                    System.out.println("Added child: " + child);
                    toBeRemoved.add(o);
                }
            }

            if(o instanceof Organism) {
                ArrayList<Integer> indices = food.checkForCollision(o.getPosition(), 15.0);
                for(int index : indices) {
                    ((Organism) o).eat(food.removeEnergy(index));
                }

                if(((Organism) o).getEnergy() < Options.requiredEnergy) {
                    toBeRemoved.add(o);
                }

                else if(((Organism) o).getEnergy() >= Options.reproductionEnergy) {
                    Egg child = ((Organism)o).layEgg();
                    toBeAdded.add(child);
                    System.out.println("Added egg: " + child);
                }
            }
        }

        objects.update();
        objects.removeAll(toBeRemoved);
        objects.addAll(toBeAdded);
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