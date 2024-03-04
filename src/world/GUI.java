package world;

import graphics.Display;
import world.World;
import support.Options;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class GUI implements ActionListener{
    int phase = 0;
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JButton button = new JButton("Start Simulation");

    public GUI(){
        panel.setBorder(BorderFactory.createEmptyBorder(Options.width, Options.width, Options.height, Options.width));
        panel.setLayout(new GridLayout(0, 1));
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Evolution Simulation");
        frame.pack();
        frame.setVisible(true);

        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        phase++;
        if(phase == 1){
            frame.dispose();
            new Thread(new Loop(new World(Options.width, Options.height), new Display(Options.width, Options.height))).start();
        }
    }
}
