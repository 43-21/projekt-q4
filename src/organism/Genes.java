package organism;

import java.util.HashMap;
import java.util.HashSet;

//Gene fürs Gehirn und den Körper des Organismus
public class Genes {
    private int colorA;
    private int colorB;

    private HashMap<Integer, NeuronGene> neuronGenes;
    private HashSet<Synapse> synapseGenes;

    public Genes() {
        
    }

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

        //sind die synapsen eine kopie (gut) oder eine referenz (schlecht)?
        return new Brain(neurons, synapses);
    }

    public void mutate() {
        //zufall muss noch reingebracht werden
        double ran = Math.random();
        if(ran < 0.03) {
            addNeuron();
        }

        if(ran < 0.05) {
            addSynapse();
        }

        if(ran < 0.9) {
            mutateChangeWeights();
        }

        if(ran < 0.5) {
            mutateNeuronThresholds();
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

    private void mutateChangeWeights() {
        //durch alle synapsen durchgehen und gewichtungen ein bisschen verändern
    }

    // private void mutateNeuronThresholds() {

    // }

    // private void mutateSynapseWeightBounds() {

    // }

    public int getColorA() {
        return colorA;
    }

    public int getColorB() {
        return colorB;
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