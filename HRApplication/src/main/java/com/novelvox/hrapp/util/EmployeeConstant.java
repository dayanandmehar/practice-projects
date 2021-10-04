package com.novelvox.hrapp.util;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;

public class EmployeeConstant {

    public static final String DB_URL = "jdbc:ucanaccess://./resources/EmpDB.accdb";
    public static Connection con = null;
    public static final String INSERT_QUERY = "insert into Emp1([ID],[Emp_Name],[Aadhar_No],[Email_ID],[Mobile_No],[DOB],[Profile],[CTC],[DOJ],[Address]) values (?,?,?,?,?,?,?,?,?,?)";
    public static final String UPDATE_QUERY = "update Emp1 set ID=?, Emp_Name=?, Aadhar_No=?, Email_ID=?,Mobile_No=?,DOB=?,Profile=?,CTC=?,DOJ=?,Address=? where ID=";
    public static final String SEARCH_QUERY = "select * from Emp1 where id=";
    public static final String DELETE_QUERY = "delete from Emp1 where id=";
    public static final String SELECT_DEFAULT = "<select>";

    public static final String NV_LOGO = "./resources/NovelVox-Logo.jpg";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final SimpleDateFormat DATE_FORMATER = new SimpleDateFormat(EmployeeConstant.DATE_FORMAT);
    public static final String TAX_SLABS_FILE = "./resources/TaxSlabs.csv";

    public static Font font3 = new Font("SansSerif", Font.BOLD, 30);
    public static Font font4 = new Font("SansSerif", Font.BOLD, 12);

    // Frame headings
    public static JLabel newEmpHeading = new JLabel("New Employee");
    public static JLabel searchEmpHeading = new JLabel("Search Employee Details");
    public static JLabel updateEmpHeading = new JLabel("Update Employee Details");
    public static JLabel salarySlipHeading = new JLabel("Generate Salary Slip");

    // Tooltips
    public static final String TOOLTIP_EMP_ID = "Enter Unique Employee ID";
    public static final String TOOLTIP_EMP_NAME = "Enter Employee full name";
    public static final String TOOLTIP_AADHAR = "Enter Aadhar card number i.e. 9876 1223 3456";
    public static final String TOOLTIP_EMAILID = "Enter Valid Email Address";
    public static final String TOOLTIP_MOBILE_NUMBER = "Enter 10 digit Mobile number";
    public static final String TOOLTIP_DOB = "Enter Employee Date of Birth in DD/MM/YYYY format";
    public static final String TOOLTIP_PROFILE = "Enter Employee Profile";
    public static final String TOOLTIP_SALARY = "Enter employee's Cost to Company in Rs.";
    public static final String TOOLTIP_DOJ = "Enter Employee Date of Joining in DD/MM/YYYY format. At the time of Joining Employee should not be under 18 years of age";
    public static final String TOOLTIP_ADDRESS = "Enter Employee Address";
    public static final String TOOLTIP_PAYSLIP_MONTH = "Select month to get salary slip.";

    public static final Object[] options = { "Yes", "No" };
    // Error messages
    public static final String ERROR = "Error";
    public static final String INFO = "Info";
    public static final String DATA_STORED_SUCCESS = "Your data has been stored successfully";
    public static final String DATA_STORED_FAILURE = "Data can not be stored";
    public static final String ENTER_ID = "Please enter employee Id";
    public static final String ID_MUSTBE_NUMERIC = "Employee ID must be Numeric";
    public static final String ID_ALREADY_EXIST = "Employee Id already exist";
    public static final String ID_NOT_FOUND = "Employee Id is not found";
    public static final String DELETE_RECORD_QUE = "Do you like to delete the record for Employee ID: ";
    public static final String DELETE_RECORD_CONF = "Delete Confirmation";
    public static final String DELETE_RECORD_SUCCESS = "Your data has been deleted successfully";
    public static final String DELETE_RECORD_FAILURE = "Failed to delete Employee details";
    public static final String UPDATE_RECORD_SUCCESS = "Your data has been updated successfully";
    public static final String UPDATE_RECORD_FAILURE = "Data can not be updated";
    public static final String SELECT_MONTH = "Please select month";
    public static final String ENTER_MANDARY_FIELDS = "Please Enter all Mandatory Fields";
    public static final String INVALID_AADHAR = "Invalid Aadhar card number";
    public static final String INVALID_EMAILID = "Invalid Email address";
    public static final String INVALID_MOB_NUMBER = "Invalid mobile number";
    public static final String INVALID_DOB = "Invalid Date of Birth";
    public static final String INVALID_DOJ = "Invalid Date of joining";
    public static final String UNDER_AGE_EMPLOYEE = "Employee is under 18 years of age at the time of joining";

    public static final String REMARK = "Remark: This is computer genarated document. Company seal and signature is not required to authenticate the document.";

    public static final String[] hundreds = {"One Hundred", "Two Hundred", "Three Hundred", "Four Hundred", "Five Hundred", "Six Hundred", "Seven Hundred", "Eight Hundred", "Nine Hundred"};
    public static final String[] tens ={"Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    public static final String[] ones= {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
    public static final String[] denom ={"Thousand", "Lakhs", "Crores"};
    public static final String[] splNums ={ "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    
    static {
        newEmpHeading.setForeground(Color.BLUE);
        newEmpHeading.setHorizontalAlignment(JLabel.CENTER);
        newEmpHeading.setFont(EmployeeConstant.font3);
        newEmpHeading.setBounds(50, 30, 500, 50);

        searchEmpHeading.setForeground(Color.BLUE);
        searchEmpHeading.setHorizontalAlignment(JLabel.CENTER);
        searchEmpHeading.setFont(EmployeeConstant.font3);
        searchEmpHeading.setBounds(50, 30, 600, 50);

        updateEmpHeading.setForeground(Color.BLUE);
        updateEmpHeading.setHorizontalAlignment(JLabel.CENTER);
        updateEmpHeading.setFont(EmployeeConstant.font3);
        updateEmpHeading.setBounds(50, 30, 600, 50);

        salarySlipHeading.setForeground(Color.BLUE);
        salarySlipHeading.setHorizontalAlignment(JLabel.CENTER);
        salarySlipHeading.setFont(EmployeeConstant.font3);
        salarySlipHeading.setBounds(50, 30, 600, 50);

        EmployeeHelper.createConnection();
    }
    
}
