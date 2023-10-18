package org.example;

import javax.swing.*;

public class VisualizeCalorieIntakeScreen extends JPanel {
    private JLabel label = new JLabel("Calorie Intake Visualization");


    public VisualizeCalorieIntakeScreen() {
        this.add(label);
        // Add components to visualize calorie intake...

        //
        this.add(new BackButton());
    }
}
