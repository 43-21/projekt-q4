package overlay;

import java.awt.Color;

/**
 * Ein Text mit Farbe und Dauer, der oben links im Overlay angezeigt wird.
 */
public class Message {
    private String content;
    private Color color;
    private long startTime;
    private int duration;

    /**
     * Erstellt die Message mit Inhalt und Dauer und schwarzer Farbe
     * @param content
     * @param duration
     */
    public Message(String content, int duration) {
        this.content = content;
        this.duration = duration;

        this.color = Color.BLACK;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Erstellt die Message mit Inhalt, Dauer und Farbe
     * @param content
     * @param duration
     * @param color
     */
    public Message(String content, int duration, Color color) {
        this.content = content;
        this.duration = duration;
        this.color = color;

        this.startTime = System.currentTimeMillis();
    }

    /**
     * Gibt aus, ob die Message ihre Lebensdauer überschritten hat.
     * @return true, wenn sie gelöscht werden sollte.
     */
    public boolean old() {
        return this.startTime + duration < System.currentTimeMillis();
    }

    public Color getColor() {
        return color;
    }

    public String getContent() {
        return content;
    }
}
