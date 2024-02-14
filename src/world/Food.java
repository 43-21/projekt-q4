package world;

import java.awt.Point;
import java.util.ArrayList;

import world.World;

public class Food extends Positioned {

    public ArrayList<Food> spawnFood(int amount) {

        ArrayList<Food> list = new ArrayList<>();

        for(int i=amount; i>0; i--) {
            Food food = new Food();
            food.setRandomPosition(World.width, World.height);
            list.add(food);
        }
    }
}
