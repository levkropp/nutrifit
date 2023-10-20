package org.example;

import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class PredictWeightLossScreen extends JPanel {
    private final JLabel label = new JLabel("Weight Loss Prediction");

    public PredictWeightLossScreen() {
        this.add(label);
        // Add components to predict weight loss...

        // Add JFreeChart
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        for (int week = 0; week <= 52; week++) {
            // Assuming weight loss of 0.5kg per week
            line_chart_dataset.addValue(week * 0.5, "Weight", Integer.toString(week));
        }

        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "Weight Loss Prediction",   // Title
                "Week",                     // X-axis Label
                "Weight Loss (kg)",         // Y-axis Label
                line_chart_dataset,         // Dataset
                PlotOrientation.VERTICAL,    // Plot orientation
                true,                       // Show legend
                true,                       // Use tooltips
                false                       // Generate URLs
        );

        // Create ChartPanel and add it to JPanel
        ChartPanel chartPanel = new ChartPanel(lineChartObject);
        this.add(chartPanel);

        this.add(new BackButton());
    }
}
