package organism;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//Gene fürs Gehirn und den Körper des Organismus
public class Genes {
    private int colorA;
    private int colorB;

    private HashMap<Integer, NeuronGene> neuronGenes;
    private HashSet<Synapse> synapseGenes;

    public Genes recombine(Genes partner) {
        return new Genes();
    }

    public Brain brain() {
        HashMap<Integer, Neuron> neurons = new HashMap<>();
        HashSet<Synapse> synapses = synapseGenes;

        for(NeuronGene gene : neuronGenes.values()) {
            Neuron neuron = new Neuron(gene.threshold, gene.index);
            neurons.put(neuron.index, neuron);
        }

        return new Brain(neurons, synapses);
    }

    public void mutate() {

    }

    private void addNeuron() {

    }

    private void addConnection() {

    }

    private void mutateChangeWeights() {

    }
}

class NeuronGene {
    public float threshold;
    public int index;

    public NeuronGene(float threshold, int index) {
        this.threshold = threshold;
        this.index = index;
    }
}