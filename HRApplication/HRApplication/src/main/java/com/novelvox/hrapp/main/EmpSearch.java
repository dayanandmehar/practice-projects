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
import com.novelvox.hrapp.util.EmployeeHelper;

;//

/**
 * SEARCH EMPLOYEE Details
 * 
 */
class EmpSearch extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	EmployeeHelper empHelper = null;
			
	EmpSearch() {

		setLayout(null);
		setBackground(Color.CYAN);

		empHelper = new EmployeeHelper();
		add(EmployeeHelper.searchEmpHeading);
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
			
		empHelper.hideElements();
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == empHelper.clearButton) {

			empHelper.clear();
			empHelper.hideElements();
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
				}
				st.close();
				rs.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

}
