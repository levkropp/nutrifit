package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.example.MainScreen.currentAccount;

public class HomeScreen extends JPanel {
    JButton button1 = new JButton("Manage Accounts");
    JButton button2 = new JButton("Log Diet Data");
    JButton button3 = new JButton("Log Exercise Data");
    JButton button4 = new JButton("Visualize Calorie Intake");
    JButton button5 = new JButton("Visualize Nutrient Intake");
    JButton button6 = new JButton("Predict Weight Loss");
    JButton button7 = new JButton("Compare Diet");

    JLabel title = new JLabel("Welcome to Nutrifit!");
    JLabel heading1 = new JLabel("current account: "+ currentAccount);
    JLabel heading2 = new JLabel("info box 2");

    public void updateCurrentAccount() {
        heading1.setText("Current account: " + currentAccount);
    }

    public HomeScreen(){
        this.setLayout(new GridLayout(4, 1)); // 4 rows, 1 column

        // Create title panel
        JPanel titlePanel = new JPanel();
        titlePanel.add(title);
        this.add(titlePanel);



        // Create heading panels
        JPanel headingPanel1 = new JPanel();
        headingPanel1.add(heading1);
        this.add(headingPanel1);

        JPanel headingPanel2 = new JPanel();
        headingPanel2.add(heading2);
        this.add(headingPanel2);



        // Create button panel with 2 columns
        JPanel buttonPanel = new JPanel(new GridLayout(4, 2)); // 4 rows, 2 columns
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        buttonPanel.add(button5);
        buttonPanel.add(button6);
        buttonPanel.add(button7);
        this.add(buttonPanel);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) HomeScreen.this.getParent().getLayout();
                cardLayout.show(HomeScreen.this.getParent(), "CREATE_ACCOUNT");
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) HomeScreen.this.getParent().getLayout();
                cardLayout.show(HomeScreen.this.getParent(), "LOG_DIET");
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) HomeScreen.this.getParent().getLayout();
                cardLayout.show(HomeScreen.this.getParent(), "LOG_EXERCISE");
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) HomeScreen.this.getParent().getLayout();
                cardLayout.show(HomeScreen.this.getParent(), "VISUALIZE_CALORIE");
            }
        });

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) HomeScreen.this.getParent().getLayout();
                cardLayout.show(HomeScreen.this.getParent(), "VISUALIZE_NUTRIENT");
            }
        });

        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) HomeScreen.this.getParent().getLayout();
                cardLayout.show(HomeScreen.this.getParent(), "PREDICT_WEIGHT");
            }
        });

        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) HomeScreen.this.getParent().getLayout();
                cardLayout.show(HomeScreen.this.getParent(), "COMPARE_DIET");
            }
        });
    }
}