

import graphics.Display;
import world.World;
import support.Options;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class GUI{
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
    

    public GUI(){
        //panel.setBorder(BorderFactory.createEmptyBorder(Options.width, Options.width, Options.height, Options.width));
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Evolution Simulation");
        frame.pack();
        //frame.setLayout(new FlowLayout());
        

        speedInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
               String value = speedInput.getText();
               int l = value.length();
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
                Options.speed = new Double(speedInput.getText()).doubleValue();
            }
        });
        panel.add(speedLabel);
        panel.add(speedInput);

        amountOfOrganismsInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
               String value = amountOfOrganismsInput.getText();
               int l = value.length();
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
                Options.amountOfOrganisms = new Integer(amountOfOrganismsInput.getText()).intValue();
            }
        });
        panel.add(amountOfOrganismsLabel);
        panel.add(amountOfOrganismsInput);

        amountOfFoodInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
               String value = amountOfFoodInput.getText();
               int l = value.length();
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
                Options.amountOfFood = new Integer(amountOfFoodInput.getText()).intValue();
            }
        });
        panel.add(amountOfFoodLabel);
        panel.add(amountOfFoodInput);
        

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                phase++;
                if(phase == 1){
                    frame.dispose();
                    new Thread(new Loop(new World(Options.width, Options.height), new Display(Options.width, Options.height))).start();
                }
            }
        });
        button.setBounds(50, 50, 100, 50);
        panel.add(button);

        frame.setVisible(true);
    }


}
