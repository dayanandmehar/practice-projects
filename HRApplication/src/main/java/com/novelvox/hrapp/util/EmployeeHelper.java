/**
 * 
 */
package com.novelvox.hrapp.util;

import java.awt.Component;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.novelvox.hrapp.exception.EmployeeValidationException;

/**
 * Employee helper class contains utility methods and constants
 *
 */
public class EmployeeHelper {

    public JTextField idField, nameField, aadharNoField, emailIdField, mobNoField, dobField, profileField, ctcField,
            dojField, addressField;
    public JComboBox<String> monthField;
    public JLabel idLabel, nameLabel, aadharLabel, emailLabel, mobNoLabel, dobLabel, profileLabel, ctcLabel,
            dojLabel, addressLabel, monthLabel;
    public JButton saveButton, clearButton, searchButton, exitButton, updateButton, deleteButton, downloadButton;

    /**
     * Constructor
     */
    public EmployeeHelper() {

        idLabel = new JLabel("Employee_ID *:");
        idLabel.setFont(EmployeeConstant.font4);

        nameLabel = new JLabel("Full Name *:");
        nameLabel.setFont(EmployeeConstant.font4);

        aadharLabel = new JLabel("Aadhar_NO. *:");
        aadharLabel.setFont(EmployeeConstant.font4);

        emailLabel = new JLabel("Email_ID *:");
        emailLabel.setFont(EmployeeConstant.font4);

        mobNoLabel = new JLabel("Mobile_No.:");
        mobNoLabel.setFont(EmployeeConstant.font4);

        dobLabel = new JLabel("D.O.Birth *:");
        dobLabel.setFont(EmployeeConstant.font4);

        profileLabel = new JLabel("Profile:");
        profileLabel.setFont(EmployeeConstant.font4);

        ctcLabel = new JLabel("CTC *:");
        ctcLabel.setFont(EmployeeConstant.font4);

        dojLabel = new JLabel("D.O.Joining *:");
        dojLabel.setFont(EmployeeConstant.font4);

        addressLabel = new JLabel("Address:");
        addressLabel.setFont(EmployeeConstant.font4);

        monthLabel = new JLabel("Select month:");
        monthLabel.setFont(EmployeeConstant.font4);

        // Set bound
        idLabel.setBounds(150, 130, 100, 25);
        nameLabel.setBounds(150, 170, 100, 25);
        aadharLabel.setBounds(150, 210, 100, 25);
        emailLabel.setBounds(150, 250, 100, 25);
        mobNoLabel.setBounds(150, 290, 100, 25);
        dobLabel.setBounds(150, 330, 100, 25);
        profileLabel.setBounds(150, 370, 100, 25);
        ctcLabel.setBounds(150, 410, 100, 25);
        dojLabel.setBounds(150, 450, 100, 25);
        addressLabel.setBounds(150, 490, 100, 25);
        monthLabel.setBounds(150, 170, 100, 25);

        idField = new JTextField();
        nameField = new JTextField();
        aadharNoField = new JTextField();
        emailIdField = new JTextField();
        mobNoField = new JTextField();
        dobField = new JTextField();
        profileField = new JTextField();
        ctcField = new JTextField();
        dojField = new JTextField();
        addressField = new JTextField();
        monthField = new JComboBox<String>(getPreviousSixMonths());

        // Set bound
        idField.setBounds(240, 130, 200, 25);
        nameField.setBounds(240, 170, 200, 25);
        aadharNoField.setBounds(240, 210, 200, 25);
        emailIdField.setBounds(240, 250, 200, 25);
        mobNoField.setBounds(240, 290, 200, 25);
        dobField.setBounds(240, 330, 200, 25);
        profileField.setBounds(240, 370, 200, 25);
        ctcField.setBounds(240, 410, 200, 25);
        dojField.setBounds(240, 450, 200, 25);
        addressField.setBounds(240, 490, 200, 25);
        monthField.setBounds(240, 170, 200, 25);

        idField.setToolTipText(EmployeeConstant.TOOLTIP_EMP_ID);
        nameField.setToolTipText(EmployeeConstant.TOOLTIP_EMP_NAME);
        aadharNoField.setToolTipText(EmployeeConstant.TOOLTIP_AADHAR);
        emailIdField.setToolTipText(EmployeeConstant.TOOLTIP_EMAILID);
        mobNoField.setToolTipText(EmployeeConstant.TOOLTIP_MOBILE_NUMBER);
        dobField.setToolTipText(EmployeeConstant.TOOLTIP_DOB);
        profileField.setToolTipText(EmployeeConstant.TOOLTIP_PROFILE);
        ctcField.setToolTipText(EmployeeConstant.TOOLTIP_SALARY);
        dojField.setToolTipText(EmployeeConstant.TOOLTIP_DOJ);
        addressField.setToolTipText(EmployeeConstant.TOOLTIP_ADDRESS);
        monthField.setToolTipText(EmployeeConstant.TOOLTIP_PAYSLIP_MONTH);

        saveButton = new JButton("SAVE");
        saveButton.setBounds(470, 130, 100, 25);

        clearButton = new JButton("CLEAR");
        clearButton.setBounds(470, 170, 100, 25);

        searchButton = new JButton("SEARCH");
        searchButton.setBounds(470, 130, 100, 25);

        exitButton = new JButton("EXIT");
        exitButton.setBounds(470, 170, 110, 25);

        updateButton = new JButton("UPDATE");
        updateButton.setBounds(470, 210, 100, 25);

        deleteButton = new JButton("DELETE");
        deleteButton.setBounds(470, 250, 100, 25);

        downloadButton = new JButton("DOWNLOAD");
        downloadButton.setBounds(470, 130, 110, 25);
    }

    /**
     * Create DB connection
     */
    public static void createConnection() {
        try {
            EmployeeConstant.con = DriverManager.getConnection(EmployeeConstant.DB_URL);
        } catch (SQLException e) {
            System.out.println("Failed to connect to DB: " + e.getMessage());
        }
    }

    /**
     * Close DB connection
     */
    public static void closeConnection() {
        if (EmployeeConstant.con != null) {
            try {
                EmployeeConstant.con.close();
            } catch (SQLException e) {
                System.out.println("Failed to close to DB: " + e.getMessage());
            }
        }
    }

    public String[] getPreviousSixMonths() {
        String[] preMonths = new String[7];
        preMonths[0] = EmployeeConstant.SELECT_DEFAULT;
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);

        int i = 1;
        while (i <= 6) {
            month = month - 1;
            if (month < 0) {
                month = 11;
                year = year - 1;
            }
            preMonths[i] = Month.of(month+1).getDisplayName(TextStyle.FULL, Locale.US) + " " + year;
            i++;
        }
        return preMonths;
    }

    public void clear() {
        idField.setText("");
        nameField.setText("");
        aadharNoField.setText("");
        emailIdField.setText("");
        mobNoField.setText("");
        dobField.setText("");
        profileField.setText("");
        ctcField.setText("");
        dojField.setText("");
        addressField.setText("");
    }

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    public void hideElements() {
        nameField.hide();
        aadharNoField.hide();
        emailIdField.hide();
        mobNoField.hide();
        dobField.hide();
        profileField.hide();
        ctcField.hide();
        dojField.hide();
        addressField.hide();

        nameLabel.hide();
        aadharLabel.hide();
        emailLabel.hide();
        mobNoLabel.hide();
        dobLabel.hide();
        profileLabel.hide();
        ctcLabel.hide();
        dojLabel.hide();
        addressLabel.hide();

        clearButton.hide();
        updateButton.hide();
        deleteButton.hide();
    }

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    public void showElements() {
        nameField.show();
        aadharNoField.show();
        emailIdField.show();
        mobNoField.show();
        dobField.show();
        profileField.show();
        ctcField.show();
        dojField.show();
        addressField.show();

        nameLabel.show();
        aadharLabel.show();
        emailLabel.show();
        mobNoLabel.show();
        dobLabel.show();
        profileLabel.show();
        ctcLabel.show();
        dojLabel.show();
        addressLabel.show();

        clearButton.show();
        updateButton.show();
        deleteButton.show();
    }

    /**
     * Feed employee details fields
     * 
     * @param employee
     */
    public void displayEmployeeDetails(Employee employee) {

        if (employee != null) {
            idField.setText(employee.getEmpId() != null ? employee.getEmpId().toString() : "");
            nameField.setText(employee.getEmpName());
            aadharNoField.setText(employee.getAadharNo());
            emailIdField.setText(employee.getEmailId());
            mobNoField.setText(employee.getMobileNumber());
            dobField.setText(employee.getDob());
            profileField.setText(employee.getProfile());
            ctcField.setText(employee.getCtc() != null ? employee.getCtc().toString() : "");
            dojField.setText(employee.getDoj());
            addressField.setText(employee.getAddress());

            showElements();
        }

    }

    /**
     * Read ResultSet and creates Employee object
     * 
     * @param rs
     * @return
     * @throws SQLException
     */
    public Employee readEmployeeDetails(ResultSet rs) throws SQLException {
        Employee employee = null;

        if (rs.next() == false) {
            clear();
            return null;
        } else {
            employee = new Employee();
            employee.setEmpId(rs.getInt("ID"));
            employee.setEmpName(rs.getString("Emp_Name"));
            employee.setAadharNo(rs.getString("Aadhar_No"));
            employee.setEmailId(rs.getString("Email_ID"));
            employee.setMobileNumber(rs.getString("Mobile_No"));
            employee.setDob(rs.getString("DOB"));
            employee.setProfile(rs.getString("Profile"));
            employee.setCtc(Double.parseDouble(rs.getString("CTC")));
            employee.setDoj(rs.getString("DOJ"));
            employee.setAddress(rs.getString("Address"));
        }

        return employee;
    }

    /**
     * @param employee
     * @param con
     * @return
     * @throws SQLException
     */
    public PreparedStatement createPreparedStmt(Employee employee, String query) throws SQLException {

        PreparedStatement pst = EmployeeConstant.con.prepareStatement(query);
        pst.setInt(1, employee.getEmpId());
        pst.setString(2, employee.getEmpName());
        pst.setString(3, employee.getAadharNo());
        pst.setString(4, employee.getEmailId());
        pst.setString(5, employee.getMobileNumber());
        pst.setString(6, employee.getDob());
        pst.setString(7, employee.getProfile());
        pst.setString(8, employee.getCtc() != null ? employee.getCtc().toString() : "");
        pst.setString(9, employee.getDoj());
        pst.setString(10, employee.getAddress());

        return pst;
    }

    /**
     * @return
     */
    public Employee readEmployeeData() throws NumberFormatException {

        Employee employee = new Employee();
        if (StringUtils.isNotBlank(idField.getText())) {
            employee.setEmpId(Integer.parseInt(idField.getText()));
        }
        employee.setEmpName(nameField.getText());
        employee.setAadharNo(aadharNoField.getText());
        employee.setEmailId(emailIdField.getText());
        employee.setMobileNumber(mobNoField.getText());
        employee.setDob(dobField.getText());
        employee.setProfile(profileField.getText());
        employee.setCtc(Double.parseDouble(ctcField.getText()));
        employee.setDoj(dojField.getText());
        employee.setAddress(addressField.getText());

        return employee;
    }

    /**
     * @param employee
     * @param component
     * @throws EmployeeValidationException
     */
    public void doValidation(Employee employee, Component component) throws EmployeeValidationException {
        if (!checkMandatoryFields(employee)) {
            JOptionPane.showMessageDialog(component, EmployeeConstant.ENTER_MANDARY_FIELDS, EmployeeConstant.ERROR,
                    JOptionPane.ERROR_MESSAGE);
            throw new EmployeeValidationException(EmployeeConstant.ENTER_MANDARY_FIELDS);
        }
        if (!validateAadhar(employee.getAadharNo())) {
            JOptionPane.showMessageDialog(component, EmployeeConstant.INVALID_AADHAR, EmployeeConstant.ERROR,
                    JOptionPane.ERROR_MESSAGE);
            throw new EmployeeValidationException(EmployeeConstant.INVALID_AADHAR);
        }
        if (!validateEmail(employee.getEmailId())) {
            JOptionPane.showMessageDialog(component, EmployeeConstant.INVALID_EMAILID, EmployeeConstant.ERROR,
                    JOptionPane.ERROR_MESSAGE);
            throw new EmployeeValidationException(EmployeeConstant.INVALID_EMAILID);
        }
        if (!validateMobNumber(employee.getMobileNumber())) {
            JOptionPane.showMessageDialog(component, EmployeeConstant.INVALID_MOB_NUMBER, EmployeeConstant.ERROR,
                    JOptionPane.ERROR_MESSAGE);
            throw new EmployeeValidationException(EmployeeConstant.INVALID_MOB_NUMBER);
        }
        if (!validateDateFormat(employee.getDob())) {
            JOptionPane.showMessageDialog(component, EmployeeConstant.INVALID_DOB, EmployeeConstant.ERROR,
                    JOptionPane.ERROR_MESSAGE);
            throw new EmployeeValidationException(EmployeeConstant.INVALID_DOB);
        }
        if (!validateDateFormat(employee.getDoj())) {
            JOptionPane.showMessageDialog(component, EmployeeConstant.INVALID_DOJ, EmployeeConstant.ERROR,
                    JOptionPane.ERROR_MESSAGE);
            throw new EmployeeValidationException(EmployeeConstant.INVALID_DOJ);
        }
        if (getDiffYears(employee.getDob(), employee.getDoj()) < 18) {
            JOptionPane.showMessageDialog(component, EmployeeConstant.UNDER_AGE_EMPLOYEE, EmployeeConstant.ERROR,
                    JOptionPane.ERROR_MESSAGE);
            throw new EmployeeValidationException(EmployeeConstant.UNDER_AGE_EMPLOYEE);
        }

    }

    public boolean checkMandatoryFields(Employee employee) {
        if ((employee.getEmpId() != null && StringUtils.isBlank(employee.getEmpId().toString()))
                || StringUtils.isBlank(employee.getEmpName()) || StringUtils.isBlank(employee.getAadharNo())
                || StringUtils.isBlank(employee.getEmailId()) || StringUtils.isBlank(employee.getDob())
                || StringUtils.isBlank(employee.getDoj()) || employee.getCtc() == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validateMobNumber(String mobileNumber) throws EmployeeValidationException {

        if (!StringUtils.isBlank(mobileNumber)) {
            // Regular expression to accept valid mob number
            String regex = "^\\d{10}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(mobileNumber);
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }

    public boolean validateEmail(String emailId) throws EmployeeValidationException {
        if (!StringUtils.isBlank(emailId)) {
            // Regular expression to accept valid email id
            String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(emailId);
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }

    public boolean validateAadhar(String aadharNo) throws EmployeeValidationException {

        if (!StringUtils.isBlank(aadharNo)) {
            // Regular expression to accept valid addhar number
            String regex = "^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(aadharNo);
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }

    public boolean validateDateFormat(String date) throws EmployeeValidationException {

        if (!StringUtils.isBlank(date)) {
            // Regular expression to accept valid date
            String regex = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(date);
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }

    public static int getDiffYears(String dob, String doj) {
        int diff = 0;
        try {
            Calendar a = getCalendar(EmployeeConstant.DATE_FORMATER.parse(dob));
            Calendar b = getCalendar(EmployeeConstant.DATE_FORMATER.parse(doj));
            diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
            if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || (a.get(Calendar.MONTH) == b.get(Calendar.MONTH)
                    && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
                diff--;
            }
        } catch (ParseException e) {
            System.out.println("Failed to parse date.");
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

}
