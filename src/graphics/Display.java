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

    public void render(World world, Overlay overlay) {
        ArrayList<Drawable> objects = world.getDrawables();

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

    public Canvas getCanvas() {
        return canvas;
    }
}