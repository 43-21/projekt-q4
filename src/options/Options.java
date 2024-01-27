package options;
public class Options {
    public static double addNeuronRate = 0.03;
    public static double addSynapseRate = 0.05;
    public static double mutateWeightsRate = 0.9;
    public static double tau = 50.0;
    public static float decay = (float) Math.exp(-1.0 / tau);
}