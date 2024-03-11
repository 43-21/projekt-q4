package overlay;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import organism.Organism;
import support.Options;

public class Overlay {
    private ArrayList<Rect> rects;
    private ArrayList<Line> lines;
    private Organism focus;
    private ArrayList<String> messages;
    private ArrayList<Message> advancedMessages;

    public Overlay() {
        this.rects = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.focus = null;
        this.advancedMessages = new ArrayList<>();
    }

    public Image getOverlay() {
        BufferedImage image = new BufferedImage(Options.width, Options.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        RenderingHints hints = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
        );

        graphics.setRenderingHints(hints);
        
        graphics.setColor(new Color(1f, 1f, 1f, 0f));
        graphics.fillRect(0, 0, Options.width, Options.height);

        for(Rect rect : rects) {
            graphics.setColor(rect.color);
            int[] x = new int[4];
            int[] y = new int[4];

            x[0] = (int) rect.a.x;
            y[0] = (int) rect.a.y;

            x[1] = (int) rect.b.x;
            y[1] = (int) rect.b.y;

            x[2] = (int) rect.c.x;
            y[2] = (int) rect.c.y;

            x[3] = (int) rect.d.x;
            y[3] = (int) rect.d.y;

            graphics.drawPolygon(x, y, 4);
        }

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

        if(focus != null) {
            graphics.drawString("Position: ( " + focus.getPosition().x + " | " + focus.getPosition().y + " )", 10, y);
            graphics.drawString("Energie: " + focus.getEnergy(), 10, y + 10);
        }

        graphics.dispose();
        return image;
    }

    public void addLine(Line line) {
        this.lines.add(line);
    }

    public void addRect(Rect rect) {
        this.rects.add(rect);
    }

    public void setFocus(Organism organism) {
        this.focus = organism;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public void addAdvancedMessage(Message message) {
        this.advancedMessages.add(message);
    }

    public void clear() {
        this.rects = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.focus = null;
        this.messages = new ArrayList<>();

        advancedMessages.removeIf(m -> {
            System.out.println(m.old());
            return m.old();
        });
    }
}