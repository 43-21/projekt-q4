package graphics;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

import input.Input;
import overlay.Overlay;
import world.Drawable;
import world.World;

public class Display extends JFrame {
    private Canvas canvas;
    int width;
    int height;

    /**
     * Initiiert das Display.
     * @param width die Breite des Displays in Pixeln
     * @param height die Höhe des Displays in Pixeln
     * @param input der Input des Pixels für Maus und Tastatur
     */
    public Display(int width, int height, Input input) {
        setTitle("Simulation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
        add(canvas);
        canvas.addMouseListener(input);
        addKeyListener(input);  
        addMouseWheelListener(input);

        pack();
        
        canvas.createBufferStrategy(3);

        setLocationRelativeTo(null);
        setVisible(true);

        this.width = width;
        this.height = height;
    }

    /**
     * Rendert das aktuelle Bild auf dem Display.
     * @param objects Die Objekte der Welt, die gezeichnet werden sollen
     * @param overlay Das Overlay, das gezeichnet werden soll
     */
    public void render(ArrayList<Drawable> objects, Overlay overlay) {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();

        RenderingHints hints = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
        );

        graphics.setRenderingHints(hints);

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        graphics.setColor(Color.BLACK);

        for(Drawable object : objects) {
            Point position = object.getDrawPosition();
            graphics.drawImage(
                object.getSprite(),
                position.x, position.y,
                null
            );
        }

        graphics.drawImage(overlay.getOverlay(), 0, 0, null);

        graphics.dispose();
        bufferStrategy.show();
    }

    /**
     * Gibt die Canvas des Displays aus, auf der gezeichnet wird.
     * @return die Canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }
}