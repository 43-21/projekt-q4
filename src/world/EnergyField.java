package world;

//aka Busch
public class EnergyField extends Positioned {
    int desiredAmountOfFood;
    int width;
    int height;

    public EnergyField(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void update() {  //irgendeine Form einer update methode ist nötig um Food neu spawnen zu lassen nach Ablauf des Cooldowns

    }
}
