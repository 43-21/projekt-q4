package world;

import java.awt.Point;
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

            if(o.position.x >= 0 && o.position.x < width && o.position.y >= 0 && o.position.y < height) continue;
            if(!(o instanceof Organism)) continue;

            Point endOfLine = Functionality.getDestinationPoint(o.position, ((Organism) o).getRotation() + Math.PI, width + height);
            int xA, xB, yA, yB;
            if(((Organism) o).getRotation() < Math.PI) {
                xA = 0;
                xB = width - 1;
                yA = height - 1;
                yB = height - 1;
            }

            else {
                xA = 0;
                xB = width - 1;
                yA = 0;
                yB = 0;
            }
            Point a = new Point(xA, yA);
            Point b = new Point(xB, yB);
            Point intersectionPoint = Functionality.getIntersectionPoint(a, b, o.position, endOfLine);
            System.out.println(intersectionPoint);
            if(intersectionPoint != null) {
                o.position = intersectionPoint;
                continue;
            }
            
            if(((Organism) o).getRotation() < Math.PI / 2.0 || ((Organism) o).getRotation() > Math.PI * (3.0 / 2.0)) {
                xA = width - 1;
                xB = width - 1;
                yA = 0;
                yB = height - 1;
            }

            else {
                xA = 0;
                xB = 0;
                yA = 0;
                yB = height - 1;
            }
            a = new Point(xA, yA);
            b = new Point(xB, yB);
            intersectionPoint = Functionality.getIntersectionPoint(a, b, o.position, endOfLine);
            o.position = intersectionPoint;
            System.out.println(intersectionPoint);
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