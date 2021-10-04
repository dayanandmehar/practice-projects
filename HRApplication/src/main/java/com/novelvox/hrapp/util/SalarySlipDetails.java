package com.novelvox.hrapp.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class calculates different components in salary slip 
 * 
 * Basic: 50% of salary
 * HRA: 50% of Basic
 * Conveyance: Salary - basic - HRA
 * TotalAddition: Basic + HRA + Conveyance
 * 
 * PF: 12% of Basic
 * ProfessionTax: 200 Fix
 * IncomeTax: Based on tax slabs
 *      ₹0 - ₹2,50,000   Nil
 *      ₹2,50,001 - ₹ 5,00,000  5%
 *      ₹5,00,001 - ₹ 7,50,000  ₹12500 + 10% of total income exceeding ₹5,00,000
 *      ₹7,50,001 - ₹ 10,00,000 ₹37500 + 15% of total income exceeding ₹7,50,000
 *      ₹10,00,001 - ₹12,50,000 ₹75000 + 20% of total income exceeding ₹10,00,000
 *      ₹12,50,001 - ₹15,00,000 ₹125000 + 25% of total income exceeding ₹12,50,000
 *      Above ₹ 15,00,000   ₹187500 + 30% of total income exceeding ₹15,00,000
 *  
 *  TotalDeduction: PF + professionTax + IncomeTax
 *  NetSalary: TotalAddition - TotalDeduction
 *  
 * @author dmehar
 *
 */
public class SalarySlipDetails {

    private Double basic;
    private Double hra;
    private Double conveyance;
    private Double totalAddition;
    private Double pf;
    private Double professionTax = new Double(200);
    private Double incomeTax;
    private Double totalDeduction;
    private Double netSalary;
    private Double netTaxableIncome;

    /**
     * 
     * @param ctc
     */
    public SalarySlipDetails(Double ctc) {
        Double monthlySalary = ctc/12;
        basic = monthlySalary * 0.5;
        hra = basic * 0.5;
        conveyance = monthlySalary - basic - hra;
        totalAddition = basic + hra + conveyance;
        
        pf = basic * 0.12;
        netTaxableIncome = 12 * (basic + conveyance - pf - professionTax);
        incomeTax = calculateIncomeTax();
        totalDeduction = pf + professionTax + incomeTax;
        netSalary = totalAddition - totalDeduction;
        
    }
    
    private Double calculateIncomeTax() {
        Double tax = 0.0;
        try {
            List<String> taxTableDataStrings = Files.readAllLines(Paths.get(EmployeeConstant.TAX_SLABS_FILE),
                    StandardCharsets.UTF_8);
            List<TaxSlab> taxSlabs = parseTaxTableFileData(taxTableDataStrings);

            Double tempTaxableIc;
            boolean limitReached = false;
            for (int i = 0; i < taxSlabs.size() && !limitReached; i++) {
                TaxSlab taxSlab = taxSlabs.get(i);
                if (netTaxableIncome < taxSlab.getHigherLimit() || new Double(-1).equals(taxSlab.getHigherLimit())) {
                    tempTaxableIc = netTaxableIncome;
                    limitReached = true;
                } else {
                    tempTaxableIc = taxSlab.getHigherLimit();
                }
                tax = tax + (tempTaxableIc - taxSlab.getLowerLimit() + 1) * taxSlab.getRate() / 100;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return tax / 12;
    }

    private List<TaxSlab> parseTaxTableFileData(List<String> taxTableDataStrings) {
        List<TaxSlab> taxSlabs = new ArrayList<TaxSlab>();
        for (String line : taxTableDataStrings) {
            String[] tokens = line.split(",");
            taxSlabs.add(new TaxSlab(Double.valueOf(tokens[0]), Double.valueOf(tokens[1]), Double.valueOf(tokens[2])));
        }
        return taxSlabs;
    }

    public Double getBasic() {
        return basic;
    }

    public Double getHra() {
        return hra;
    }

    public Double getConveyance() {
        return conveyance;
    }

    public Double getTotalAddition() {
        return totalAddition;
    }

    public Double getPf() {
        return pf;
    }

    public Double getProfessionTax() {
        return professionTax;
    }

    public Double getIncomeTax() {
        return incomeTax;
    }

    public Double getTotalDeduction() {
        return totalDeduction;
    }

    public Double getNetSalary() {
        return netSalary;
    }

    public Double getNetTaxableIncome() {
        return netTaxableIncome;
    }
    
}
