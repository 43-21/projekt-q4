package input;

import java.awt.event.KeyEvent;

public class Controller {
    private Input input;

    public Controller(Input input) {
        this.input = input;
    }

    public boolean isRequestingPause() {
        return input.isTyped(KeyEvent.VK_SPACE);
    }

    public boolean getMouseClicked() {
        return input.getMouseClicked();
    }
}
