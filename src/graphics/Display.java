package graphics;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;

import organism.Egg;
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
            Point position = object.getDrawPosition();
            graphics.drawImage(
                object.getSprite(),
                position.x, position.y,
                null
            );

            if(object instanceof Positioned) {
                int x = (int) ((Positioned) object).getPosition().x;
                int y = (int) ((Positioned) object).getPosition().y;
                graphics.setColor(Color.ORANGE);
                graphics.drawString(x + " " + y, x, y);
                graphics.setColor(Color.BLACK);
            }

            if(object instanceof organism.Organism) {
                DecimalFormat df = new DecimalFormat("#.##");
                df.setRoundingMode(RoundingMode.HALF_UP);
                graphics.drawString(df.format(((organism.Organism) object).getEnergy()), 10, 40);
            }

            if(object instanceof world.Food) {
                graphics.drawString("" + ((world.Food) object).getAmountOfFood(), 10, 50);
            }
        }

        graphics.drawString(canvas.getWidth() + " " + canvas.getHeight(), 10, 30);

        graphics.drawString(String.valueOf(time), 10, 10);

        graphics.drawString(String.valueOf(objects.size()), 10, 60);


        graphics.dispose();
        bufferStrategy.show();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}