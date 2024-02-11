package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Shape implements Drawable {
    //oder so...
    Point[] vertices;
    Edge[] edges;
    //mittelpunkt sollte auch mal definiert werden damit man das schön rotieren kann

    public Shape(int amountOfVertices) {

    }

    @Override
    public Image getSprite() {
        BufferedImage image = new BufferedImage(20, 30, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        //magie

        graphics.dispose();
        return image;

    }
}

class Edge {
    int fromVertex;
    int toVertex;
    Color color;

    public Edge(int from, int to, Color color) {
        fromVertex = from;
        toVertex = to;
        this.color = color;
    }
}