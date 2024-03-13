package support;

// Globale Konstanten sind hier gespeichert
public class Options {

    // Konstanten im Kontext der Gehirne
    public static double addNeuronRate = 0.06;
    public static double addSynapseRate = 0.1;
    public static double mutateWeightsRate = 0.9;
    public static double multipleMutationsRate = 0.005;
    public static int numberOfPossibleMutations = 4;
    public static int mutationsOnReproduction = 2;
    public static int startingSynapses = 48;
    public static int amountOfPseudoInputs = 2;
    public static double tau = 30.0;
    public static double decay = Math.exp(-1.0 / tau);

    // Konstanten im Kontext der Energie
    public static double initialEnergy = 1.0;
    public static double requiredEnergy = 0.0;
    public static double desiredEnergy = 3.0;
    public static double organismEnergy = 0.0;
    public static double reproductionEnergy = desiredEnergy + initialEnergy + organismEnergy;
    public static int deathAge = 70000;
    public static double energyConsumptionAtDeathAge = 0.07;
    public static double energyInFood = 2.0;

    // Konstanten für Organismen und andere Objekte
    public static double viewRange = 250.0;
    public static double communicationRadius = 75.0;
    public static int eggScale = 6;
    public static int organismScale = 10;
    public static int foodScale = 3;

    // Konstanten die durch die Einstellungen im GUI geregelt werden (Default Werte
    // hier)
    public static int amountOfOrganisms = 10;
    public static int amountOfFood = 50;
    public static double speed = 1.0;

    // Technische Konstanten
    public static int fps = 30;
    public static int ups = 90;
    public static int width = 1920;
    public static int height = 1200;
    public static int amountOfHorizontalCells = 16;
    public static int amountOfVerticalCells = 10;
    public static int cellLength = width / amountOfHorizontalCells;
}