import graphics.Display;
import world.World;

class Main {
    public static void main(String[] args) {
        new Thread(new Loop(new World(1920, 1200), new Display(1920, 1200))).start();
    }
}