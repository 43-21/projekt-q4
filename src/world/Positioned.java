package world;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Positioned {
    protected Point position = new Point();

    public Point getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        position.setLocation(x, y);
    }

    public void setRandomPosition(int width, int height) {
        int x = ThreadLocalRandom.current().nextInt(width);
        int y = ThreadLocalRandom.current().nextInt(height);

        position.setLocation(x, y);
    }
}
