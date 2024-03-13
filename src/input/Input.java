package input;

import java.awt.event.*;

/**
 * Der Input dient als KeyListener und MouseListener und soll somit Input von Maus und Tastatur erkennen können.
 */
public class Input implements KeyListener, MouseListener {
    private boolean[] pressed;
    private boolean[] typed;
    private boolean mouseClicked = false;

    /**
     * Erstellt einen neuen Input.
     */
    public Input() {
        pressed = new boolean[255];
        typed = new boolean[255];
        mouseClicked = false;
    }

    /**
     * Bestimmt, ob eine Taste gedrückt ist
     * @param keyCode der Key Code der Taste
     * @return true, wenn die Taste gedrückt ist
     */
    public boolean isPressed(int keyCode) {
        return pressed[keyCode];
    }

    /**
     * Bestimmt, ob eine Taste getippt wurde
     * @param keyCode der Key Code der Taste
     * @return true, wenn die Taste getippt wurde
     */
    public boolean isTyped(int keyCode) {
        if(typed[keyCode]) {
            typed[keyCode] = false;
            return true;
        }

        return false;
    }

    /**
     * Bestimmt, ob die Maus geklickt wurde
     * @return true, wenn die Maus geklickt wurde
     */
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
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}