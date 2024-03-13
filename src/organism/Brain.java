package organism;

import java.util.HashMap;
import java.util.HashSet;

import support.Options;

/**
 * Das Gehirn eines Organismus in Form eines Spiking Neural Network (SNN).
 */
public class Brain {
    HashMap<Integer, Neuron> neurons;
    HashSet<Synapse> synapses;
    final double decay = Options.decay();

    int inputSize;
    int outputSize;
    boolean[] inputs;
    boolean[] outputs;

    /**
     * Erstellt das Objekt des Gehirns.
     * @param inputSize die Anzahl an Inputs (exklusive Pseudo-Inputs, die immer an sind und in den Optionen eingestellt werden)
     * @param outputSize die Anzahl an outputs
     * @param n Die Neuronen des Gehirns
     * @param s Die Synapsen des Gehirns
     */
    public Brain(int inputSize, int outputSize, HashMap<Integer, Neuron> n, HashSet<Synapse> s) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        neurons = n;
        synapses = s;

        this.outputs = new boolean[outputSize];
    }

    /**
     * Bringt das Gehirn auf den aktuellen Stand; liest Inputs aus und setzt die Outputs.
     */
    public void update() {
        // inputs
        for (int i = 0; i < inputSize; i++) {
            neurons.get(i).spike = inputs[i];
        }

        for (int i = inputSize; i < inputSize + Options.amountOfPseudoInputs; i++) {
            neurons.get(i).spike = true;
        }

        // spiking
        for (Synapse s : synapses) {
            Neuron from = neurons.get(s.from);
            Neuron to = neurons.get(s.to);

            if (from.spike) {
                to.potential += s.weight;
            }
        }

        // outputs & spiking
        outputs = new boolean[outputSize];

        for (Neuron n : neurons.values()) {
            n.spike = false;

            if (n.potential >= n.threshold) {
                n.potential -= n.threshold;
                n.spike = true;
            }

            if (n.potential < 0) {
                n.potential = 0;
            }

            n.potential = n.potential * decay;

            if (n.index >= inputSize + Options.amountOfPseudoInputs && n.index < inputSize + Options.amountOfPseudoInputs + outputSize) {
                outputs[n.index - inputSize - Options.amountOfPseudoInputs] = n.spike;
            }
        }
    }

    public void setInputs(boolean[] inputs) {
        this.inputs = inputs;
    }

    public boolean[] getOutputs() {
        return outputs;
    }
}

/**
 * Das Neuron, das das aktuelle Aktionspotenzial speichert und ob es spiket.
 */
class Neuron {
    public double threshold;
    public int index;

    public boolean spike = false;
    public double potential = .0f;

    /**
     * Erstellt das Neuron mit einer Schwelle und einem Index.
     * @param t die Schwelle, nach der das Neuron spiket
     * @param i der Index
     */
    public Neuron(double t, int i) {
        threshold = t;
        index = i;
    }
}

/**
 * Die Synapse zwischen zwei Neuronen, die eine Gewichtung hat.
 */
class Synapse {
    public int from;
    public int to;
    public double weight;

    /**
     * Erstellt eine Synapse zwischen zwei Neuronen mit einer bestimmten Gewichtung.
     * @param f das Neuron, aus dem die Synapse kommt
     * @param t das Neuron, zu dem die Synapse führt
     * @param w die Gewichtung
     */
    public Synapse(int f, int t, double w) {
        from = f;
        to = t;
        weight = w;
    }

    /**
     * Kopiert eine Synapse
     * @param s die zu kopierende Synapse
     */
    public Synapse(Synapse s) {
        from = s.from;
        to = s.to;
        weight = s.weight;
    }
}