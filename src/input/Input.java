package input;

import java.awt.event.*;

import overlay.Message;
import overlay.Overlay;

public class Input implements KeyListener, MouseListener, MouseWheelListener {
    public Overlay overlay;

    private boolean[] pressed;
    private boolean[] typed;
    private boolean mousePressed;
    private boolean mouseClicked = false;
    private int mouseWheelRotation;

    public Input() {
        pressed = new boolean[255];
        typed = new boolean[255];
        mousePressed = false;
        mouseWheelRotation = 0;
        mouseClicked = false;
    }

    public boolean isPressed(int keyCode) {
        return pressed[keyCode];
    }

    public boolean isTyped(int keyCode) {
        if(typed[keyCode]) {
            typed[keyCode] = false;
            return true;
        }

        return false;
    }

    public boolean getMousePressed() {
        return mousePressed;
    }

    public boolean getMouseClicked() {
        if(mouseClicked) {
            mouseClicked = false;
            return true;
        };
        return false;
    }

    public int getMouseWheelRotation() {
        return mouseWheelRotation;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // overlay.addAdvancedMessage(new Message("Taste getippt: " + e.getKeyCode(), 3000));
        // typed[e.getKeyCode()] = true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        overlay.addAdvancedMessage(new Message("Taste gedrückt: " + e.getKeyCode(), 3000));
        pressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed[e.getKeyCode()] = false;
        typed[e.getKeyCode()] = true;
        overlay.addAdvancedMessage(new Message("Taste losgelassen: " + e.getKeyCode(), 3000));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseClicked = true;
        overlay.addAdvancedMessage(new Message("Mausklick!", 3000));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheelRotation = e.getWheelRotation();
    }
}