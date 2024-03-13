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
    JLabel viewRangeLabel = new JLabel("Set View Range:"); 
    JTextField viewRangeInput = new JTextField(3); 
    JLabel communicationRadiusLabel = new JLabel("Set Communication Radius:");
    JTextField communicationRadiusInput = new JTextField(3); 
    JLabel eggScaleLabel = new JLabel("Set Egg Scale:");
    JTextField eggScaleInput = new JTextField(2);
    JLabel organismScaleLabel = new JLabel("Set Organism Scale:");
    JTextField organismScaleInput = new JTextField(2); 
    JLabel foodScaleLabel = new JLabel("Set Food Scale:");
    JTextField foodScaleInput = new JTextField(2);
    JLabel showAllPossiblyInViewLabel = new JLabel("Show all possibly visible Objects");
    JCheckBox showAllPossiblyInViewCheckbox = new JCheckBox();
    JLabel showPossiblyInViewLabel = new JLabel("Show possibly visible Objects");
    JCheckBox showPossiblyInViewCheckbox = new JCheckBox();
    JLabel showViewRangeLabel = new JLabel("Show View Range");
    JCheckBox showViewRangeCheckbox = new JCheckBox(); 
    JLabel showViewLabel = new JLabel("Show View");
    JCheckBox showViewCheckbox = new JCheckBox();
    JLabel showCommunicationLabel = new JLabel("Show Communication");
    JCheckBox showCommunicationCheckbox = new JCheckBox(); 
    JLabel showSensesOnlyOnFocusLabel = new JLabel("Show Senses Only on Focus");
    JCheckBox showSensesOnlyOnFocusCheckbox = new JCheckBox(); 
    JLabel showLogsLabel = new JLabel("Show Logs");
    JCheckBox showLogsCheckbox = new JCheckBox();
    JLabel showInformationOnFocusLabel = new JLabel("Show Information on Focus");
    JCheckBox showInformationOnFocusCheckbox = new JCheckBox(); 
    JLabel showWorldInformationLabel = new JLabel("Show World Information");
    JCheckBox showWorldInformationCheckbox = new JCheckBox(); 
    JLabel updateRateLabel = new JLabel("Show Update Rate");
    JCheckBox updateRateCheckbox = new JCheckBox();
    
    
    

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
                Options.tau = Double.parseDouble(tauInput.getText());
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

        viewRangeInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen und Punkt können eingegeben werden, weil Speed ein double ist
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || (ke.getKeyChar() == '.') || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    viewRangeInput.setEditable(true);
                } else {
                    viewRangeInput.setEditable(false);
                }
            }
         });
         viewRangeInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu double konvertiert
                Options.viewRange = Double.parseDouble(viewRangeInput.getText());
            }
        });
        panelTextFields.add(viewRangeLabel);
        viewRangeInput.setText(Double.toString(Options.viewRange));
        panelTextFields.add(viewRangeInput);

        communicationRadiusInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen und Punkt können eingegeben werden, weil Speed ein double ist
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || (ke.getKeyChar() == '.') || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    communicationRadiusInput.setEditable(true);
                } else {
                    communicationRadiusInput.setEditable(false);
                }
            }
         });
         communicationRadiusInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu double konvertiert
                Options.communicationRadius = Double.parseDouble(communicationRadiusInput.getText());
            }
        });
        panelTextFields.add(communicationRadiusLabel);
        communicationRadiusInput.setText(Double.toString(Options.communicationRadius));
        panelTextFields.add(communicationRadiusInput);

        eggScaleInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    eggScaleInput.setEditable(true);
                } else {
                    eggScaleInput.setEditable(false);
                }
            }
         });
         eggScaleInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.eggScale = Integer.parseInt(eggScaleInput.getText());
            }
        });
        panelTextFields.add(eggScaleLabel);
        eggScaleInput.setText(Integer.toString(Options.eggScale));
        panelTextFields.add(eggScaleInput);

        organismScaleInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    organismScaleInput.setEditable(true);
                } else {
                    organismScaleInput.setEditable(false);
                }
            }
         });
         organismScaleInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.organismScale = Integer.parseInt(organismScaleInput.getText());
            }
        });
        panelTextFields.add(organismScaleLabel);
        organismScaleInput.setText(Integer.toString(Options.organismScale));
        panelTextFields.add(organismScaleInput);

        foodScaleInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    foodScaleInput.setEditable(true);
                } else {
                    foodScaleInput.setEditable(false);
                }
            }
         });
         foodScaleInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // String wird zu int konvertiert
                Options.foodScale = Integer.parseInt(foodScaleInput.getText());
            }
        });
        panelTextFields.add(foodScaleLabel);
        foodScaleInput.setText(Integer.toString(Options.foodScale));
        panelTextFields.add(foodScaleInput);


        showAllPossiblyInViewCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Options.showAllPossiblyInView = !Options.showAllPossiblyInView;
            }
        });
        showAllPossiblyInViewCheckbox.setSelected(Options.showAllPossiblyInView);
        panelCheckboxes.add(showAllPossiblyInViewLabel);
        panelCheckboxes.add(showAllPossiblyInViewCheckbox);

        showPossiblyInViewCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Options.showPossiblyInView = !Options.showPossiblyInView;
            }
        });
        showPossiblyInViewCheckbox.setSelected(Options.showPossiblyInView);
        panelCheckboxes.add(showPossiblyInViewLabel);
        panelCheckboxes.add(showPossiblyInViewCheckbox);

        showViewRangeCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Options.showViewRange = !Options.showViewRange;
            }
        });
        showViewRangeCheckbox.setSelected(Options.showViewRange);
        panelCheckboxes.add(showViewRangeLabel);
        panelCheckboxes.add(showViewRangeCheckbox);

        showViewCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Options.showView = !Options.showView;
            }
        });
        showViewCheckbox.setSelected(Options.showView);
        panelCheckboxes.add(showViewLabel);
        panelCheckboxes.add(showViewCheckbox);

        showCommunicationCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Options.showCommunication = !Options.showCommunication;
            }
        });
        showCommunicationCheckbox.setSelected(Options.showCommunication);
        panelCheckboxes.add(showCommunicationLabel);
        panelCheckboxes.add(showCommunicationCheckbox);

        showSensesOnlyOnFocusCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Options.showSensesOnlyOnFocus = !Options.showSensesOnlyOnFocus;
            }
        });
        showSensesOnlyOnFocusCheckbox.setSelected(Options.showSensesOnlyOnFocus);
        panelCheckboxes.add(showSensesOnlyOnFocusLabel);
        panelCheckboxes.add(showSensesOnlyOnFocusCheckbox);

        showLogsCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Options.showLogs = !Options.showLogs;
            }
        });
        showLogsCheckbox.setSelected(Options.showLogs);
        panelCheckboxes.add(showLogsLabel);
        panelCheckboxes.add(showLogsCheckbox);

        showInformationOnFocusCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Options.showInformationOnFocus = !Options.showInformationOnFocus;
            }
        });
        showInformationOnFocusCheckbox.setSelected(Options.showInformationOnFocus);
        panelCheckboxes.add(showInformationOnFocusLabel);
        panelCheckboxes.add(showInformationOnFocusCheckbox);

        showWorldInformationCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Options.showWorldInformation = !Options.showWorldInformation;
            }
        });
        showWorldInformationCheckbox.setSelected(Options.showWorldInformation);
        panelCheckboxes.add(showWorldInformationLabel);
        panelCheckboxes.add(showWorldInformationCheckbox);

        updateRateCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Options.updateRate = !Options.updateRate;
            }
        });
        updateRateCheckbox.setSelected(Options.updateRate);
        panelCheckboxes.add(updateRateLabel);
        panelCheckboxes.add(updateRateCheckbox);

        




        


        

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
