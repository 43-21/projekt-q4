package overlay;

import java.awt.Color;

public class Message {
    private String content;
    private Color color;
    private long startTime;
    private int duration;

    public Message(String content, int duration) {
        this.content = content;
        this.duration = duration;

        this.color = Color.BLACK;
        this.startTime = System.currentTimeMillis();
    }

    public Message(String content, int duration, Color color) {
        this.content = content;
        this.duration = duration;
        this.color = color;

        this.startTime = System.currentTimeMillis();
    }

    public boolean old() {
        System.out.println(this.startTime);
        System.out.println(System.currentTimeMillis());
        return this.startTime + duration < System.currentTimeMillis();
    }

    public Color getColor() {
        return color;
    }

    public String getContent() {
        return content;
    }
}
