package gui;

import input.Input;
import main.Loop;
import support.Options;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

public class GUI{ // Graphic User Interface

    // für Swing relevante Instanzen werden erstellt
    int phase = 0;
    JFrame frame = new JFrame();
    JPanel panelTextFields = new JPanel();
    JPanel panelCharakterDesign = new JPanel();
    JPanel panelCheckboxes = new JPanel();


    JButton buttonSimulation = new JButton("Simulation starten");
    /*JButton buttonDesign11 = new JButton("");
    JButton buttonDesign12 = new JButton("");
    JButton buttonDesign13 = new JButton("");
    JButton buttonDesign21 = new JButton("");
    JButton buttonDesign22 = new JButton("");
    JButton buttonDesign23 = new JButton("");
    JButton buttonDesign31 = new JButton("");
    JButton buttonDesign32 = new JButton("");
    JButton buttonDesign33 = new JButton("");*/

    //
    JLabel speedLabel = new JLabel("Set Organism Speed:");
    JTextField speedInput = new JTextField(2);
    JLabel amountOfOrganismsLabel = new JLabel("Set Amount of Organisms:");
    JTextField amountOfOrganismsInput = new JTextField(2);
    JLabel amountOfFoodLabel = new JLabel("Set Amount of Food:");
    JTextField amountOfFoodInput = new JTextField(2);
    JLabel foodSpawnRateLabel = new JLabel("Set Food Spawn Rate:");
    JTextField foodSpawnRateInput = new JTextField(3);
    JLabel initialEnergyLabel = new JLabel("Set Initial Energy:");
    JTextField initialEnergyInput = new JTextField(2); 
    JLabel desiredEnergyLabel = new JLabel("Set Desired Energy:");
    JTextField desiredEnergyInput = new JTextField(2);
    JLabel energyConsumptionAtDeathAgeLabel = new JLabel("Set Energy Consumption:");
    JTextField energyConsumptionAtDeathAgeInput = new JTextField(3); 
    JLabel deathAgeLabel = new JLabel("Set Death Age:");
    JTextField deathAgeInput = new JTextField(4);
    JLabel addNeuronRateLabel = new JLabel("Set Neuron Mutation Rate:");
    JTextField addNeuronRateInput = new JTextField(3);
    JLabel addSynapseRateLabel = new JLabel("Set Synapse Mutation Rate:");
    JTextField addSynapseRateInput = new JTextField(3); 
    JLabel mutateWeightsRateLabel = new JLabel("Set Synapse Weight Mutation Rate:");
    JTextField mutateWeightsRateInput = new JTextField(3); 
    JLabel multipleMutationsRateLabel = new JLabel("Set Multiple Mutation Rate:");
    JTextField multipleMutationsRateInput = new JTextField(3); 
    JLabel mutationsOnReproductionLabel = new JLabel("Set Mutations on Reproduction:");
    JTextField mutationsOnReproductionInput = new JTextField(2);
    JLabel startingSynapsesLabel = new JLabel("Set Starting Synapses:");
    JTextField startingSynapsesInput = new JTextField(2);
    JLabel startingMutationsLabel = new JLabel("Set Starting Mutations:");
    JTextField startingMutationsInput = new JTextField(2);
    JLabel tauLabel = new JLabel("Set Neuron Charge Decay Constant:");
    JTextField tauInput = new JTextField(3);
    JLabel fpsLabel = new JLabel("Set FPS:");
    JTextField fpsInput = new JTextField(2);
    JLabel upsLabel = new JLabel("Set UPS:");
    JTextField upsInput = new JTextField(2);
    JLabel checkboxLabel1 = new JLabel("Activate 1:");
    JLabel checkboxLabel2 = new JLabel("Activate 2:");
    JLabel checkboxLabel3 = new JLabel("Activate 3:");
    JCheckBox checkbox1 = new JCheckBox();
    JCheckBox checkbox2 = new JCheckBox();
    JCheckBox checkbox3 = new JCheckBox();

    // Das gesamte GUI wird hier erstellt
    public GUI(){

        // Setup
        //panel.setBorder(BorderFactory.createEmptyBorder(Options.width, Options.width, Options.height, Options.width));
        panelTextFields.setBorder(BorderFactory.createEmptyBorder(25, 80, 25, 80));
        frame.setPreferredSize(new Dimension(Options.width / 2, Options.height / 3));
        //panelCharakterDesign.setLayout(new GridLayout(3, 2, 10, 10));
        //panelButton.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        panelCheckboxes.setBorder(BorderFactory.createEmptyBorder(25, 100, 25, 100));
        panelTextFields.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        frame.add(panelTextFields, BorderLayout.CENTER);
        //frame.add(panelButton, BorderLayout.SOUTH);
        frame.add(panelCheckboxes, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Einstellungen");
        frame.pack();
        //frame.setLayout(new FlowLayout());
        
        // Fügt Textfeld mit Label zu Einstellung der Geschwindigkeit hinzu
        speedInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen und Punkt können eingegeben werden, weil Speed ein double ist
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || (ke.getKeyChar() == '.') || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    speedInput.setEditable(true);
                } else {
                    speedInput.setEditable(false);
                }
            }
         });
        speedInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu double konvertiert
                Options.speed = Double.parseDouble(speedInput.getText());
            }
        });
        panelTextFields.add(speedLabel);
        speedInput.setText(Double.toString(Options.speed));
        panelTextFields.add(speedInput);

        // Fügt Textfeld mit Label zu Einstellung der Anzahl der Organismen hinzu
        amountOfOrganismsInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfOrganisms ein int ist
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    amountOfOrganismsInput.setEditable(true);
                } else {
                    amountOfOrganismsInput.setEditable(false);
                }
            }
         });
         amountOfOrganismsInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.amountOfOrganisms = Integer.parseInt(amountOfOrganismsInput.getText());
            }
        });
        panelTextFields.add(amountOfOrganismsLabel);
        amountOfOrganismsInput.setText(Integer.toString(Options.amountOfOrganisms));
        panelTextFields.add(amountOfOrganismsInput);

        // Fügt Textfeld mit Label zu Einstellung der Anzahl des Essens hinzu
        amountOfFoodInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    amountOfFoodInput.setEditable(true);
                } else {
                    amountOfFoodInput.setEditable(false);
                }
            }
         });
         amountOfFoodInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.amountOfFood = Integer.parseInt(amountOfFoodInput.getText());
            }
        });
        panelTextFields.add(amountOfFoodLabel);
        amountOfFoodInput.setText(Integer.toString(Options.amountOfFood));
        panelTextFields.add(amountOfFoodInput);
        
        foodSpawnRateInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || (ke.getKeyChar() == '.') || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    foodSpawnRateInput.setEditable(true);
                } else {
                    foodSpawnRateInput.setEditable(false);
                }
            }
         });
         foodSpawnRateInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.foodSpawnRate = Double.parseDouble(foodSpawnRateInput.getText());
            }
        });
        panelTextFields.add(foodSpawnRateLabel);
        foodSpawnRateInput.setText(Double.toString(Options.foodSpawnRate));
        panelTextFields.add(foodSpawnRateInput);
        
        initialEnergyInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || (ke.getKeyChar() == '.') || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    initialEnergyInput.setEditable(true);
                } else {
                    initialEnergyInput.setEditable(false);
                }
            }
         });
         initialEnergyInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.initialEnergy = Double.parseDouble(initialEnergyInput.getText());
            }
        });
        panelTextFields.add(initialEnergyLabel);
        initialEnergyInput.setText(Double.toString(Options.initialEnergy));
        panelTextFields.add(initialEnergyInput);

        desiredEnergyInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || (ke.getKeyChar() == '.') || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    desiredEnergyInput.setEditable(true);
                } else {
                    desiredEnergyInput.setEditable(false);
                }
            }
         });
         desiredEnergyInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.desiredEnergy = Double.parseDouble(desiredEnergyInput.getText());
            }
        });
        panelTextFields.add(desiredEnergyLabel);
        desiredEnergyInput.setText(Double.toString(Options.desiredEnergy));
        panelTextFields.add(desiredEnergyInput);

        energyConsumptionAtDeathAgeInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || (ke.getKeyChar() == '.') || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    energyConsumptionAtDeathAgeInput.setEditable(true);
                } else {
                    energyConsumptionAtDeathAgeInput.setEditable(false);
                }
            }
         });
         energyConsumptionAtDeathAgeInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.energyConsumptionAtDeathAge = Double.parseDouble(energyConsumptionAtDeathAgeInput.getText());
            }
        });
        panelTextFields.add(energyConsumptionAtDeathAgeLabel);
        energyConsumptionAtDeathAgeInput.setText(Double.toString(Options.energyConsumptionAtDeathAge));
        panelTextFields.add(energyConsumptionAtDeathAgeInput);

        deathAgeInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    deathAgeInput.setEditable(true);
                } else {
                    deathAgeInput.setEditable(false);
                }
            }
         });
         deathAgeInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.deathAge = Integer.parseInt(deathAgeInput.getText());
            }
        });
        panelTextFields.add(deathAgeLabel);
        deathAgeInput.setText(Integer.toString(Options.deathAge));
        panelTextFields.add(deathAgeInput);

        addNeuronRateInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || (ke.getKeyChar() == '.') || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    addNeuronRateInput.setEditable(true);
                } else {
                    addNeuronRateInput.setEditable(false);
                }
            }
         });
         addNeuronRateInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.addNeuronRate = Double.parseDouble(addNeuronRateInput.getText());
            }
        });
        panelTextFields.add(addNeuronRateLabel);
        addNeuronRateInput.setText(Double.toString(Options.addNeuronRate));
        panelTextFields.add(addNeuronRateInput);

        addSynapseRateInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || (ke.getKeyChar() == '.') || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    addSynapseRateInput.setEditable(true);
                } else {
                    addSynapseRateInput.setEditable(false);
                }
            }
         });
         addSynapseRateInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.addSynapseRate = Double.parseDouble(addSynapseRateInput.getText());
            }
        });
        panelTextFields.add(addSynapseRateLabel);
        addSynapseRateInput.setText(Double.toString(Options.addSynapseRate));
        panelTextFields.add(addSynapseRateInput);

        mutateWeightsRateInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || (ke.getKeyChar() == '.') || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    mutateWeightsRateInput.setEditable(true);
                } else {
                    mutateWeightsRateInput.setEditable(false);
                }
            }
         });
         mutateWeightsRateInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.mutateWeightsRate = Double.parseDouble(mutateWeightsRateInput.getText());
            }
        });
        panelTextFields.add(mutateWeightsRateLabel);
        mutateWeightsRateInput.setText(Double.toString(Options.mutateWeightsRate));
        panelTextFields.add(mutateWeightsRateInput);

        multipleMutationsRateInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || (ke.getKeyChar() == '.') || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    multipleMutationsRateInput.setEditable(true);
                } else {
                    multipleMutationsRateInput.setEditable(false);
                }
            }
         });
         multipleMutationsRateInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.multipleMutationsRate = Double.parseDouble(multipleMutationsRateInput.getText());
            }
        });
        panelTextFields.add(multipleMutationsRateLabel);
        multipleMutationsRateInput.setText(Double.toString(Options.multipleMutationsRate));
        panelTextFields.add(multipleMutationsRateInput);

        mutationsOnReproductionInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    mutationsOnReproductionInput.setEditable(true);
                } else {
                    mutationsOnReproductionInput.setEditable(false);
                }
            }
         });
         mutationsOnReproductionInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.mutationsOnReproduction = Integer.parseInt(mutationsOnReproductionInput.getText());
            }
        });
        panelTextFields.add(mutationsOnReproductionLabel);
        mutationsOnReproductionInput.setText(Integer.toString(Options.mutationsOnReproduction));
        panelTextFields.add(mutationsOnReproductionInput);

        startingSynapsesInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    startingSynapsesInput.setEditable(true);
                } else {
                    startingSynapsesInput.setEditable(false);
                }
            }
         });
         startingSynapsesInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.startingSynapses = Integer.parseInt(startingSynapsesInput.getText());
            }
        });
        panelTextFields.add(startingSynapsesLabel);
        startingSynapsesInput.setText(Integer.toString(Options.startingSynapses));
        panelTextFields.add(startingSynapsesInput);

        startingMutationsInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    startingMutationsInput.setEditable(true);
                } else {
                    startingMutationsInput.setEditable(false);
                }
            }
         });
         startingMutationsInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.startingMutations = Integer.parseInt(startingMutationsInput.getText());
            }
        });
        panelTextFields.add(startingMutationsLabel);
        startingMutationsInput.setText(Integer.toString(Options.startingMutations));
        panelTextFields.add(startingMutationsInput);

        tauInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen und Punkt können eingegeben werden, weil Speed ein double ist
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || (ke.getKeyChar() == '.') || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    tauInput.setEditable(true);
                } else {
                    tauInput.setEditable(false);
                }
            }
         });
         tauInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu double konvertiert
                Options.speed = Double.parseDouble(tauInput.getText());
            }
        });
        panelTextFields.add(tauLabel);
        tauInput.setText(Double.toString(Options.tau));
        panelTextFields.add(tauInput);

        fpsInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    fpsInput.setEditable(true);
                } else {
                    fpsInput.setEditable(false);
                }
            }
         });
         fpsInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.fps = Integer.parseInt(fpsInput.getText());
            }
        });
        panelTextFields.add(fpsLabel);
        fpsInput.setText(Integer.toString(Options.fps));
        panelTextFields.add(fpsInput);

        upsInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    upsInput.setEditable(true);
                } else {
                    upsInput.setEditable(false);
                }
            }
         });
         upsInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.ups = Integer.parseInt(upsInput.getText());
            }
        });
        panelTextFields.add(upsLabel);
        upsInput.setText(Integer.toString(Options.ups));
        panelTextFields.add(upsInput);


        checkbox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // Aktion einfügen
            }
        });
        panelCheckboxes.add(checkboxLabel1);
        panelCheckboxes.add(checkbox1);

        checkbox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // Aktion einfügen
            }
        });
        panelCheckboxes.add(checkboxLabel2);
        panelCheckboxes.add(checkbox2);

        checkbox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // Aktion einfügen
            }
        });
        panelCheckboxes.add(checkboxLabel3);
        panelCheckboxes.add(checkbox3);
        

        // Ein Button zum Start der Simulation wird hinzugefügt
        buttonSimulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                phase++;
                // Start der Simulation
                if(phase == 1){
                    frame.dispose();
                    new Thread(new Loop(new Input())).start();
                }
            }
        });
        buttonSimulation.setBounds(50, 50, 100, 50);
        frame.add(buttonSimulation, BorderLayout.SOUTH);

        /*for(int i = 0; i < 9; i++){
            JButton b = new JButton("");
            b.addActionListener(null);
            panelCharakterDesign.add(b);
        }*/

        frame.setVisible(true);
    }

}
