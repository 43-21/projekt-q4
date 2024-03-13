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

/**
 * Die Welt der Simulation.
 * Speichert Objekte in einer Matrix.
 */
public class World {
    public Overlay overlay;

    Matrix objects;
    Food food;

    int width, height;
    int time = 0;
    int amountOfOrganisms = Options.amountOfOrganisms;

    int counter = 0;

    /**
     * Erstellt die Welt mit der gegebenen Höhe und Breite
     * @param width
     * @param height
     */
    public World(int width, int height) {
        this.width = width;
        this.height = height;

        this.food = new Food(Options.amountOfFood);

        objects = new Matrix(Options.amountOfHorizontalCells, Options.amountOfVerticalCells, width, height);

        for (int i = 0; i < Options.amountOfOrganisms; i++) {
            Organism organism = new Organism(new Genes(8, 6));
            organism.setRandomPosition(width, height);
            objects.add(organism);
        }
    }

    /**
     * Führt die Updates für alle Objekte der Welt aus und ermittelt davor und danach
     * die notwendigen Informationen dafür. Bestimmt die Logik des Geschehens.
     */
    public void update() {
        time++;
        // Abfolge:
        // 1. Inputs der Gehirne ermitteln
        // 2. Alles updaten, essen, koordinaten etc

        for (Positioned o : objects) {
            if (o instanceof Organism) {
                boolean hasFocus = overlay.getFocus() == o;

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
                    if(!Options.showCommunication) continue;
                    if(Options.showSensesOnlyOnFocus && !hasFocus) continue;
                    overlay.addLine(new Line(p.position, o.position, Color.CYAN));
                }
                
                //SICHT
                double length = Options.viewRange;
                Double endPoint = Functionality.getDestinationPoint(o.position, ((Organism) o).getRotation(), length);

                ArrayList<Positioned> possible = objects.searchRay(o.getPosition(), ((Organism) o).getRotation(), length);

                if(Options.showViewRange) { 
                    if(!Options.showSensesOnlyOnFocus || hasFocus ) {
                        overlay.addLine(new Line(o.position, endPoint, Color.ORANGE));
                    }
                }

                double currentDistanceSquared = java.lang.Double.POSITIVE_INFINITY;
                Positioned closest = null;
                for (Positioned p : possible) {
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

                    if (hasFocus && Options.showPossiblyInView) {
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
                        if(Options.showView) {  
                            if(!Options.showSensesOnlyOnFocus || hasFocus) {
                                overlay.addLine(new Line(o.position, intersection, Color.MAGENTA));
                            }
                        }
                    }

                    else {
                        Square square = closest.intersecting(o.position, endPoint);
                        color = square.getColor();
                    }
                }

                else {
                    Square square = closest.intersecting(o.position, endPoint);
                    color = square.getColor();
                    if(Options.showView) {
                        if(!Options.showSensesOnlyOnFocus || hasFocus) {
                            overlay.addLine(new Line(o.position, closest.getIntersectionPoint(o.position, endPoint), Color.MAGENTA));
                        }
                    }
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
                    toBeRemoved.add(o);

                    if(Options.showLogs) {
                        overlay.addAdvancedMessage("Added child: " + child, 3000);
                    }
                }
            }

            if (o instanceof Organism) {
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
                    if(Options.showLogs) {
                        overlay.addAdvancedMessage("Added egg: " + child, 4000);
                    }
                }
            }
        }

        objects.update();
        objects.removeAll(toBeRemoved);
        objects.addAll(toBeAdded);

        if(!Options.showWorldInformation) return;
        overlay.addMessage("Zeitpunkt: " + time);
        overlay.addMessage("Anzahl an Essen: " + food.getAmountOfFood());
        overlay.addMessage("Anzahl der Organismen: " + amountOfOrganisms);
    }

    /**
     * Gibt alle Objekte wieder, die Drawable implementieren
     * @return
     */
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

    /**
     * Stellt fest, ob sich bei der Mausposition ein Objekt befindet.
     * @param mousePosition
     * @return das Objekt oder null, wenn es keins gibt
     */
    public Positioned getPositionedOnMouse(Point mousePosition) {
        Double position = new Double(mousePosition.x, mousePosition.y);
        if(Options.showLogs) overlay.addAdvancedMessage(String.format("Maus bei x: %d, y: %d", mousePosition.x, mousePosition.y), 3000);
        for (Positioned object : objects.searchRect(position, Organism.scale * 2, Organism.scale * 2)) {
            if (object.intersecting(position)) {
                return object;
            }
        }
        return null;
    }
}