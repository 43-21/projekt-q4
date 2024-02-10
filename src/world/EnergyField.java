package world;

//aka Busch
public class EnergyField extends Positioned {
    boolean filled;
    int cooldown;

    public EnergyField(boolean startValue, int cooldown) {
        this.filled = startValue;
        this.cooldown = cooldown;
    }

    public void update() {  //irgendeine Form einer update methode ist nötig um Food neu spawnen zu lassen nach Ablauf des Cooldowns

    }
}
