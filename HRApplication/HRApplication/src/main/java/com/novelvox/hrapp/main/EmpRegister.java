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
 * NEW EMPLOYEE REGISTRATION
 *
 */
class EmpRegister extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	EmployeeHelper empHelper = null;

	EmpRegister() {
		setLayout(null);
		setBackground(Color.CYAN);
		empHelper = new EmployeeHelper();
		add(EmployeeHelper.newEmpHeading);
		add(empHelper.idLabel);
		add(empHelper.nameLabel);
		add(empHelper.aadharLabel);
		add(empHelper.emailLabel);
		add(empHelper.mobNoLabel);
		add(empHelper.dobLabel);
		add(empHelper.profileLabel);
		add(empHelper.salaryLabel);
		add(empHelper.dojLabel);
		add(empHelper.addressLabel);

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

		empHelper.saveButton.addActionListener(this);
		empHelper.clearButton.addActionListener(this);
		add(empHelper.saveButton);
		add(empHelper.clearButton);
		
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == empHelper.saveButton) {
			try {
				EmployeeHelper.createConnection();
				Employee employee = empHelper.readEmployeeData();

				// Validate data
				empHelper.doValidation(employee, this);
								
				// Validate if Employee Id is existing
				checkExitingEmployeeId();

				PreparedStatement pst = empHelper.createPreparedStmt(employee, EmployeeHelper.INSERT_QUERY);
				int n = pst.executeUpdate();

				if (n != 0) {
					JOptionPane.showMessageDialog(this, "Your data has been stored successfully", "info",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Data can not be stored", "Error", JOptionPane.ERROR_MESSAGE);
				}

				pst.close();

			} catch (SQLException | EmployeeValidationException e) {
				System.out.println(e.getMessage());
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
		Statement st = EmployeeHelper.con.createStatement();
		String searchQuery = EmployeeHelper.SEARCH_QUERY + empHelper.idField.getText();
		ResultSet rs = st.executeQuery(searchQuery);
		if (rs.next() == true) {
			JOptionPane.showMessageDialog(this, "Employee Id already exist", "Error", JOptionPane.ERROR_MESSAGE);
			throw new EmployeeValidationException("Employee Id already exist.");
		}
		st.close();
		rs.close();
	}

}
