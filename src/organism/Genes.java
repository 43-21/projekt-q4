package organism;

import java.util.HashMap;
import java.util.HashSet;

import support.Options;

//Gene fürs Gehirn und den Körper des Organismus
public class Genes {
    private HashMap<Integer, NeuronGene> neuronGenes;
    private HashSet<Synapse> synapseGenes;

    public Genes() {
        //unverbundenes neuronales netz machen (also nur inputs und outputs)
        neuronGenes = new HashMap<>();
        synapseGenes = new HashSet<>();
    }

    // public Genes recombine(Genes partner) {
    //     return new Genes();
    // }

    //neue Gene aus diesem durch Mutation
    public Genes(Genes oldGenes) {

    }

    public Brain brain() {
        HashMap<Integer, Neuron> neurons = new HashMap<>();
        HashSet<Synapse> synapses = synapseGenes;

        for(NeuronGene gene : neuronGenes.values()) {
            Neuron neuron = new Neuron(gene.threshold, gene.index);
            neurons.put(neuron.index, neuron);
        }

        //sind die synapsen eine kopie (gut) oder eine referenz (schlecht)?
        return new Brain(6, neurons, synapses);
    }

    public void mutate() {
        //sollte zu nem switch case gemacht werden
        double ran = Math.random();
        if(ran < Options.addNeuronRate) {
            addNeuron();
        }

        if(ran < Options.addSynapseRate) {
            addSynapse();
        }

        if(ran < Options.mutateWeightsRate) {
            mutateWeights();
        }
    }

    private void addNeuron() {
        //synapse aussuchen, gewichtung auf 0 setzen
        //an deren stelle zwei neue machen ins und aus dem neuen neuron
        //gewichtungen: 1 und so wie die alte
    }

    private void addSynapse() {
        //zwei unverbundene Neuronen aussuchen und Synapse mit zufälliger Gewichtung hinzufügen
    }

    private void mutateWeights() {
        //durch alle synapsen durchgehen und gewichtungen ein bisschen verändern
    }

    // private void mutateNeuronThresholds() {

    // }

    // private void mutateSynapseWeightBounds() {

    // }
}

class NeuronGene {
    public double threshold;
    public int index;

    public NeuronGene(double threshold, int index) {
        this.threshold = threshold;
        this.index = index;
    }
}