package input;

import java.awt.event.KeyEvent;

public class Controller {
    private Input input;

    public Controller(Input input) {
        this.input = input;
    }

    public boolean isRequestingSave() {
        return input.isTyped(KeyEvent.VK_S);
    }

    public boolean isRequestingPause() {
        return input.isTyped(KeyEvent.VK_SPACE);
    }

    public int getMouseWheelRotation() {
        return input.getMouseWheelRotation();
    }

    public boolean getMouseClicked() {
        return input.getMouseClicked();
    }
}
