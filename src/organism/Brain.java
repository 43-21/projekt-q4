package organism;

import java.util.HashMap;
import java.util.HashSet;

import options.Options;
import world.Thing;

public class Brain implements Thing {
    HashMap<Integer, Neuron> neurons;
    HashSet<Synapse> synapses;
    final float decay = Options.decay;
    final float decaySpikeTrace = (float) Math.exp(-1.0 / 100.0);

    int inputSize;
    int outputSize;
    boolean[] inputs;
    boolean[] outputs;

    public Brain(HashMap<Integer, Neuron> n, HashSet<Synapse> s) {
        neurons = n;
        synapses = s;
    }

    @Override
    public void update() {
        //inputs
        for(int i = 0; i < inputSize; i++) {
            neurons.get(i).spike = inputs[i];
        }

        //spiking
        for(Synapse s : synapses) {
            Neuron from = neurons.get(s.from);
            Neuron to = neurons.get(s.to);
            if(from.spike) {
                to.potential += s.weight;

                //stdp presynaptic spike
                from.preChange += 0.1f;
                s.weight += to.postChange;
            }

            else from.preChange *= decaySpikeTrace;

            //stdp postsynaptic spike
            if(to.spike) {
                to.postChange -= 0.1f;
                s.weight += from.preChange;
            }

            else to.postChange *= decaySpikeTrace;

            if(s.weight > 1.0) {
                s.weight = 1.0f;
            }

            else if(s.weight < 0.0) {
                s.weight = 0.0f;
            }
        }


        //outputs & spiking
        outputs = new boolean[outputSize];

        for(Neuron n : neurons.values()) {
            n.spike = false;

            if(n.potential >= n.threshold) {
                n.potential = .0f;
                n.spike = true;
            }
            
            n.potential = n.potential * decay;

            if(n.index >= inputSize && n.index < inputSize + outputSize) {
                outputs[n.index - inputSize] = n.spike;
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

class Neuron {
    public float threshold;
    public int index;

    public boolean spike = false;
    public float potential = .0f;
    public float preChange = .0f;
    public float postChange = .0f;

    public Neuron(float t, int i) {
        threshold = t;
        index = i;
    }
}

class Synapse {
    public int from;
    public int to;
    public float weight;

    public Synapse(int f, int t, float w) {
        from = f;
        to = t;
        weight = w;
    }
}