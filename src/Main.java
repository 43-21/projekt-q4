import graphics.Display;
import support.Options;
import world.World;

class Main {
    public static void main(String[] args) {
        //GUI gui = new GUI();
        //gui.button.updateUI();
        new Thread(new Loop(new World(Options.width, Options.height), new Display(Options.width, Options.height))).start();
    }
}