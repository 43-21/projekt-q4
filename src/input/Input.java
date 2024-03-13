package input;

import java.awt.event.*;


public class Input implements KeyListener, MouseListener, MouseWheelListener {
    private boolean[] pressed;
    private boolean[] typed;
    private boolean mousePressed;
    private boolean mouseClicked = false;

    public Input() {
        pressed = new boolean[255];
        typed = new boolean[255];
        mousePressed = false;
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed[e.getKeyCode()] = false;
        typed[e.getKeyCode()] = true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseClicked = true;
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
    }
}