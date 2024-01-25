package organism;

import java.util.HashMap;
import java.util.HashSet;

public class Brain {
    HashMap<Integer, Neuron> neurons;
    HashSet<Synapse> synapses;
    //wär besser wenn die in der main loop berechnet würde, damit man nur einmal rechnet
    //e^(-dt / tau)
    //mit tau sollten wir noch rumspielen
    final float decay = (float) Math.exp(-1.0 / 150.0);

    public Brain(HashMap<Integer, Neuron> n, HashSet<Synapse> s) {
        neurons = n;
        synapses = s;
    }


    public void update() {
        //output: 1 wenn schwelle überschritten, sonst 0
            //idee: auch outputs mit "muskeln" verbinden,
                //d.h. die stärke der aktion könnte durch lernen verändert werden,
                //wär aber immernoch konstant

        //feuerzeiten müssten ggf gespeichert werden für die synaptische plastizität

        for(Synapse s : synapses) {
            if(neurons.get(s.from).spike) {
                neurons.get(s.to).potential += s.weight;
            }
        }

        for(Neuron n : neurons.values()) {
            n.spike = false;

            if(n.potential >= n.threshold) {
                n.potential = .0f;
                n.spike = true;
            }
            
            n.potential = n.potential * decay;
        }
    }
}

class Neuron {
    public boolean spike = false;
    public float potential = .0f;
    public float threshold;
    public int index;

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