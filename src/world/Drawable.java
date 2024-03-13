package world;

import java.awt.Image;
import java.awt.Point;

/**
 * Interface für Objekte, die auf dem Display zeichenbar sind.
 */
public interface Drawable {
    /**
     * Erstellt die Abbildung des Objekts
     * @return das Image mit der Abbildung
     */
    public Image getSprite();
    /**
     * Ermittelt die Position, an der das Objekt gezeichnet werden soll
     * @return diese Position
     */
    public Point getDrawPosition();
}