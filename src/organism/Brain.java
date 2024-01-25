package organism;

import java.util.HashMap;
import java.util.HashSet;

public class Brain {
    HashMap<Integer, Neuron> neurons;
    HashSet<Synapse> synapses;
    //wär besser wenn die in der main loop berechnet würde, damit man nur einmal rechnet
    //e^(-dt / tau)
    //mit tau sollten wir noch rumspielen
    final float decay = (float) Math.exp(-1.0 / 50.0);

    int t = 0;

    public Brain(HashMap<Integer, Neuron> n, HashSet<Synapse> s) {
        neurons = n;
        synapses = s;
    }


    //sollte eigtl array oÄ mit binären outputs zurückgeben
    public void update() {
        //output: 1 wenn schwelle überschritten, sonst 0
            //idee: auch outputs mit "muskeln" verbinden,
                //d.h. die stärke der aktion könnte durch lernen verändert werden,
                //wär aber immernoch konstant

        //feuerzeiten müssten ggf gespeichert werden für die synaptische plastizität

        for(Synapse s : synapses) {
            Neuron from = neurons.get(s.from);
            Neuron to = neurons.get(s.to);
            if(from.spike) {
                to.potential += s.weight;

                //stdp presynaptic spike
                from.preChange += 0.1f;
                s.weight += from.postChange;
            }

            //stdp postsynaptic spike
            if(to.spike) {
                to.postChange -= 0.1f;
                s.weight += to.preChange;
            }

            if(s.weight > 1.0) {
                s.weight = 1.0f;
            }

            else if(s.weight < 0.0) {
                s.weight = 0.0f;
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