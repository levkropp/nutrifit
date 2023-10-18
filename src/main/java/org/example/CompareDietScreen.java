package org.example;
import javax.swing.*;

public class CompareDietScreen extends JPanel {
    private JLabel label = new JLabel("Diet Comparison");


    public CompareDietScreen() {
        this.add(label);
        // Add components to compare diet...

        //
        this.add(new BackButton());
    }
}
