package world;

public class World implements Thing {
    Thing[] objects;

    @Override
    public void update() {
        //alle objekte updaten, ggf notwendige informationen ermitteln vorher
            //Sicht: Strahl von Organismus aus; auf früheste Kollision überprüfen und entsprechend Inputs setzen
        //für Organismen überprüfen,
            // ob ihr Energielevel hoch genug ist um Ei zu legen -> .layEgg()
            // ob sie auf Essen sind -> .eat(energy)
        //für Eier überprüfen, ob sie schlüpfen sollen (.canHatch())
        //wenn irgendein Objekt außerhalb der Grenzen ist,
        //horizontal und vertikal spiegeln (winkel bleibt gleich)
    }

    public Thing[] getObjects() {
        return objects;
    }
}
