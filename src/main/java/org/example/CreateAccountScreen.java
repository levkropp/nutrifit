package org.example;

import javax.swing.*;

import java.sql.*;

public class CreateAccountScreen extends JPanel {
    private JLabel nameLabel = new JLabel("Create New Account:");
    private JTextField nameField = new JTextField(20);
    private JLabel heightLabel = new JLabel("Height (cm):");
    private JTextField heightField = new JTextField(5);
    private JLabel weightLabel = new JLabel("Weight (kg):");
    private JTextField weightField = new JTextField(5);
    private JButton saveButton = new JButton("Save");

    private JLabel selectedAccountLabel = new JLabel("Selected Account:");
    private JComboBox<String> accountDropdown = new JComboBox<>();
    private JLabel selectedAccountNameLabel = new JLabel("Name: N/A");
    private JLabel selectedAccountHeightLabel = new JLabel("Height: N/A");
    private JLabel selectedAccountWeightLabel = new JLabel("Weight: N/A");

    public CreateAccountScreen() {
        this.add(nameLabel);
        this.add(nameField);
        this.add(heightLabel);
        this.add(heightField);
        this.add(weightLabel);
        this.add(weightField);
        this.add(saveButton);

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:nutrifit.db")) {
            String sql = "SELECT * FROM accounts WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, MainScreen.currentAccount);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        selectedAccountNameLabel.setText("Name: "+resultSet.getString("name"));
                        selectedAccountHeightLabel.setText("Height (cm): "+String.valueOf(resultSet.getInt("height")));
                        selectedAccountWeightLabel.setText("Weight (kg): "+String.valueOf(resultSet.getInt("weight")));
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

            if (height < 50 || height > 300 || weight < 30 || weight > 300) {
                JOptionPane.showMessageDialog(null, "Height must be between 50 and 300 cm. Weight must be between 30 and 300 kg.", "Invalid Height or Weight", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:mutrifit.db")) {
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

                sql = "INSERT OR REPLACE INTO accounts (name, height, weight) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setInt(2, height);
                    preparedStatement.setInt(3, weight);
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

        this.add(selectedAccountLabel);
        this.add(accountDropdown);
        this.add(selectedAccountNameLabel);
        this.add(selectedAccountHeightLabel);
        this.add(selectedAccountWeightLabel);

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:nutrifit.db")) {
            String sql = "INSERT OR IGNORE INTO accounts (name, height, weight) VALUES ('guest', 200, 100)";
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
                            selectedAccountHeightLabel.setText("Height (cm): "+String.valueOf(resultSet.getInt("height")));
                            selectedAccountWeightLabel.setText("Weight (kg): "+String.valueOf(resultSet.getInt("weight")));

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

        this.add(new BackButton());
    }
}