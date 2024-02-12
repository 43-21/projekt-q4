package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import support.Functionality;

public class Shape implements Drawable {
    // Point centerSquare;
    int scale;
    int width = 0;
    int height = 0;
    ArrayList<Square> squares;

    public Shape(int scale) {
        this.scale = scale;
        squares = new ArrayList<>();
    }

    @Override
    public Image getSprite() {
        BufferedImage image = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width * scale, height * scale);

        for(Square square : squares) {
            graphics.setColor(Functionality.getDisplayColorFromEncoding(square.color));
            graphics.fillRect(square.x * scale, square.y * scale, scale, scale);
        }

        graphics.dispose();
        return image;

    }

    public void addSquare(int x, int y, boolean[] color) {
        squares.add(new Square(x, y, color));
        if(x + 1 > width) width = x + 1;
        if(y + 1 > height) height = y + 1;
    }
}

class Square {
    int x, y;
    boolean[] color;

    public Square(int x, int y, boolean[] color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
}