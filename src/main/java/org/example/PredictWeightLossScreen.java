package org.example;

import javax.swing.*;

public class PredictWeightLossScreen extends JPanel {
    private JLabel label = new JLabel("Weight Loss Prediction");



    public PredictWeightLossScreen() {
        this.add(label);
        // Add components to predict weight loss...

        this.add(new BackButton());
    }
}
