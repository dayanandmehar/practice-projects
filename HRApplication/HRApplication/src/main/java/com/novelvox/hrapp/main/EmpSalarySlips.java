package com.novelvox.hrapp.main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;

import com.novelvox.hrapp.exception.EmployeeValidationException;
import com.novelvox.hrapp.util.Employee;
import com.novelvox.hrapp.util.EmployeeHelper;
import com.novelvox.hrapp.util.PDFGenarator;

/**
 * SALARY SLIPS FORM
 *
 */

class EmpSalarySlips extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    EmployeeHelper empHelper = null;

    EmpSalarySlips() {
        setLayout(null);
        setBackground(Color.CYAN);

        empHelper = new EmployeeHelper();
        add(EmployeeHelper.salarySlipHeading);
        add(empHelper.idLabel);
        add(empHelper.idField);
        
        add(empHelper.monthField);
        add(empHelper.monthLabel);

        empHelper.downloadButton.addActionListener(this);
        add(empHelper.downloadButton);
        empHelper.exitButton.addActionListener(this);
        add(empHelper.exitButton);

        
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == empHelper.downloadButton) {
            try {
                if (StringUtils.isBlank(empHelper.idField.getText())) {
                    JOptionPane.showMessageDialog(this, "Please enter employee Id", "Error", JOptionPane.ERROR_MESSAGE);
                    throw new EmployeeValidationException("Please enter employee Id");
                }
                if (empHelper.monthField.getSelectedItem() == null
                        || EmployeeHelper.SELECT_DEFAULT.equals(empHelper.monthField.getSelectedItem())) {
                    JOptionPane.showMessageDialog(this, "Please select month", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    throw new EmployeeValidationException("Please select month from drop down");
                }

                EmployeeHelper.createConnection();
                Statement st = EmployeeHelper.con.createStatement();

                String searchQuery = EmployeeHelper.SEARCH_QUERY + empHelper.idField.getText();
                ResultSet rs = st.executeQuery(searchQuery);

                Employee employee = empHelper.readEmployeeDetails(rs);
                if (employee == null) {
                    JOptionPane.showMessageDialog(this, "Employee Id is not found", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String selectedMonth = (String) empHelper.monthField.getSelectedItem();
                    String selectedFile = employee.getEmpName() + "_" + selectedMonth + "_Payslip.pdf";
                    JFileChooser chooser = new JFileChooser();
                    chooser.setSelectedFile(new File(selectedFile));

                    int returnVal = chooser.showSaveDialog(this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        PDFGenarator.generateSalarySlip(employee, selectedMonth, chooser.getSelectedFile());
                        JOptionPane.showMessageDialog(this, chooser.getSelectedFile() +" file downloaded successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                st.close();
                rs.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        if (ae.getSource() == empHelper.exitButton) {
            System.exit(0);
        }

    }
}
