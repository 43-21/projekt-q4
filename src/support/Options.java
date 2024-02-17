package support;
public class Options {
    public static double addNeuronRate = 0.03;
    public static double addSynapseRate = 0.05;
    public static double mutateWeightsRate = 0.9;
    public static double multipleMutationsRate = 0.005;
    public static int numberOfPossibleMutations = 4;
    public static int mutationsOnReproduction = 2;
    public static int startingSynapses = 12;
    public static double tau = 10.0;
    public static double decay = Math.exp(-1.0 / tau);

    public static double speed = 1.0;

    public static double initialEnergy = 0.2;
    public static double requiredEnergy = 0.0;
    public static double desiredEnergy = 1.0;
    //energie für körper öÄ, wäre nach dem Tod als Essen verfügbar
    public static double organismEnergy = 0.2;
    public static double reproductionEnergy = desiredEnergy + initialEnergy + organismEnergy;
    public static int deathAge = 70000;
    public static double energyConsumptionAtDeathAge = 0.1;

    public static int fps = 60;
    public static int ups = 90;

    public static int width = 1920;
    public static int height = 1200;
}