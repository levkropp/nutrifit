package org.example;

import javax.swing.*;

public class CreateAccountScreen extends JPanel {
    private JLabel nameLabel = new JLabel("Name:");
    private JTextField nameField = new JTextField(20);
    private JButton saveButton = new JButton("Save");

    public CreateAccountScreen() {
        this.add(nameLabel);
        this.add(nameField);
        this.add(saveButton);
        // Add more components as required...
        // Add action listeners to buttons...

        //
        this.add(new BackButton());
    }
}