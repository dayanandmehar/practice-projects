package com.novelvox.hrapp.main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.novelvox.hrapp.util.Employee;
import com.novelvox.hrapp.util.EmployeeConstant;
import com.novelvox.hrapp.util.EmployeeHelper;

;//

/**
 * SEARCH EMPLOYEE Details
 * 
 */
class EmpSearch extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private EmployeeHelper empHelper = null;

    EmpSearch() {

        setLayout(null);
        setBackground(Color.CYAN);

        empHelper = new EmployeeHelper();
        add(EmployeeConstant.searchEmpHeading);
        add(empHelper.idLabel);
        add(empHelper.idField);

        add(empHelper.nameField);
        add(empHelper.aadharNoField);
        add(empHelper.emailIdField);
        add(empHelper.mobNoField);
        add(empHelper.dobField);
        add(empHelper.profileField);
        add(empHelper.ctcField);
        add(empHelper.dojField);
        add(empHelper.addressField);

        add(empHelper.nameLabel);
        add(empHelper.aadharLabel);
        add(empHelper.emailLabel);
        add(empHelper.mobNoLabel);
        add(empHelper.dobLabel);
        add(empHelper.profileLabel);
        add(empHelper.ctcLabel);
        add(empHelper.dojLabel);
        add(empHelper.addressLabel);

        add(empHelper.clearButton);
        empHelper.clearButton.addActionListener(this);

        add(empHelper.searchButton);
        empHelper.searchButton.addActionListener(this);

        empHelper.hideElements();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == empHelper.clearButton) {

            empHelper.clear();
            empHelper.hideElements();
        } else if (ae.getSource() == empHelper.searchButton) {
            Statement st = null;
            ResultSet rs = null;
            try {
                EmployeeHelper.createConnection();
                st = EmployeeConstant.con.createStatement();
                String searchQuery = EmployeeConstant.SEARCH_QUERY + empHelper.idField.getText();
                rs = st.executeQuery(searchQuery);

                Employee employee = empHelper.readEmployeeDetails(rs);
                if (employee == null) {
                    JOptionPane.showMessageDialog(this, EmployeeConstant.ID_NOT_FOUND, EmployeeConstant.ERROR, JOptionPane.ERROR_MESSAGE);
                } else {
                    empHelper.displayEmployeeDetails(employee);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    EmployeeConstant.con.close();
                    st.close();
                    rs.close();
                } catch (Exception e) {
                    // Do nothing
                }
            }

        }

    }

}
