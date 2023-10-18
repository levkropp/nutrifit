package org.example;

import javax.swing.*;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackButton extends JButton {

    public BackButton() {
        super("Back");

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the grandparent, which should be the mainPanel with CardLayout
                CardLayout cardLayout = (CardLayout) ((JButton)e.getSource()).getParent().getParent().getLayout();
                cardLayout.show(((JButton)e.getSource()).getParent().getParent(), "HOME");
            }
        });
    }
}