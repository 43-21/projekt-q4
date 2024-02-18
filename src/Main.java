import graphics.Display;
import world.World;
import support.Options;

class Main {
    public static void main(String[] args) {
        new Thread(new Loop(new World(Options.width, Options.height), new Display(Options.width, Options.height))).start();
    }
}