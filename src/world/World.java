package world;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.geom.Point2D.Double;

import organism.Egg;
import organism.Genes;
import organism.Organism;
import overlay.Line;
import overlay.Overlay;
import support.*;

// Die Welt der Simulation
public class World {
    public Overlay overlay;

    Matrix objects;
    Food food;

    int width, height;
    int time = 0;
    int amountOfOrganisms = Options.amountOfOrganisms;

    int counter = 0;

    // Die Welt wird erstellt und befüllt
    public World(int width, int height) {
        this.width = width;
        this.height = height;

        this.food = new Food(Options.amountOfFood, 0);

        objects = new Matrix(Options.amountOfHorizontalCells, Options.amountOfVerticalCells, width, height);

        for (int i = 0; i < Options.amountOfOrganisms; i++) {
            Organism organism = new Organism(new Genes(8, 6));
            organism.setRandomPosition(width, height);
            objects.add(organism);
        }
    }

    public void update() {
        time++;
        // Abfolge:
        // 1. Inputs der Gehirne ermitteln
        // 2. Alles updaten, essen, koordinaten etc

        for (Positioned o : objects) {
            if (o instanceof Organism) {
                boolean[] inputs = new boolean[8]; // 3 für farbe, 2 für entfernung, 3 für kommunikation

                //KOMMUNIKATION
                double radius = Options.communicationRadius;
                ArrayList<Positioned> possiblyInRadius = objects.searchRect(o.position, radius, radius);
                for(Positioned p : possiblyInRadius) {
                    if(!(p instanceof Organism)) continue;
                    if(p == o) continue;
                    if(Functionality.squareDistance(p.position, o.position) > radius * radius) continue;
                    boolean[] outputs = ((Organism)o).getOutputs();
                    for(int i = 3; i < 6; i++) {
                        if(outputs[i]) {
                            inputs[i + 2] = true;
                        }
                    }
                    overlay.addLine(new Line(p.position, o.position, Color.CYAN));
                }
                
                //SICHT
                double length = Options.viewRange;
                Double endPoint = Functionality.getDestinationPoint(o.position, ((Organism) o).getRotation(), length);

                // ArrayList<Positioned> possible = objects.searchRay(o.getPosition(),
                // ((Organism) o).getRotation(), length);

                overlay.addLine(new Line(o.position, endPoint, Color.ORANGE));

                double currentDistanceSquared = java.lang.Double.POSITIVE_INFINITY;
                Positioned closest = null;
                for (Positioned p : objects) {
                    if (p.equals(o)) {
                        continue;
                    }
                    Double intersection = p.getIntersectionPoint(o.position, endPoint);
                    if (intersection != null) {
                        double distance = Functionality.squareDistance(o.position, intersection);
                        if (currentDistanceSquared > distance) {
                            closest = p;
                            currentDistanceSquared = distance;
                        }
                    }

                    if (overlay.getFocus() == o) {
                        overlay.addLine(new Line(o.position, p.position));
                    }
                }

                boolean[] color;

                Double intersection = food.checkForLineIntersection(o.position, endPoint);
                if(intersection == null && closest == null) {
                    ((Organism) o).setInputs(inputs);
                    continue;
                }
                if (intersection != null) {
                    double squareDistance = Functionality.squareDistance(o.position, intersection);

                    if (currentDistanceSquared > squareDistance) {
                        color = new boolean[] { true, true, true };
                        overlay.addLine(new Line(o.position, intersection, Color.MAGENTA));
                    }

                    else {
                        Square square = closest.intersecting(o.position, endPoint);
                        color = square.getColor();
                    }
                }

                else {
                    Square square = closest.intersecting(o.position, endPoint);
                    color = square.getColor();
                    overlay.addLine(new Line(o.position, closest.getIntersectionPoint(o.position, endPoint), Color.MAGENTA));
                }

                for (int i = 0; i < color.length; i++) {
                    inputs[i] = color[i];
                }

                if (currentDistanceSquared <= (length * length) / 9.0 * 4.0)
                    inputs[3] = true;
                if (currentDistanceSquared <= (length * length) / 9.0)
                    inputs[4] = true;

                ((Organism) o).setInputs(inputs);
            }
        }

        food.update();

        ArrayList<Positioned> toBeRemoved = new ArrayList<>();
        ArrayList<Positioned> toBeAdded = new ArrayList<>();

        for (Positioned o : objects) {
            if (o instanceof Dynamic) {
                ((Dynamic) o).update();
            }

            if (o.position.x < 0)
                o.position.x = 0;
            else if (o.position.x >= width)
                o.position.x = width - 1;
            if (o.position.y < 0)
                o.position.y = 0;
            else if (o.position.y >= height)
                o.position.y = height - 1;

            if (o instanceof Egg) {
                if (((Egg) o).canHatch()) {
                    Organism child = ((Egg) o).hatch();
                    amountOfOrganisms++;
                    toBeAdded.add(child);
                    System.out.println("Added child: " + child);
                    toBeRemoved.add(o);
                }
            }

            if (o instanceof Organism) {
                boolean[] outputs = ((Organism)o).getOutputs();

                /*
                 * if output[0]: move
                 * check for collision
                 * if collision: move back
                 */

                // if(outputs[0]) {
                //     o.position.x += Math.cos(((Organism) o).getRotation()) * Options.speed;
                //     o.position.y += -Math.sin(((Organism) o).getRotation()) * Options.speed;

                //     for(Positioned p : objects.searchRect(o.position, Options.organismScale * 2, Options.organismScale * 2)) {
                //         if(o.colliding(p)) {
                //             o.position.x -= Math.cos(((Organism) o).getRotation()) * Options.speed;
                //             o.position.y -= -Math.sin(((Organism) o).getRotation()) * Options.speed;
                //         }
                //     }
                // }


                ArrayList<Integer> indices = food.checkForCollision(o);
                for (int index : indices) {
                    ((Organism) o).eat(food.removeEnergy(index));
                }

                if (((Organism) o).getEnergy() < Options.requiredEnergy) {
                    if (overlay.getFocus() == o) {
                        overlay.setFocus(null);
                    }
                    toBeRemoved.add(o);
                    amountOfOrganisms--;
                }

                else if (((Organism) o).getEnergy() >= Options.reproductionEnergy()) {
                    Egg child = ((Organism) o).layEgg();
                    toBeAdded.add(child);
                    System.out.println("Added egg: " + child);
                }
            }
        }

        objects.update();
        objects.removeAll(toBeRemoved);
        objects.addAll(toBeAdded);

        overlay.addMessage("Zeitpunkt: " + time);
        overlay.addMessage("Anzahl an Essen: " + food.getAmountOfFood());
        overlay.addMessage("Anzahl der Organismen: " + amountOfOrganisms);
    }

    public ArrayList<Drawable> getDrawables() {
        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.add(food);

        for (Positioned thing : objects) {
            if (thing instanceof Drawable) {
                drawables.add((Drawable) thing);
            }
        }

        return drawables;
    }

    public Positioned getPositionedOnMouse(Point mousePosition) {
        Double position = new Double(mousePosition.x, mousePosition.y);
        overlay.addAdvancedMessage(String.format("Maus bei x: %d, y: %d", mousePosition.x, mousePosition.y), 3000);
        for (Positioned object : objects.searchRect(position, Organism.scale, Organism.scale)) {
            if (object.intersecting(position)) {
                return object;
            }
        }
        return null;
    }
}