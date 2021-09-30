package com.novelvox.hrapp.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.novelvox.hrapp.util.EmployeeConstant;

//LOGIN FORM INCLUDING MAIN METHOD

class Main extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    static Home home = null;

    JLabel usernameLabel, passwrdLabel, headingLabel, logoLabel;
    TextField userNamefield, passwrdField;
    JButton loginButton, clearButton, exitButton;
    JCheckBox jc, jc1;
    Image img = null;

    Main() {

        setLayout(null);
        setBackground(Color.RED);
        try {
            BufferedImage myPicture = ImageIO.read(new File(EmployeeConstant.NV_LOGO));
            logoLabel = new JLabel(new ImageIcon(myPicture));
        } catch (IOException e1) {
            System.out.println("Failed to load Logo image.");
        }
        usernameLabel = new JLabel("Enter User_Name:");
        passwrdLabel = new JLabel("Enter Password :");
        headingLabel = new JLabel("Novelvox Software India Pvt.Ltd.");
        headingLabel.setHorizontalAlignment(JLabel.CENTER);

        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        headingLabel.setFont(font1);

        userNamefield = new TextField();
        passwrdField = new TextField();

        passwrdField.setEchoChar('*');

        loginButton = new JButton("Login");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");

        loginButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);

        loginButton.setToolTipText("Click here to Login");
        clearButton.setToolTipText("Click here to Reset Field");
        exitButton.setToolTipText("Click here to Exit");

        jc = new JCheckBox("Show Password");
        jc.addActionListener(this);

        jc1 = new JCheckBox("Remember ID & Password");
        jc1.addActionListener(this);

        logoLabel.setBounds(30, 10, 50, 50);
        headingLabel.setBounds(10, 10, 480, 50);
        usernameLabel.setBounds(50, 120, 130, 30);
        passwrdLabel.setBounds(50, 170, 130, 30);
        userNamefield.setBounds(190, 120, 170, 30);
        passwrdField.setBounds(190, 170, 170, 30);
        jc.setBounds(200, 210, 200, 30);
        jc1.setBounds(200, 240, 300, 30);
        loginButton.setBounds(70, 350, 120, 30);
        clearButton.setBounds(210, 350, 120, 30);
        exitButton.setBounds(340, 350, 120, 30);

        add(logoLabel);
        add(usernameLabel);
        add(passwrdLabel);
        add(headingLabel);
        add(userNamefield);
        add(passwrdField);
        add(loginButton);
        add(clearButton);
        add(exitButton);
        add(jc);
        add(jc1);

        Font font2 = new Font("SansSerif", Font.BOLD, 15);
        usernameLabel.setFont(font2);
        passwrdLabel.setFont(font2);
        // jc.setFont(font2);
        // jc1.setFont(font2);

        // Timer t = new Timer(400, this);
        // t.start();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 550);
        setVisible(true);
        setTitle("LOGIN HR");
        setResizable(true);
        setBackground(Color.red);
        loginButton.setForeground(Color.BLUE);
        clearButton.setForeground(Color.BLUE);
        exitButton.setForeground(Color.BLUE);
        headingLabel.setForeground(Color.BLUE);

    }

    public void actionPerformed(ActionEvent ae) {

        // String oldText = l3.getText();
        // String newText = oldText.substring(1) + oldText.substring(0, 1);
        // l3.setText(newText);

        String v1, v2;
        v1 = userNamefield.getText();
        v2 = passwrdField.getText();

        if (ae.getSource() == loginButton) {
            if (v1.equals("Novelvox") && v2.equals("NovelvoxHR")) {
                home = new Home();
                this.setVisible(false);
            } else {
                System.out.println("enter the valid username and password");
                JOptionPane.showMessageDialog(this, "Incorrect login or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (ae.getSource() == clearButton) {
            userNamefield.setText("");
            passwrdField.setText("");

        }

        if (ae.getSource() == exitButton) {

            System.exit(0);

        }

        if (ae.getSource() == jc) {
            if (jc.isSelected()) {
                passwrdField.setEchoChar((char) 0);
            } else {
                passwrdField.setEchoChar('*');
            }
        }

        if (ae.getSource() == jc1) {
            if (jc1.isSelected()) {
                userNamefield.setText("Novelvox");
                passwrdField.setText("NovelvoxHR");
            } else {
                userNamefield.setText("");
                passwrdField.setText("");
            }
        }

    }

    public static void main(String ar[]) {
        new Main();
    }

}
