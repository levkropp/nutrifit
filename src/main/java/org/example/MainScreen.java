package org.example;

import javax.swing.*;
import java.awt.*;

public class MainScreen {
    JFrame frame = new JFrame("Nutrifit: Eat, Run, Smile!");
    JPanel mainPanel = new JPanel(new CardLayout());
    HomeScreen homeScreen = new HomeScreen();
    CreateAccountScreen createAccountScreen = new CreateAccountScreen();
    LogDietDataScreen logDietDataScreen = new LogDietDataScreen();
    LogExerciseDataScreen logExerciseDataScreen = new LogExerciseDataScreen();
    VisualizeCalorieIntakeScreen visualizeCalorieIntakeScreen = new VisualizeCalorieIntakeScreen();
    VisualizeNutrientIntakeScreen visualizeNutrientIntakeScreen = new VisualizeNutrientIntakeScreen();
    PredictWeightLossScreen predictWeightLossScreen = new PredictWeightLossScreen();
    CompareDietScreen compareDietScreen = new CompareDietScreen();

    public MainScreen(){
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel.add(homeScreen, "HOME");
        mainPanel.add(createAccountScreen, "CREATE_ACCOUNT");
        mainPanel.add(logDietDataScreen, "LOG_DIET");
        mainPanel.add(logExerciseDataScreen, "LOG_EXERCISE");
        mainPanel.add(visualizeCalorieIntakeScreen, "VISUALIZE_CALORIE");
        mainPanel.add(visualizeNutrientIntakeScreen, "VISUALIZE_NUTRIENT");
        mainPanel.add(predictWeightLossScreen, "PREDICT_WEIGHT");
        mainPanel.add(compareDietScreen, "COMPARE_DIET");

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}