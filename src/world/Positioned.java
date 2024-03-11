package world;

import java.awt.geom.Point2D.Double;
import java.util.concurrent.ThreadLocalRandom;

// Oblekte dieser Art haben eine Position
public abstract class Positioned implements WithShape {
    protected Double position = new Double();

    public Double getPosition() {
        return position;
    }

    public void setPosition(double x, double y) {
        position.setLocation(x, y);
    }

    // Objekt wird auf eine zufällige Position gesetzt
    public void setRandomPosition(int width, int height) {
        double x = ThreadLocalRandom.current().nextDouble(width);
        double y = ThreadLocalRandom.current().nextDouble(height);

        position.setLocation(x, y);
    }
}
