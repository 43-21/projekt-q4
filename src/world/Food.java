package world;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.geom.Point2D.Double;

import support.Functionality;
import support.Options;

public class Food {
    ArrayList<Double> food = new ArrayList<>();
    EnergyField[] fields;
    int desiredAmountOfFood;
    double accumulator = 0.0;

    public Food(int food, int amountOfFields) {
        desiredAmountOfFood = food;
        fields = new EnergyField[amountOfFields];
        
        for(int i = 0; i < amountOfFields; i++) {
            fields[i] = new EnergyField(125, 50);
        }

        for(int i = 0; i < food; i++) {
            addFood();
        }
    }

    public void update() {
        if(food.size() < desiredAmountOfFood) {
            accumulator += 0.05;
        }

        double random = ThreadLocalRandom.current().nextDouble();
        if(accumulator >= random && addFood()) accumulator -= random;
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
                if(Functionality.checkForCollision(position, 15.0, newPosition, 15.0)) {
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

    public ArrayList<Integer> checkForCollision(Double position, double radius) {
        ArrayList<Integer> list = new ArrayList<>();

        int i = 0;
        for(Double energy : food) {
            if(Functionality.checkForCollision(energy, 15.0, position, radius)) {
                list.add(i);
            }
            i++;
        }

        return list;
    }
}