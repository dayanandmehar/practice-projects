package com.novelvox.hrapp.main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.novelvox.hrapp.exception.EmployeeValidationException;
import com.novelvox.hrapp.util.Employee;
import com.novelvox.hrapp.util.EmployeeConstant;
import com.novelvox.hrapp.util.EmployeeHelper;

/**
 * NEW EMPLOYEE REGISTRATION
 *
 */
class EmpRegister extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private EmployeeHelper empHelper = null;

    EmpRegister() {
        setLayout(null);
        setBackground(Color.CYAN);
        empHelper = new EmployeeHelper();
        add(EmployeeConstant.newEmpHeading);
        add(empHelper.idLabel);
        add(empHelper.nameLabel);
        add(empHelper.aadharLabel);
        add(empHelper.emailLabel);
        add(empHelper.mobNoLabel);
        add(empHelper.dobLabel);
        add(empHelper.profileLabel);
        add(empHelper.ctcLabel);
        add(empHelper.dojLabel);
        add(empHelper.addressLabel);

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

        empHelper.saveButton.addActionListener(this);
        empHelper.clearButton.addActionListener(this);
        add(empHelper.saveButton);
        add(empHelper.clearButton);

    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == empHelper.saveButton) {
            PreparedStatement pst = null;
            try {
                EmployeeHelper.createConnection();
                Employee employee = empHelper.readEmployeeData();

                // Validate data
                empHelper.doValidation(employee, this);

                // Validate if Employee Id is existing
                checkExitingEmployeeId();

                pst = empHelper.createPreparedStmt(employee, EmployeeConstant.INSERT_QUERY);
                int n = pst.executeUpdate();

                if (n != 0) {
                    JOptionPane.showMessageDialog(this, EmployeeConstant.DATA_STORED_SUCCESS, EmployeeConstant.INFO,
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, EmployeeConstant.DATA_STORED_FAILURE, EmployeeConstant.ERROR, JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, EmployeeConstant.ID_MUSTBE_NUMERIC, EmployeeConstant.ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (SQLException | EmployeeValidationException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    EmployeeConstant.con.close();
                    pst.close();
                } catch (Exception e) {
                    // Do nothing
                }
            }

        } else if (ae.getSource() == empHelper.clearButton) {
            empHelper.clear();
        }
    }

    /**
     * @param con
     * @throws SQLException
     * @throws EmployeeValidationException
     */
    private void checkExitingEmployeeId() throws SQLException, EmployeeValidationException {
        Statement st = EmployeeConstant.con.createStatement();
        String searchQuery = EmployeeConstant.SEARCH_QUERY + empHelper.idField.getText();
        ResultSet rs = st.executeQuery(searchQuery);
        if (rs.next() == true) {
            JOptionPane.showMessageDialog(this, EmployeeConstant.ID_ALREADY_EXIST, EmployeeConstant.ERROR, JOptionPane.ERROR_MESSAGE);
            throw new EmployeeValidationException(EmployeeConstant.ID_ALREADY_EXIST);
        }
        st.close();
        rs.close();
    }

}
