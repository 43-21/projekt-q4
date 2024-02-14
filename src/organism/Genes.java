package organism;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import support.Options;

//Gene fürs Gehirn und den Körper des Organismus
public class Genes {
    private HashMap<Integer, NeuronGene> neuronGenes;
    private HashSet<Synapse> synapseGenes;

    public Genes() {
        //unverbundenes neuronales netz machen (also nur inputs und outputs)
        neuronGenes = new HashMap<>();
        synapseGenes = new HashSet<>();
        for(int i = 0; i < 14; i++){
            neuronGenes.put(i, new NeuronGene(1, i));
        }
    }

    // public Genes recombine(Genes partner) {
    //     return new Genes();
    // }

    //neue Gene aus diesem durch Mutation
    public Genes(Genes oldGenes) {
        neuronGenes = oldGenes.neuronGenes;
        synapseGenes = oldGenes.synapseGenes;
        for(int i = 0; i < Options.mutationsOnReproduction; i++){
            mutate();
        }
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
        if(ran < Options.multipleMutationsRate) {
            mutate();
        }
    }

    private void addNeuron() {
        //synapse aussuchen, gewichtung auf 0 setzen
        //an deren stelle zwei neue machen ins und aus dem neuen neuron
        //gewichtungen: 1 und so wie die alte
        if(!(synapseGenes.isEmpty())) {
            int rand = (int) Math.floor(Math.random()*synapseGenes.size());
            int size = neuronGenes.size();
            NeuronGene n = new NeuronGene(1, size);
            ArrayList<Synapse> list = new ArrayList<Synapse>(synapseGenes);
            Synapse tempS = list.get(rand);
            int temp = tempS.to;
            synapseGenes.remove(tempS);
            tempS.to = size;
            synapseGenes.add(tempS);
            neuronGenes.put(neuronGenes.size(), n);
            synapseGenes.add(new Synapse(size, temp, 1));
        }
    }

    private void addSynapse() {
        //zwei unverbundene Neuronen aussuchen und Synapse mit zufälliger Gewichtung hinzufügen
        int from = (int) Math.floor(Math.random()*neuronGenes.size());
        int to = (int) Math.floor(Math.random()*neuronGenes.size());
        while(from == to){
            to = (int) Math.floor(Math.random()*neuronGenes.size());
        }
        synapseGenes.add(new Synapse(from, to, 1));
    }

    private void mutateWeights() {
        //durch alle synapsen durchgehen und gewichtungen ein bisschen verändern
        double sigma = 0.2;
        Random random = new Random();
        double mutation = (sigma * random.nextGaussian());
        mutation = Math.min(Math.max(mutation, -1), 1);
        int rand = (int) Math.floor(random.nextDouble()*synapseGenes.size());
        ArrayList<Synapse> list = new ArrayList<Synapse>(synapseGenes);
        Synapse tempS = list.get(rand);
        double temp = tempS.weight + mutation;
        synapseGenes.remove(tempS);
        tempS.weight = temp;
        synapseGenes.add(tempS);
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