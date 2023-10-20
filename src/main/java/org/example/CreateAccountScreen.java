package org.example;

import javax.swing.*;

import java.awt.*;
import java.sql.*;

public class CreateAccountScreen extends JPanel {
    private final JLabel nameLabel = new JLabel("Create New Account");
    private final JTextField nameField = new JTextField(10);
    private final JLabel heightLabel = new JLabel("Height (cm):");
    private final JTextField heightField = new JTextField(5);
    private final JLabel weightLabel = new JLabel("Weight (kg):");
    private final JTextField weightField = new JTextField(5);
    private final JLabel ageLabel = new JLabel("Age (years):");
    private final JTextField ageField = new JTextField(5);
    private final JButton saveButton = new JButton("Save");

    private final JLabel sexLabel = new JLabel("Sex: ");

    private final JRadioButton maleButton = new JRadioButton("Male");
    private final JRadioButton femaleButton = new JRadioButton("Female");
    private final ButtonGroup sexButtonGroup = new ButtonGroup();


    private final JLabel selectedAccountLabel = new JLabel("Selected Account:");
    private final JComboBox<String> accountDropdown = new JComboBox<>();
    private final JLabel selectedAccountNameLabel = new JLabel("Name: N/A");
    private final JLabel selectedAccountHeightLabel = new JLabel("Height: N/A");
    private final JLabel selectedAccountWeightLabel = new JLabel("Weight: N/A");

    private final JLabel selectedAccountAgeLabel = new JLabel("Age: N/A");
    private final JLabel selectedAccountSexLabel = new JLabel("Sex: N/A");


    private final JLabel bmrLabel = new JLabel("BMR: N/A");


    public CreateAccountScreen() {
//        this.add(nameLabel);
//        this.add(nameField);
//
//        this.add(heightLabel);
//        this.add(heightField);
//
//        this.add(weightLabel);
//        this.add(weightField);
//
//        this.add(ageLabel);
//        this.add(ageField);
//
//        this.add(sexLabel);
//        sexButtonGroup.add(maleButton);
//        sexButtonGroup.add(femaleButton);
//        this.add(maleButton);
//        this.add(femaleButton);
//        maleButton.setSelected(true);
//
//        this.add(saveButton);

        this.setLayout(new BorderLayout()); // Set the overall layout to BorderLayout

        // JPanel for left side
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(6, 2)); // 6 rows and 2 columns
        leftPanel.add(nameLabel);
        leftPanel.add(nameField);
        leftPanel.add(heightLabel);
        leftPanel.add(heightField);
        leftPanel.add(weightLabel);
        leftPanel.add(weightField);
        leftPanel.add(ageLabel);
        leftPanel.add(ageField);
        sexButtonGroup.add(maleButton);
        sexButtonGroup.add(femaleButton);
        leftPanel.add(maleButton);
        leftPanel.add(femaleButton);
        maleButton.setSelected(true);
        leftPanel.add(saveButton);

        // JPanel for right side
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(7, 1)); // 7 rows and 1 column
        rightPanel.add(selectedAccountLabel);
        rightPanel.add(accountDropdown);
        rightPanel.add(selectedAccountNameLabel);
        rightPanel.add(selectedAccountHeightLabel);
        rightPanel.add(selectedAccountWeightLabel);
        rightPanel.add(selectedAccountAgeLabel);
        rightPanel.add(selectedAccountSexLabel);

        this.add(leftPanel, BorderLayout.WEST); // Add leftPanel to the left (west) side of the BorderLayout
        this.add(rightPanel, BorderLayout.EAST); // Add rightPanel to the right (east) side of the BorderLayout

        // JPanel for bottom center (back button)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Align the BackButton to the center
        bottomPanel.add(bmrLabel);
        bottomPanel.add(new BackButton());

        this.add(bottomPanel, BorderLayout.SOUTH); // Add bottomPanel to the bottom (south) of the BorderLayout

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:nutrifit.db")) {
            String sql = "SELECT * FROM accounts WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, MainScreen.currentAccount);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        selectedAccountNameLabel.setText("Name: "+resultSet.getString("name"));
                        selectedAccountHeightLabel.setText("Height (cm): " + resultSet.getInt("height"));
                        selectedAccountWeightLabel.setText("Weight (kg): " + resultSet.getInt("weight"));
                        selectedAccountAgeLabel.setText("Age: " + resultSet.getInt("age"));
                        selectedAccountSexLabel.setText("Sex: " + (resultSet.getInt("sex") == 1 ? "Male" : "Female"));

                    }
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }


        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            if (!name.matches("[a-zA-Z]{3,10}")) {
                JOptionPane.showMessageDialog(null, "Account name must be all letters and between 3 and 10 characters long.", "Invalid Account Name", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int height = Integer.parseInt(heightField.getText());
            int weight = Integer.parseInt(weightField.getText());
            int age = Integer.parseInt(ageField.getText());
            int sex = maleButton.isSelected() ? 1 : 0;

            //Clear the fields once data is submitted
            nameField.setText("");
            heightField.setText("");
            weightField.setText("");
            ageField.setText("");

            if (height < 50 || height > 300 || weight < 30 || weight > 300) {
                JOptionPane.showMessageDialog(null, "Height must be between 50 and 300 cm. Weight must be between 30 and 300 kg.", "Invalid Height or Weight", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (age < 10 || age > 99) {
                JOptionPane.showMessageDialog(null, "Age must be between 10 and 99 years old.", "Invalid Age", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:nutrifit.db")) {
                String sql = "SELECT name FROM accounts WHERE name = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, name);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            saveButton.setText("Update");
                        } else {
                            saveButton.setText("Save");
                        }
                    }
                }

                sql = "INSERT OR REPLACE INTO accounts (name, height, weight, age, sex) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setInt(2, height);
                    preparedStatement.setInt(3, weight);
                    preparedStatement.setInt(4, age);
                    preparedStatement.setInt(5, sex);
                    preparedStatement.executeUpdate();

                    // Refresh account list in dropdown
                    accountDropdown.removeAllItems();
                    try (Statement statement = connection.createStatement();
                         ResultSet resultSet = statement.executeQuery("SELECT name FROM accounts")) {
                        while (resultSet.next()) {
                            accountDropdown.addItem(resultSet.getString("name"));
                        }
                    }

                    JOptionPane.showMessageDialog(null, "Account saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException sqlException) {
                JOptionPane.showMessageDialog(null, "Error saving account: " + sqlException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

//        this.add(selectedAccountLabel);
//        this.add(accountDropdown);
//        this.add(selectedAccountNameLabel);
//        this.add(selectedAccountHeightLabel);
//        this.add(selectedAccountWeightLabel);
//        this.add(selectedAccountAgeLabel);
//        this.add(selectedAccountSexLabel);

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:nutrifit.db")) {
            String sql = "INSERT OR IGNORE INTO accounts (name, height, weight, age) VALUES ('guest', 200, 100, 20)";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            }

            sql = "SELECT name FROM accounts";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while(resultSet.next()) {
                    accountDropdown.addItem(resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        accountDropdown.addActionListener(e -> {
            String selectedAccount = (String) accountDropdown.getSelectedItem();
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:nutrifit.db")) {
                String sql = "SELECT * FROM accounts WHERE name = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, selectedAccount);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            MainScreen.currentAccount = resultSet.getString("name");
                            selectedAccountNameLabel.setText("Name: "+resultSet.getString("name"));
                            selectedAccountHeightLabel.setText("Height (cm): " + resultSet.getInt("height"));
                            selectedAccountWeightLabel.setText("Weight (kg): " + resultSet.getInt("weight"));
                            selectedAccountAgeLabel.setText("Age: " + resultSet.getInt("age"));
                            selectedAccountSexLabel.setText("Sex: " + (resultSet.getInt("sex") == 1 ? "Male" : "Female"));

                            // Calculate BMR and update label
                            int height = resultSet.getInt("height");
                            int weight = resultSet.getInt("weight");
                            int age = resultSet.getInt("age");
                            int sex = resultSet.getInt("sex");
                            calculateBMR(height, weight, age, sex);

                            // Update the heading1 JLabel in the HomeScreen
                            HomeScreen homeScreen = (HomeScreen) this.getParent().getComponent(0);
                            homeScreen.updateCurrentAccount();
                        }
                    }
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });

    }

    private void calculateBMR(int height, int weight, int age, int sex) {
        double bmr = (10 * weight) + (6.25 * height) - (5 * age);
        bmr = (sex == 1) ? bmr + 5 : bmr - 161; // Adjust BMR based on sex
        bmrLabel.setText("BMR: " + bmr);
    }
}