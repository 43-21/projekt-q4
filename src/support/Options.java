package support;

/**
 * Speichert die globalen Konstanten.
 */
public class Options {
    // Konstanten im Kontext der Gehirne
    public static double mutateWeightsStdDev = 0.1;
    public static double addNeuronRate = 0.06;
    public static double addSynapseRate = 0.1;
    public static double mutateWeightsRate = 0.9;
    public static double multipleMutationsRate = 0.005;
    public static int mutationsOnReproduction = 2;
    public static int startingSynapses = 48;
    public static int startingMutations = 0;
    public static int amountOfPseudoInputs = 2;
    public static double tau = 30.0;
    public static double decay(){
        return Math.exp(-1.0 / tau);
    } 

    // Konstanten im Kontext der Energie
    public static double initialEnergy = 1.0;
    public static double requiredEnergy = 0.0;
    public static double desiredEnergy = 2.5;
    //energie für körper öÄ, wäre nach dem Tod als Essen verfügbar
    //public static double organismEnergy = 0.0;
    public static double reproductionEnergy(){ 
        return desiredEnergy + initialEnergy; 
    }
    public static int deathAge = 30000;
    public static double energyConsumptionAtDeathAge = 0.005;
    public static double energyInFood = 2.5;

    // Konstanten für Organismen und andere Objekte
    public static double viewRange = 250.0;
    public static double communicationRadius = 75.0;
    public static int eggScale = 6;
    public static int organismScale = 10;
    public static int foodScale = 3;

    // Konstanten die durch die Einstellungen im GUI geregelt werden (Default Werte
    // hier)
    public static int amountOfOrganisms = 10;
    public static int amountOfFood = 80;
    public static double foodSpawnRate = 0.00006;
    public static double speed = 1.0;

    // Technische Konstanten
    public static int fps = 30;
    public static int ups = 90;
    public static final int width = 1920;
    public static final int height = 1200;
    public static final int amountOfHorizontalCells = 16;
    public static final int amountOfVerticalCells = 10;
    public static final int cellLength = width / amountOfHorizontalCells;


    //Overlay
    public static boolean showViewRange = true;
    public static boolean showView = true;
    public static boolean showCommunication = true;
    public static boolean showSensesOnlyOnFocus = true;
    
    public static boolean showPossiblyInView = true;

    public static boolean showLogs = false;
    public static boolean showInformationOnFocus = true;

    public static boolean showWorldInformation = true;
    public static boolean updateRate = true;
}