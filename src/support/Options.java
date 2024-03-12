package support;
// Globale Konstanten sind hier gespeichert
public class Options {

    // Konstanten im Kontext der Gehirne
    public static double addNeuronRate = 0.06;
    public static double addSynapseRate = 0.1;
    public static double mutateWeightsRate = 0.9;
    public static double multipleMutationsRate = 0.005;
    public static final int numberOfPossibleMutations = 4;
    public static int mutationsOnReproduction = 2;
    public static int startingSynapses = 38;
    public static int startingMutations = 10;
    public static final int amountOfPseudoInputs = 2;
    public static double tau = 30.0;
    public static double decay(){
        return Math.exp(-1.0 / tau);
    } 

    // Konstanten im Kontext der Energie
    public static double initialEnergy = 1.0;
    public static double requiredEnergy = 0.0;  
    public static double desiredEnergy = 3.0;
    //energie für körper öÄ, wäre nach dem Tod als Essen verfügbar
    //public static double organismEnergy = 0.0;
    public static double reproductionEnergy(){ 
        return desiredEnergy + initialEnergy; 
    }
    public static int deathAge = 70000;
    public static double energyConsumptionAtDeathAge = 0.1;

    // Konstanten die durch die Einstellungen im GUI geregelt werden (Default Werte hier)
    public static int amountOfOrganisms = 5;
    public static int amountOfFood = 300;
    public static double foodSpawnRate = 0.05;
    public static double speed = 1.0;

    // Technische Konstanten
    public static int fps = 30;
    public static int ups = 90;
    public static final int width = 1920;
    public static final int height = 1200;
    public static final int amountOfHorizontalCells = 16;
    public static final int amountOfVerticalCells = 10;
    public static final int cellLength = width / amountOfHorizontalCells;
}