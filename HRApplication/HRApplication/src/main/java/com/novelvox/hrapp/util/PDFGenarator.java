package com.novelvox.hrapp.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 *
 */
public class PDFGenarator {

    public static void generateSalarySlip(Employee employee, String selectedMonth, File file) throws MalformedURLException, IOException {
        Document doc = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();

            Image image = Image.getInstance(EmployeeHelper.NV_LOGO);

            Font fontCompName = new Font(FontFamily.TIMES_ROMAN, 25, Font.BOLD);
            Paragraph compName = new Paragraph("Novelvox Software India Pvt. Ltd.", fontCompName);
            compName.setAlignment(Element.ALIGN_CENTER);
            compName.setSpacingBefore(0);

            Font fontCompAddress = new Font(FontFamily.TIMES_ROMAN, 10, Font.ITALIC);
            Paragraph compAddress = new Paragraph(
                    "(6th Floor, SSR Corporate Park, Mathura Rd, Ekta Nagar, Faridabad, Haryana 121003)",
                    fontCompAddress);
            compAddress.setAlignment(Element.ALIGN_CENTER);
            compAddress.setSpacingBefore(10);
            compAddress.setSpacingAfter(20);

            Font fontSlipHead = new Font(FontFamily.TIMES_ROMAN, 20, Font.BOLD);
            Paragraph salarySlipHead = new Paragraph("Salary Slip", fontSlipHead);
            salarySlipHead.setAlignment(Element.ALIGN_CENTER);
            salarySlipHead.setSpacingAfter(20);

            Font fontEmpDetails = new Font(FontFamily.TIMES_ROMAN, 14, Font.BOLD);
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
            PdfPTable table = createSalaryTable();
            
            doc.add(image);
            doc.add(compName);
            doc.add(compAddress);
            doc.add(salarySlipHead);
            doc.add(empID);
            doc.add(empName);
            doc.add(designation);
            doc.add(salaryMonth);
            doc.add(table);
            doc.close();
            writer.close();
            
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    private static PdfPTable createSalaryTable() {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(90);
        table.setSpacingBefore(30);
        table.setSpacingAfter(30);
        PdfPCell earnings = new PdfPCell();
        earnings.addElement(new Paragraph("Earnings", new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
        earnings.setBackgroundColor(BaseColor.LIGHT_GRAY);
        earnings.setColspan(2);
        earnings.setVerticalAlignment(Element.ALIGN_CENTER);

        PdfPCell deductions = new PdfPCell();
        deductions.addElement(new Paragraph("Deductions", new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
        deductions.setBackgroundColor(BaseColor.LIGHT_GRAY);
        deductions.setColspan(2);
        deductions.setVerticalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell blankCell = new PdfPCell(new Paragraph("2"));

        table.addCell(earnings);
        table.addCell(deductions);
        table.addCell(blankCell);
        table.addCell(blankCell);
        table.addCell(blankCell);
        table.addCell(blankCell);
        return table;
    }
}
