package organism;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import world.Drawable;
import world.Dynamic;
import world.Positioned;

public class Egg extends Positioned implements Dynamic, Drawable {
    Genes genes;
    int timeToHatch;

    int time = 0;
    boolean fertilised = true; //false

    public Egg(Genes genes, int timeToHatch) {
        this.genes = genes;
        this.timeToHatch = timeToHatch;
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
        return new Organism(position, genes);
    }

    @Override
    public Image getSprite() {
        BufferedImage image = new BufferedImage(20, 30, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, 20, 30);

        graphics.dispose();
        return image;
    }
}