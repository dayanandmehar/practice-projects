package com.novelvox.hrapp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 *
 */
public class PDFGenarator {

    static String text = "";

    public static void generateSalarySlip(Employee employee, String selectedMonth, File file) throws Exception {
        Document doc = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();
            SalarySlipDetails salaryDetails = new SalarySlipDetails(employee.getCtc());
            Image image = Image.getInstance(EmployeeConstant.NV_LOGO);
            doc.add(image);
            doc.add(addCompanyName());
            doc.add(addCompanyAddress());
            doc.add(addSalarySlipHeading());
            addEmployeeDetails(employee, selectedMonth, doc);
            doc.add(createSalaryTable(salaryDetails));
            doc.add(addSalaryInWords(salaryDetails.getNetSalary()));
            doc.add(addDate());
            doc.add(addLine());
            doc.add(addRemark());
            doc.close();
            writer.close();

        } catch (Exception e) {
            throw new Exception("Failed to generate salary slip: " + e.getMessage());
        }
    }

    /**
     * @param employee
     * @param selectedMonth
     * @param doc
     * @throws DocumentException
     */
    private static void addEmployeeDetails(Employee employee, String selectedMonth, Document doc)
            throws DocumentException {
        Font fontEmpDetails = new Font(FontFamily.UNDEFINED, 14, Font.BOLD);
        Paragraph empID = new Paragraph("Employee ID:      " + employee.getEmpId(), fontEmpDetails);
        empID.setIndentationLeft(50);
        // empID.setSpacingAfter(10);

        Paragraph empName = new Paragraph("Employee Name: " + employee.getEmpName(), fontEmpDetails);
        empName.setIndentationLeft(50);
        // empName.setSpacingAfter(10);

        Paragraph designation = new Paragraph("Designation:        " + employee.getProfile(), fontEmpDetails);
        designation.setIndentationLeft(50);
        // designation.setSpacingAfter(10);

        Paragraph salaryMonth = null;
        if (selectedMonth != null) {
            String[] tokens = selectedMonth.split(" ");
            salaryMonth = new Paragraph("Month: " + tokens[0] + "    Year: " + tokens[1], fontEmpDetails);
            salaryMonth.setIndentationLeft(50);
        }
        doc.add(empID);
        doc.add(empName);
        doc.add(designation);
        doc.add(salaryMonth);
    }

    /**
     * @return
     * @throws DocumentException
     */
    private static Paragraph addSalarySlipHeading() throws DocumentException {
        Font fontSlipHead = new Font(FontFamily.UNDEFINED, 20, Font.BOLD);
        Paragraph salarySlipHead = new Paragraph("Salary Slip", fontSlipHead);
        salarySlipHead.setAlignment(Element.ALIGN_CENTER);
        salarySlipHead.setSpacingAfter(20);
        return salarySlipHead;
    }

    /**
     * @throws DocumentException
     */
    private static Paragraph addCompanyAddress() throws DocumentException {
        Font fontCompAddress = new Font(FontFamily.UNDEFINED, 10, Font.ITALIC);
        Paragraph compAddress = new Paragraph(
                "(6th Floor, SSR Corporate Park, Mathura Rd, Ekta Nagar, Faridabad, Haryana 121003)", fontCompAddress);
        compAddress.setAlignment(Element.ALIGN_CENTER);
        compAddress.setSpacingBefore(10);
        compAddress.setSpacingAfter(20);
        return compAddress;
    }

    /**
     * @return
     * @throws DocumentException
     */
    private static Paragraph addCompanyName() throws DocumentException {
        Font fontCompName = new Font(FontFamily.UNDEFINED, 25, Font.BOLD);
        Paragraph compName = new Paragraph("Novelvox Software India Pvt. Ltd.", fontCompName);
        compName.setAlignment(Element.ALIGN_CENTER);
        compName.setSpacingBefore(0);
        return compName;
    }

    /**
     * @param double1
     * @return
     */
    private static PdfPTable createSalaryTable(SalarySlipDetails salaryDetails) {

        Font font1 = new Font(FontFamily.UNDEFINED, 12, Font.BOLD);
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(30);
        PdfPCell earnings = new PdfPCell();
        Paragraph ear = new Paragraph("Earnings", font1);
        ear.setAlignment(Element.ALIGN_CENTER);
        ear.setSpacingAfter(10);
        earnings.addElement(ear);
        earnings.setBackgroundColor(BaseColor.LIGHT_GRAY);
        earnings.setColspan(2);

        PdfPCell deductions = new PdfPCell();
        Paragraph ded = new Paragraph("Deductions", font1);
        ded.setAlignment(Element.ALIGN_CENTER);
        ded.setSpacingAfter(10);
        deductions.addElement(ded);
        deductions.setBackgroundColor(BaseColor.LIGHT_GRAY);
        deductions.setColspan(2);

        PdfPCell basic = new PdfPCell(new Phrase("Basic"));
        PdfPCell basicValue = new PdfPCell(new Phrase(getStringValue(salaryDetails.getBasic())));

        PdfPCell pf = new PdfPCell(new Phrase("Provident Fund"));
        PdfPCell pfValue = new PdfPCell(new Phrase(getStringValue(salaryDetails.getPf())));

        PdfPCell hra = new PdfPCell(new Phrase("HRA"));
        PdfPCell hraValue = new PdfPCell(new Phrase(getStringValue(salaryDetails.getHra())));

        PdfPCell profTax = new PdfPCell(new Phrase("Professional tax"));
        PdfPCell profTaxValue = new PdfPCell(new Phrase(getStringValue(salaryDetails.getProfessionTax())));

        PdfPCell conveyance = new PdfPCell(new Phrase("Conveyance"));
        PdfPCell conveyanceValue = new PdfPCell(new Phrase(getStringValue(salaryDetails.getConveyance())));

        PdfPCell icTax = new PdfPCell(new Phrase("Income Tax"));
        PdfPCell icTaxValue = new PdfPCell(new Phrase(getStringValue(salaryDetails.getIncomeTax())));

        PdfPCell totalAdd = new PdfPCell(new Phrase("Total Addition"));
        PdfPCell totalAddValue = new PdfPCell(new Phrase(getStringValue(salaryDetails.getTotalAddition())));

        PdfPCell totalDed = new PdfPCell(new Phrase("Total Deduction"));
        PdfPCell totalDedValue = new PdfPCell(new Phrase(getStringValue(salaryDetails.getTotalDeduction())));

        PdfPCell netSal = new PdfPCell(new Phrase("Net Salary", font1));
        netSal.setBackgroundColor(BaseColor.LIGHT_GRAY);
        Paragraph netSalary = new Paragraph(getStringValue(salaryDetails.getNetSalary()));
        netSalary.setFont(font1);
        PdfPCell netSalValue = new PdfPCell(netSalary);
        netSalValue.setBackgroundColor(BaseColor.LIGHT_GRAY);

        PdfPCell blank = new PdfPCell(new Phrase(" "));

        table.addCell(earnings);
        table.addCell(deductions);
        table.addCell(basic);
        table.addCell(basicValue);
        table.addCell(pf);
        table.addCell(pfValue);
        table.addCell(hra);
        table.addCell(hraValue);
        table.addCell(profTax);
        table.addCell(profTaxValue);
        table.addCell(conveyance);
        table.addCell(conveyanceValue);
        table.addCell(icTax);
        table.addCell(icTaxValue);
        table.addCell(blank);
        table.completeRow();
        table.addCell(blank);
        table.completeRow();
        table.addCell(totalAdd);
        table.addCell(totalAddValue);
        table.addCell(totalDed);
        table.addCell(totalDedValue);
        table.addCell(blank);
        table.addCell(blank);
        table.addCell(netSal);
        table.addCell(netSalValue);
        table.completeRow();
        table.addCell(blank);
        table.completeRow();

        return table;
    }

    /**
     * @param
     * @return
     */
    private static String getStringValue(Double value) {
        if (value != null) {
            BigDecimal bd = BigDecimal.valueOf(value);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            return bd.toString();
        }
        return null;
    }

    private static Element addSalaryInWords(Double netSalary) {
        Paragraph datePara = new Paragraph("Rupees " + convertNumber(netSalary) + "Only", new Font(FontFamily.UNDEFINED, 12, Font.BOLD));
        datePara.setSpacingBefore(10);
        return datePara;
    }

    private static Element addDate() {
        Paragraph datePara = new Paragraph(
                "Dated As: " + EmployeeConstant.DATE_FORMATER.format(Calendar.getInstance().getTime()));
        datePara.setSpacingBefore(20);
        return datePara;
    }

    private static Element addLine() {
        Paragraph linePara = new Paragraph("______________________________________________________________________________");
        linePara.setSpacingBefore(80);
        return linePara;
    }

    private static Element addRemark() {
        Paragraph datePara = new Paragraph(EmployeeConstant.REMARK);
        return datePara;
    }

    private static String convertNumber(double num) {
        int rem = 0;
        int i = 0;
        while (num > 0) {
            if (i == 0) {
                rem = (int) (num % 1000);
                generateText(rem);
                num = num / 1000;
                i++;
            } else if (num > 0) {
                rem = (int) (num % 100);
                if (rem > 0) {
                    text = EmployeeConstant.denom[i - 1] + " " + text;
                }
                generateText(rem);
                num = num / 100;
                i++;
            }
        }
        
        return text;
    }

    public static void generateText(int num) {
        if (!(num > 9 && num < 19)) {
            if (num % 10 > 0) {
                text = EmployeeConstant.ones[(num % 10) - 1] + " " + text;
            }
            num = num / 10;
            if (num % 10 > 0) {
                text = EmployeeConstant.tens[(num % 10) - 2] + " " + text;
            }

            num = num / 10;
            if (num > 0) {
                text = EmployeeConstant.hundreds[num - 1] + " " + text;
            }
        } else {
            text = EmployeeConstant.splNums[num % 10] + " " + text;
        }
    }
}
