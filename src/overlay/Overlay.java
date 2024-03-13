package overlay;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import organism.Organism;
import support.Options;
import world.Positioned;

/**
 * Das Overlay wird als Bild über den sonstigen Inhalt des Displays gelegt
 * und enthält Linien und Text als zusätzliche Information.
 */
public class Overlay {
    private ArrayList<Line> lines;
    private Positioned focus;
    private ArrayList<String> messages;
    private ArrayList<Message> advancedMessages;

    /**
     * Erstellt ein leeres Overlay.
     */
    public Overlay() {
        this.lines = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.focus = null;
        this.advancedMessages = new ArrayList<>();
    }

    /**
     * Erstellt ein Image mit Breite und Höhe der Welt, wo der Inhalt des Overlays abgebildet ist.
     * @return das Image
     */
    public Image getOverlay() {
        BufferedImage image = new BufferedImage(Options.width, Options.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(new Color(1f, 1f, 1f, 0f));
        graphics.fillRect(0, 0, Options.width, Options.height);

        for(Line line : lines) {
            graphics.setColor(line.color);
            graphics.drawLine((int) line.a.x, (int) line.a.y, (int) line.b.x, (int) line.b.y);
        }

        int y = 10;
        graphics.setColor(Color.BLACK);
        for(String message : messages) {
            graphics.drawString(message, 10, y);
            y += 10;
        }

        for(Message message : advancedMessages) {
            graphics.setColor(message.getColor());
            graphics.drawString(message.getContent(), 10, y);
            y += 10;
        }

        graphics.setColor(Color.BLACK);

        if(focus != null && Options.showInformationOnFocus) {
            y += 10;
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.HALF_UP);

            graphics.drawString("Position: ( " + df.format(focus.getPosition().x) + " | " + df.format(focus.getPosition().y) + " )", 10, y);
            if(focus instanceof Organism) {
                graphics.drawString("Rotation: " + df.format(((Organism) focus).getRotation() / Math.PI * 180) + "°", 10, y + 10);
                graphics.drawString("Energie: " + df.format(((Organism) focus).getEnergy()), 10, y + 20);
                graphics.drawString("Alter: " + ((Organism) focus).getAge(), 10, y + 30);
            }
        }

        graphics.dispose();
        return image;
    }

    public void addLine(Line line) {
        this.lines.add(line);
    }

    /**
     * Setzt den Fokus auf ein bestimmtes Objekt, sodass zusätzliche Informationen
     * darüber angezeigt werden, sofern das in den Optionen so eingestellt ist
     * @param object
     */
    public void setFocus(Positioned object) {
        this.focus = object;
    }

    public Positioned getFocus() {
        return focus;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public void addAdvancedMessage(Message message) {
        this.advancedMessages.add(message);
    }

    /**
     * Erstellt eine Message mit dem Inhalt und der Dauer und fügt sie dem Overlay hinzu.
     * @param content
     * @param duration
     */
    public void addAdvancedMessage(String content, int duration) {
        this.advancedMessages.add(new Message(content, duration));
    }

    /**
     * Löscht Linien und normale Nachrichten; löscht die Messages
     * wenn sie ihre Lebensdauer überschritten haben
     */
    public void clear() {
        this.lines = new ArrayList<>();
        this.messages = new ArrayList<>();

        advancedMessages.removeIf(m -> {
            return m.old();
        });
    }
}