package gui;


import graphics.Display;
import input.Input;
import main.Loop;
import world.World;
import support.Options;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

public class GUI{ // Graphic User Interface

    // für Swing relevante Instanzen werden erstellt
    int phase = 0;
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JButton button = new JButton("Start Simulation");
    JLabel speedLabel = new JLabel("Set Organism Speed:");
    JTextField speedInput = new JTextField(2);
    JLabel amountOfOrganismsLabel = new JLabel("Set Amount of Organisms:");
    JTextField amountOfOrganismsInput = new JTextField(2);
    JLabel amountOfFoodLabel = new JLabel("Set Amount of Food:");
    JTextField amountOfFoodInput = new JTextField(2);
    

    // Das gesamte GUI wird hier erstellt
    public GUI(){

        // Setup
        //panel.setBorder(BorderFactory.createEmptyBorder(Options.width, Options.width, Options.height, Options.width));
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Evolution Simulation");
        frame.pack();
        //frame.setLayout(new FlowLayout());
        
        // Fügt Textfeld mit Label zu Einstellung der Geschwindigkeit hinzu
        speedInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
               // Nur Zahlen und Punkt können eingegeben werden, weil Speed ein double ist
               if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || (ke.getKeyChar() == '.')) {
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
                Options.speed = new Double(speedInput.getText()).doubleValue();
            }
        });
        panel.add(speedLabel);
        panel.add(speedInput);

        // Fügt Textfeld mit Label zu Einstellung der Anzahl der Organismen hinzu
        amountOfOrganismsInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
               // Nur Zahlen können eingegeben werden, weil amountOfOrganisms ein int ist
               if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
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
                Options.amountOfOrganisms = new Integer(amountOfOrganismsInput.getText()).intValue();
            }
        });
        panel.add(amountOfOrganismsLabel);
        panel.add(amountOfOrganismsInput);

        // Fügt Textfeld mit Label zu Einstellung der Anzahl des Essens hinzu
        amountOfFoodInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
               // Nur Zahlen können eingegeben werden, weil amountOfFood ein int ist
               if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
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
                Options.amountOfFood = new Integer(amountOfFoodInput.getText()).intValue();
            }
        });
        panel.add(amountOfFoodLabel);
        panel.add(amountOfFoodInput);
        
        // Ein Button zum Start der Simulation wird hinzugefügt
        button.addActionListener(new ActionListener() {
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
        button.setBounds(50, 50, 100, 50);
        panel.add(button);

        frame.setVisible(true);
    }


}
