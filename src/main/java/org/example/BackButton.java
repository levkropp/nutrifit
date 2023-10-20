package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackButton extends JButton {
    public BackButton() {
        super("Back");

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the top-level JFrame
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());

                // Assuming the JFrame's contentPane layout is BorderLayout
                BorderLayout borderLayout = (BorderLayout) frame.getContentPane().getLayout();

                // Assuming the 'center' component of the JFrame's contentPane is the mainPanel
                JPanel mainPanel = (JPanel) borderLayout.getLayoutComponent(BorderLayout.CENTER);

                // Now you can get the CardLayout from mainPanel
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

                // Show the HOME card
                cardLayout.show(mainPanel, "HOME");
            }
        });
    }
}