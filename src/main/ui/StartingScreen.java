package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Represents the screen before the GUI starts and prompts the user whether they want to play
 * CITATION: ButtonExample
 */
public class StartingScreen extends JFrame implements ActionListener {
    private JButton playButton;

    public StartingScreen() {
        super("Batman - Rooftop Rumble");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 90));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());

        playButton = new JButton("Play");
        playButton.addActionListener(this);

        add(playButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: called when the JButton btn is clicked.
    //          Starts the GUI.
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            setVisible(false);
            SwingUtilities.invokeLater(GUI::new);
        }
    }
}