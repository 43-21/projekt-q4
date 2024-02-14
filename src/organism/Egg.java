package organism;

import java.awt.Image;

import world.Drawable;
import world.Dynamic;
import world.Positioned;
import world.Shape;

public class Egg extends Positioned implements Dynamic, Drawable {
    Shape shape;
    Genes genes;
    int timeToHatch;

    int time = 0;
    boolean fertilised = true; //false

    public Egg(Genes genes, int timeToHatch) {
        this.genes = genes;
        this.timeToHatch = timeToHatch;

        shape = new Shape(10);
        boolean[] color = {true, true, false};
        shape.addSquare(1, 0, color);
        shape.addSquare(0, 1, color);
        shape.addSquare(2, 1, color);
        shape.addSquare(1, 2, color);
    }

    public void update() {
        if(fertilised) time++;
    }

    // public void fertilise(Genes partner) {
    //     genes = genes.recombine(partner);
    //     fertilised = true;
    // }

    public boolean canHatch() {
        return time > timeToHatch;
    }

    public Organism hatch() {
        return new Organism(genes);
    }

    @Override
    public Image getSprite() {
        // BufferedImage image = new BufferedImage(20, 30, BufferedImage.TYPE_INT_RGB);
        // Graphics2D graphics = image.createGraphics();

        // graphics.setColor(Color.BLACK);
        // graphics.fillRect(0, 0, 20, 30);

        // graphics.dispose();
        // return image;
        return shape.getSprite();
    }
}