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
import com.novelvox.hrapp.util.EmployeeHelper;

/**
 * UPDATE EMPLOYEE DETAILS
 *
 */

class EmpUpdate extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	EmployeeHelper empHelper = null;

	EmpUpdate() {
		setLayout(null);
		setBackground(Color.CYAN);

		empHelper = new EmployeeHelper();
		add(EmployeeHelper.updateEmpHeading);
		add(empHelper.idLabel);
		add(empHelper.idField);

		add(empHelper.nameField);
		add(empHelper.aadharNoField);
		add(empHelper.emailIdField);
		add(empHelper.mobNoField);
		add(empHelper.dobField);
		add(empHelper.profileField);
		add(empHelper.salaryField);
		add(empHelper.dojField);
		add(empHelper.addressField);

		add(empHelper.nameLabel);
		add(empHelper.aadharLabel);
		add(empHelper.emailLabel);
		add(empHelper.mobNoLabel);
		add(empHelper.dobLabel);
		add(empHelper.profileLabel);
		add(empHelper.salaryLabel);
		add(empHelper.dojLabel);
		add(empHelper.addressLabel);

		add(empHelper.clearButton);
		empHelper.clearButton.addActionListener(this);

		add(empHelper.searchButton);
		empHelper.searchButton.addActionListener(this);

		add(empHelper.updateButton);
		empHelper.updateButton.addActionListener(this);

		add(empHelper.deleteButton);
		empHelper.deleteButton.addActionListener(this);

		empHelper.hideElements();

	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == empHelper.clearButton) {
			empHelper.clear();
			empHelper.idField.enable();
		} else if (ae.getSource() == empHelper.searchButton) {

			try {
				EmployeeHelper.createConnection();
				Statement st = EmployeeHelper.con.createStatement();
				String searchQuery = EmployeeHelper.SEARCH_QUERY + empHelper.idField.getText();
				ResultSet rs = st.executeQuery(searchQuery);

				Employee employee = empHelper.readEmployeeDetails(rs);
				if (employee == null) {
					JOptionPane.showMessageDialog(this, "Employee Id is not found", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					empHelper.displayEmployeeDetails(employee);
					empHelper.idField.disable();
				}
				st.close();
				rs.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (ae.getSource() == empHelper.deleteButton) {
			try {
				Object[] options = { "Yes", "No" };

				int n = JOptionPane.showOptionDialog(this,
						"Do you like to delete the record for Employee ID: " + empHelper.idField.getText() + " ?",
						"Delete Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						options, options);

				if (n == JOptionPane.YES_OPTION) {
					EmployeeHelper.createConnection();
					Statement st = EmployeeHelper.con.createStatement();
					String deleteQuery = EmployeeHelper.DELETE_QUERY + empHelper.idField.getText();
					int value = st.executeUpdate(deleteQuery);
					if (value > 0) {
						JOptionPane.showMessageDialog(this, "Your data has been deleted successfully", "info",
								JOptionPane.INFORMATION_MESSAGE);
						empHelper.clear();
					} else {
						JOptionPane.showMessageDialog(this, "Failed to delete Employee details", "error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				System.out.println(e);
			}
		} else if (ae.getSource() == empHelper.updateButton) {
			try {
				EmployeeHelper.createConnection();
				Employee employee = empHelper.readEmployeeData();

				// Validate data
				empHelper.doValidation(employee, this);

				PreparedStatement pst = empHelper.createPreparedStmt(employee,
						EmployeeHelper.UPDATE_QUERY + employee.getEmpId());
				int n = pst.executeUpdate();

				if (n != 0) {
					JOptionPane.showMessageDialog(this, "Your data has been updated successfully", "info",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Data can not be updated", "Error", JOptionPane.ERROR_MESSAGE);
				}

				pst.close();

			} catch (SQLException | EmployeeValidationException e) {
				System.out.println(e.getMessage());
			}
		}

	}

}
