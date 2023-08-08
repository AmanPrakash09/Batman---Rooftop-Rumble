package ui;

import model.Game;
import model.Score;
import model.Scoreboard;
import persistence.JsonReaderScoreboard;
import persistence.JsonWriterScoreboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// this class is responsible for adding the Scoreboard at the end of the game
public class AddScorePanel extends JFrame {
    private static final String JSON_STORE = "./data/scoreboard.json";

    // add fields to represent changing properties of AddScorePanel
    private JsonReaderScoreboard jsonReader;
    private JsonWriterScoreboard jsonWriter;
    private Scoreboard scoreboard;
    private JTextField nameField;
    private JButton addButton;
    private JButton removeButton;
    private Game game;
    private int score;
    private String name;
    JTable table;
    JScrollPane scrollPane;
    Score newScore;

    // EFFECTS: constructs AddScorePanel with initial properties
    public AddScorePanel(Game g) throws IOException {
        game = g;
        score = g.getScore();
        jsonReader = new JsonReaderScoreboard(JSON_STORE);
        jsonWriter = new JsonWriterScoreboard(JSON_STORE);
        scoreboard = jsonReader.read();

        setTitle("Scoreboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        updateTable();
        updatePanel();

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a JTable and adds it to a JScrollPane to represent the Scoreboard
    public void updateTable() {
        table = createScoreboardTable(scoreboard);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        add(scrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: creates a JPanel.
    //          Adds a JTextField where the user can enter their name.
    //          Adds a JButton that adds the user's score to the Scoreboard.
    //          Adds a JButton that removes that same score and name from the Scoreboard.
    public void updatePanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        nameField = new JTextField(20);
        addButton = new JButton("Add Score");
        addButton.addActionListener(new AddScoreListener());

        inputPanel.add(new JLabel("Enter your name: "));
        inputPanel.add(nameField);
        inputPanel.add(addButton);

        removeButton = new JButton("Remove Score");
        removeButton.addActionListener(new RemoveScoreListener());
        inputPanel.add(removeButton);
        removeButton.setEnabled(false);

        add(inputPanel, BorderLayout.SOUTH);
    }

    // REQUIRES: Scoreboard
    // MODIFIES: this
    // EFFECTS: uses the data in the Scoreboard to create a table
    private JTable createScoreboardTable(Scoreboard scoreboard) {
        String[] columnNames = {"Players", "Scores"};
        Object[][] data = new Object[scoreboard.getSize()][2];

        // Populate the data array with names and scores from the Scoreboard
        for (int i = 0; i < scoreboard.getSize(); i++) {
            data[i][0] = scoreboard.getUserList().get(i);
            data[i][1] = scoreboard.getScoreList().get(i);
        }

        // CITATION:
        // https://www.tutorialspoint.com/how-to-create-defaulttablemodel-which-is-an-implementation-of-tablemodel
        // http://www.java2s.com/Tutorial/Java/0240__Swing/CreateDefaultTableModel.htm
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        return table;
    }

    // MODIFIES: this
    // EFFECTS: called when the "Add Score" JButton btn is clicked.
    //          Inserts the user's name and score to the table at the appropriate position.
    //          Updates Scoreboard as well.
    private class AddScoreListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            name = nameField.getText();
//            int score = game.getScore();
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            if (!name.isBlank()) {

                if (scoreboard.getUserList().contains(name)) {
                    int removeIndex = scoreboard.getUserList().indexOf(name);
                    model.removeRow(removeIndex);
                }

                newScore = new Score(name, score);
                try {
                    scoreboard.addScore(newScore);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                try {
                    jsonWriter.open();
                    jsonWriter.write(scoreboard);
                    jsonWriter.close();
//                    System.out.println("Updated scores to " + JSON_STORE);
                } catch (FileNotFoundException e1) {
//                    System.out.println("Unable to write to file: " + JSON_STORE);
                }

                int insertIndex = scoreboard.getScoreList().indexOf(score);
                model.insertRow(insertIndex, new Object[]{name, score});

                nameField.setText("");
                nameField.setEnabled(false);
                addButton.setEnabled(false);

                removeButton.setEnabled(true);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: called when the "Remove Score" JButton btn is clicked.
    //          Removes the user's name and score from the table.
    //          Updates Scoreboard as well.
    private class RemoveScoreListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int removeIndex = scoreboard.getScoreList().indexOf(score);
            model.removeRow(removeIndex);

            try {
                scoreboard.removeScore(newScore);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            try {
                jsonWriter.open();
                jsonWriter.write(scoreboard);
                jsonWriter.close();
//                    System.out.println("Updated scores to " + JSON_STORE);
            } catch (FileNotFoundException e1) {
//                    System.out.println("Unable to write to file: " + JSON_STORE);
            }

            removeButton.setEnabled(false);
        }
    }

}
