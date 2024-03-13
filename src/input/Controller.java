package input;

import java.awt.event.KeyEvent;

public class Controller {
    private Input input;

    /**
     * Erstellt einen Controller mit dem eingegebenen Input.
     * @param input der Input...
     */
    public Controller(Input input) {
        this.input = input;
    }

    /**
     * Bestimmt, ob der Nutzer pausieren will.
     * @return true, wenn die Leertaste gedrückt ist.
     */
    public boolean isRequestingPause() {
        return input.isTyped(KeyEvent.VK_SPACE);
    }

    /**
     * Bestimmt, ob die Maus geklickt wurde.
     * @return true, wenn die Maus geklickt wurde.
     */
    public boolean getMouseClicked() {
        return input.getMouseClicked();
    }
}
