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
import com.novelvox.hrapp.util.EmployeeConstant;
import com.novelvox.hrapp.util.EmployeeHelper;
import com.novelvox.hrapp.util.PDFGenarator;

/**
 * SALARY SLIPS FORM
 *
 */

class EmpSalarySlips extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private EmployeeHelper empHelper = null;

    EmpSalarySlips() {
        setLayout(null);
        setBackground(Color.CYAN);

        empHelper = new EmployeeHelper();
        add(EmployeeConstant.salarySlipHeading);
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
            Statement st = null;
            ResultSet rs = null;
            try {
                if (StringUtils.isBlank(empHelper.idField.getText())) {
                    JOptionPane.showMessageDialog(this, EmployeeConstant.ENTER_ID, EmployeeConstant.ERROR, JOptionPane.ERROR_MESSAGE);
                    throw new EmployeeValidationException(EmployeeConstant.ENTER_ID);
                }
                if (empHelper.monthField.getSelectedItem() == null
                        || EmployeeConstant.SELECT_DEFAULT.equals(empHelper.monthField.getSelectedItem())) {
                    JOptionPane.showMessageDialog(this, EmployeeConstant.SELECT_MONTH, EmployeeConstant.ERROR, JOptionPane.ERROR_MESSAGE);
                    throw new EmployeeValidationException(EmployeeConstant.SELECT_MONTH);
                }

                EmployeeHelper.createConnection();
                st = EmployeeConstant.con.createStatement();

                String searchQuery = EmployeeConstant.SEARCH_QUERY + empHelper.idField.getText();
                rs = st.executeQuery(searchQuery);

                Employee employee = empHelper.readEmployeeDetails(rs);
                if (employee == null) {
                    JOptionPane.showMessageDialog(this, EmployeeConstant.ID_NOT_FOUND, EmployeeConstant.ERROR, JOptionPane.ERROR_MESSAGE);
                } else {
                    String selectedMonth = (String) empHelper.monthField.getSelectedItem();
                    String selectedFile = employee.getEmpName() + "_" + selectedMonth + "_Payslip.pdf";
                    JFileChooser chooser = new JFileChooser();
                    chooser.setSelectedFile(new File(selectedFile));

                    int returnVal = chooser.showSaveDialog(this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        PDFGenarator.generateSalarySlip(employee, selectedMonth, chooser.getSelectedFile());
                        JOptionPane.showMessageDialog(this,
                                chooser.getSelectedFile().getName() + " file downloaded successfully.", EmployeeConstant.INFO,
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
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

        if (ae.getSource() == empHelper.exitButton) {
            EmployeeHelper.closeConnection();
            System.exit(0);
        }

    }
}
