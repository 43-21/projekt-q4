package world;

import java.awt.Image;
import java.awt.Point;

// Objekte dieser Art können gezeichnet werden
public interface Drawable {
    public Image getSprite();
    public Point getDrawPosition();
}