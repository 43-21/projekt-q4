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

        this.food = new Food(75, 0);

        Egg egg = new Egg(new Genes(8, 6), 200);
        egg.setRandomPosition(width, height);

        objects = new Matrix(16, 10, width, height);
        //objects.add(egg);

        Organism organism = new Organism(new Genes(8, 6));
        organism.setRandomPosition(width, height);
        objects.add(organism);
        Organism organism1 = new Organism(new Genes(8, 6));
        organism1.setRandomPosition(width, height);
        objects.add(organism1);
        objects.add(egg);
    }

    public void update() {
        time++;
        //abfolge:
        //1. inputs ermitteln
        //2. alle updaten, essen, koordinaten etc

        for(Positioned o : objects) {
            if(o instanceof Organism) {
                // alle möglicherweise relevanten objekte bestimmen (also alles was in den matrix feldern ist wo der strahl ist)
                // davon alle strecken mit farbe vom rechteck bestimmen (gerne auch nur die relevanten wenn es schnell geht)
                // davon alle strecken die sich mit dem strahl schneiden bestimmen
                // (später sollte man auch rotationen berücksichtigen ist aber jetzt noch nicht so wichtig
                //  weil wir es ja eh noch nicht so anzeigen)
                // am ende braucht man farben mitsamt irgendwas um das nächste zu bestimmen
                // also skalierender faktor oder punkt oder was auch immer
                // damit die farbe vom nächsten rechteck quasi bestimmen. allerdings braucht man auch die
                // tatsächliche entfernung davon dann, damit man die dem organismus geben kann

                // evtl hilfreiche methoden:
                    // Matrix.searchRay - soll alle Objekte die den Strahl schneiden zurückgeben
                        // bis jetzt gibt er aber nur alle möglicherweise relevanten Objekte zurück
                        // also kannst du die ja anpassen wie es dir passt.
                    // und alles aus Functionality, musst halt schauen ob es funktioniert
                // außerdem solltest du mit Shape arbeiten da dort die rechtecke gespeichert sind
                boolean[] inputs = new boolean[8]; //3 für farbe, 2 für entfernung, 3 für kommunikation
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