package graphics;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

import world.Drawable;
import world.Positioned;
import world.World;

public class Display extends JFrame {
    private Canvas canvas;
    int width;
    int height;

    public Display(int width, int height) {
        setTitle("Simulation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);

        add(canvas);
        pack();
        
        canvas.createBufferStrategy(3);

        setLocationRelativeTo(null);
        setVisible(true);

        this.width = width;
        this.height = height;
    }

    public void render(World world) {
        ArrayList<Drawable> objects = world.getDrawables();
        int time = world.time;

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
            int posX = 0;
            int posY = 0;
            if(object instanceof Positioned) {
                posX = ((Positioned) object).getPosition().x;
                posY = ((Positioned) object).getPosition().y;
            }
            graphics.drawImage(
                object.getSprite(),
                posX, posY,
                null
            );

            graphics.drawString(String.valueOf(posX) + " " + String.valueOf(posY), 10, 20);
        }

        graphics.drawString(canvas.getWidth() + " " + canvas.getHeight(), 10, 30);

        graphics.drawString(String.valueOf(time), 10, 10);

        graphics.dispose();
        bufferStrategy.show();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}