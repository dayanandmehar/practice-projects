package com.novelvox.hrapp.main;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * HR APPLICATION HOME PAGE
 *
 */
class Home extends JFrame {

    private static final long serialVersionUID = 1L;
    JTabbedPane jtab;

    Home() {

        jtab = new JTabbedPane();
        jtab.addTab("New Employee", new EmpRegister());
        jtab.addTab("Search Employee", new EmpSearch());
        jtab.addTab("Update Employee", new EmpUpdate());
        jtab.addTab("Salary Slips", new EmpSalarySlips());
        jtab.addTab("Logout", new Logout());

        getContentPane().add(jtab);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Welcome to HR Portal");
        setSize(1440, 750);
        setVisible(true);
        setResizable(true);

    }

}
