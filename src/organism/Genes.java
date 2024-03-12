package organism;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

import support.Options;

//Gene fürs Gehirn und den Körper des Organismus
public class Genes {
    private HashMap<Integer, NeuronGene> neuronGenes;
    private HashSet<Synapse> synapseGenes;

    int inputSize;
    int outputSize;

    public Genes(int inputSize, int outputSize) {
        //unverbundenes neuronales netz machen (also nur inputs und outputs)
        this.inputSize = inputSize;
        this.outputSize = outputSize;

        neuronGenes = new HashMap<>();
        synapseGenes = new HashSet<>();

        for(int i = 0; i < inputSize + Options.amountOfPseudoInputs + outputSize; i++){
            neuronGenes.put(i, new NeuronGene(1.0, i));
        }
        for(int i = 0; i < Options.startingSynapses; i++){
            addSynapse();
        }
    }

    //copy
    public Genes(Genes genes) {
        this.inputSize = genes.inputSize;
        this.outputSize = genes.outputSize;
        this.neuronGenes = new HashMap<>(genes.neuronGenes);
        this.synapseGenes = new HashSet<>();

        for(Synapse s : genes.synapseGenes) {
            synapseGenes.add(new Synapse(s));
        }
    }

    // public Genes recombine(Genes partner) {
    //     return new Genes();
    // }

    public Brain brain() {
        HashMap<Integer, Neuron> neurons = new HashMap<>();
        HashSet<Synapse> synapses = synapseGenes;

        for(NeuronGene gene : neuronGenes.values()) {
            Neuron neuron = new Neuron(gene.threshold, gene.index);
            neurons.put(neuron.index, neuron);
        }

        return new Brain(inputSize, outputSize, neurons, synapses);
    }

    public void mutate() {
        //sollte zu nem switch case gemacht werden
        double[] ran = new double[Options.numberOfPossibleMutations];
        for(int i = 0; i < Options.numberOfPossibleMutations; i++){
            ran[i] = ThreadLocalRandom.current().nextDouble();
        }
        if(ran[0] < Options.addNeuronRate) {
            addNeuron();
        }

        if(ran[1] < Options.addSynapseRate) {
            addSynapse();
        }

        if(ran[2] < Options.mutateWeightsRate) {
            mutateWeights();
        }
        if(ran[3] < Options.multipleMutationsRate) {
            mutate();
        }
    }

    private void addNeuron() {
        //synapse löschen
        //an deren stelle zwei neue machen ins und aus dem neuen neuron
        //gewichtungen: 1 und so wie die alte
        if(synapseGenes.isEmpty()) return;

        int synapseIndex = ThreadLocalRandom.current().nextInt(synapseGenes.size());
        int i = 0;
        int oldFrom = 0;
        int oldTo = inputSize;
        double weight = 0.0;
        for(Synapse s : synapseGenes) {
            if(i != synapseIndex) {
                i++;
                continue;
            }
            oldFrom = s.from;
            oldTo = s.to;
            weight = s.weight;
            synapseGenes.remove(s);
            break;
        }

        int neuronIndex = neuronGenes.size();
        NeuronGene newNeuronGene = new NeuronGene(1.0, neuronIndex);

        synapseGenes.add(new Synapse(oldFrom, neuronIndex, weight));
        synapseGenes.add(new Synapse(neuronIndex, oldTo, 1.0));
        neuronGenes.put(neuronIndex, newNeuronGene);
        System.out.println("Added a new neuron gene: index " + neuronIndex);
        System.out.println("Neurongene: " + neuronGenes.get(neuronIndex));
    }

    private void addSynapse() {
        //zwei unverbundene Neuronen aussuchen und Synapse mit zufälliger Gewichtung hinzufügen
        ArrayList<int[]> unconnectedNeurons = new ArrayList<>();
        for(int from : neuronGenes.keySet()) {
            if(from >= inputSize + Options.amountOfPseudoInputs && from < inputSize + Options.amountOfPseudoInputs + outputSize) continue; //keine synapsen von output neuronen
            loop:
            for(int to : neuronGenes.keySet()) {
                if(to < inputSize + Options.amountOfPseudoInputs) continue; //keine synapsen zu input neuronen
                if(from == to) continue;
                for(Synapse synapse : synapseGenes) {
                    if(synapse.from == from && synapse.to == to) continue loop;
                }
                unconnectedNeurons.add(new int[] {from, to});
            }
        }

        if(unconnectedNeurons.size() == 0) return;

        int[] pair = unconnectedNeurons.get((int) Math.floor(Math.random() * unconnectedNeurons.size()));
        double weight = Math.random() * 2.0 - 1.0;
        synapseGenes.add(new Synapse(pair[0], pair[1], weight));

        // int from = (int) Math.floor(Math.random()*neuronGenes.size());
        // int to = (int) Math.floor(Math.random()*neuronGenes.size());
        // while(from == to){
        //     to = (int) Math.floor(Math.random()*neuronGenes.size());
        // }
        // synapseGenes.add(new Synapse(from, to, 1));
    }

    //durch alle synapsen durchgehen und gewichtungen ein bisschen verändern
    //wenn performance probleme, eventuel einfach uniform statt normalverteilt
    private void mutateWeights() {
        double standardDeviation = 0.2;
        for(Synapse synapse : synapseGenes) {
            double mutation = ThreadLocalRandom.current().nextGaussian()*standardDeviation;
            mutation = Math.min(Math.max(mutation, -1.0), 1.0);
            synapse.weight += mutation;
        }

        // double sigma = 0.2;
        // Random random = new Random();
        // float mutation = (float) (sigma * random.nextGaussian());
        // mutation = Math.min(Math.max(mutation, -1), 1);
        // int rand = (int) Math.floor(random.nextDouble()*synapseGenes.size());
        // ArrayList<Synapse> list = new ArrayList<Synapse>(synapseGenes);
        // Synapse tempS = list.get(rand);
        // float temp = tempS.weight + mutation;
        // synapseGenes.remove(tempS);
        // tempS.weight = temp;
        // synapseGenes.add(tempS);
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