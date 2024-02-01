package world;

import java.util.*;
import java.lang.Math;
import java.awt.Point;


public class World implements Thing {
    Thing[] objects;

    //private int xLimit = 1;   ??
    //private int yLimit = 1;   ??

    @Override
    public void update() {

    }

    public Thing[] getObjects() {
        return objects;
    }
}

// eigene Class für jedes Entity die alle Cord implementen
abstract class Cords {
    private Point cords = new Point();

    public void generateCords() {
        private int maxValueY = 1080;       // standard cord range asuming a 1080x1920 Resolution for the world
        private int maxValueX = 1920;       // change if needed

        Random random1 = new Random();
        Random random2 = new Random();
        
        private int xCoordinate = random1.nextInt(maxValueX);
        private int yCoordinate = random2.nextInt(maxValueY);

        cords.setLocation(xCoordinate, yCoordinate);
    }

    public Pair getCords() {
        return this.Cords;
    }

    public void setCords(float x, float y) {
        this.Cords = (x, y);
    }
}

class Organism extends Coords {


} 

class Egg extends Coords {

}

class Bush extends Coords {

}

class Food extends Coords {    //Berries oder so idk

}