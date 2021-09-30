package com.novelvox.hrapp.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.novelvox.hrapp.util.EmployeeHelper;

/**
 * HR LOGOUT FORM
 *
 */
class Logout extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    JLabel ll1;
    JButton bb1, bb2;

    Logout() {

        setLayout(null);
        setBackground(Color.CYAN);

        ll1 = new JLabel("Thank You For Visiting HR Portal                    ");
        ll1.setBounds(10, 20, 1420, 80);
        add(ll1);
        ll1.setForeground(Color.BLUE);
        ll1.setHorizontalAlignment(JLabel.CENTER);
        Font font3 = new Font("SansSerif", Font.BOLD, 30);
        ll1.setFont(font3);

        bb1 = new JButton("LOGOUT");
        bb1.addActionListener(this);
        bb2 = new JButton("Exit");
        bb2.addActionListener(this);

        add(bb1);
        add(bb2);

        bb1.setBounds(500, 300, 100, 30);
        bb2.setBounds(610, 300, 100, 30);
        bb1.setForeground(Color.BLUE);
        bb2.setForeground(Color.BLUE);

        Timer t = new Timer(400, this);
        t.start();

    }

    public void actionPerformed(ActionEvent ae) {

        String oldText = ll1.getText();
        String newText = oldText.substring(1) + oldText.substring(0, 1);
        ll1.setText(newText);

        EmployeeHelper.closeConnection();
        if (ae.getSource() == bb1) {
            Main lg = new Main();
            lg.setVisible(true);
            Main.home.dispose();
        }

        if (ae.getSource() == bb2) {
            System.exit(0);
        }

    }
}
