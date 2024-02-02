package support;
public class Options {
    public static double addNeuronRate = 0.03;
    public static double addSynapseRate = 0.05;
    public static double mutateWeightsRate = 0.9;
    public static double tau = 10.0;
    public static float decay = (float) Math.exp(-1.0 / tau);

    public static double speed = 1.0;

    public static float initialEnergy = 0.1f;
    public static float requiredEnergy = 0.0f;
    public static float desiredEnergy = 1.0f;
    //energie für körper öÄ, wäre nach dem Tod als Essen verfügbar
    public static float organismEnergy = 0.2f;
    public static float reproductionEnergy = desiredEnergy + initialEnergy + organismEnergy;
    public static int deathAge = 20000;
    public static float energyConsumptionAtDeathAge = 0.2f;

    public static int fps = 30;
    public static int ups = 90;
}