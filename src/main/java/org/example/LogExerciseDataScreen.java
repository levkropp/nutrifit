package org.example;

import javax.swing.*;

public class LogExerciseDataScreen extends JPanel {
    private JLabel exerciseLabel = new JLabel("Exercise:");
    private JTextField exerciseField = new JTextField(20);
    private JButton logButton = new JButton("Log");


    public LogExerciseDataScreen() {
        this.add(exerciseLabel);
        this.add(exerciseField);
        this.add(logButton);
        // Add more components as required...
        // Add action listeners to buttons...

        //
        this.add(new BackButton());
    }
}