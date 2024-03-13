package world;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

import support.Functionality;
import support.Options;

public class Food implements Dynamic, Drawable {
    ArrayList<Double> food = new ArrayList<>();
    // EnergyField[] fields;
    int desiredAmountOfFood;
    double accumulator = 0.0;
    Shape energyShape;
    final int scale = Options.foodScale;

    public Food(int food, int amountOfFields) {
        desiredAmountOfFood = food;
        // fields = new EnergyField[amountOfFields];
        
        // for(int i = 0; i < amountOfFields; i++) {
        //     fields[i] = new EnergyField(125, 50);
        // }

        for(int i = 0; i < food; i++) {
            addFood();
        }

        energyShape = new Shape(3);
        energyShape.addSquare(1, 0, new boolean[]{true, true, true});
        energyShape.addSquare(0, 1, new boolean[]{true, true, true});
        energyShape.addSquare(2, 1, new boolean[]{true, true, true});
        energyShape.addSquare(1, 2, new boolean[]{true, true, true});
        energyShape.addSquare(1, 1, new boolean[]{false, false, false});
        energyShape.setCenter(1, 1);
        energyShape.setPositionKind(Shape.CENTER);
    }

    public void update() {
        if(food.size() < desiredAmountOfFood) {
            accumulator += Options.foodSpawnRate;
        }

        double random = ThreadLocalRandom.current().nextDouble();
        if(accumulator >= random && addFood()) accumulator -= random;

        // for(EnergyField field : fields) {
        //     field.update();
        // }
    }

    //true wenn erfolgreich
    private boolean addFood() {
        double x = ThreadLocalRandom.current().nextDouble(Options.width);
        double y = ThreadLocalRandom.current().nextDouble(Options.height);

        Double newPosition = new Double(x, y);
        boolean collision = true;

        for(int i = 0; i < 3; i++) {
            boolean noCollisionYet = true;
            for(Double position : food) {
                if(Functionality.checkForCollision(position, scale * 3, newPosition, scale * 3)) {
                    noCollisionYet = false;
                    x = ThreadLocalRandom.current().nextDouble(Options.width);
                    y = ThreadLocalRandom.current().nextDouble(Options.height);
                }
            }
            if(noCollisionYet) {
                collision = false;
                break;
            }
        }

        if(!collision) food.add(new Double(x, y));
        return !collision;
    }

    public ArrayList<Integer> checkForCollision(Positioned object) {
        ArrayList<Integer> list = new ArrayList<>();

        int i = 0;
        for(Double energy : food) {
            if(object.colliding(energyShape, energy)) {
                list.add(i);
            }
            i++;
        }

        return list;
    }

    public Double checkForLineIntersection(Double a, Double b) {
        Double current = null;
        double currentSquareDistance = java.lang.Double.POSITIVE_INFINITY;
        for(Double energy : food) {
            Double intersection = Functionality.getClosestIntersection(energyShape, energy, a, b);
            if(intersection == null) continue;
            double squareDistance = Functionality.squareDistance(intersection, a);
            if(currentSquareDistance > squareDistance) {
                currentSquareDistance = squareDistance;
                current = intersection;
            }
        }
        return current;
    }

    public double removeEnergy(int index) {
        food.remove(index);
        return Options.energyInFood;
    }

    public Image getSprite() {
        BufferedImage image = new BufferedImage(Options.width, Options.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        Image sprite = energyShape.getSprite();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, Options.width, Options.height);
        
        for(Double position : food) {
            Point drawPosition = getAbsolutePosition(position, energyShape.getRelativePosition());
            graphics.drawImage(sprite, drawPosition.x, drawPosition.y, null);
        }

        graphics.dispose();
        return image;
    }

    private Point getAbsolutePosition(Double position, Double translation) {
        double x = position.x + translation.x;
        double y = position.y + translation.y;
        return new Point((int) x, (int) y);
    }

    public Point getDrawPosition() {
        return new Point(0, 0);
    }

    public int getAmountOfFood() {
        return food.size();
    }
}