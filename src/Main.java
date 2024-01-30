import graphics.Display;
import world.World;

class Main {
    public static void main(String[] args) {
        new Thread(new Loop(new World(), new Display())).start();
    }
}