/**
 * 
 */
package com.novelvox.hrapp.util;

/**
 * @author dmehar
 *
 */
public class TaxSlab {

    private Double lowerLimit;
    private Double higherLimit;
    private Double rate;
    
    public TaxSlab(Double lowerLimit, Double higherLimit, Double rate) {
        this.lowerLimit = lowerLimit;
        this.higherLimit = higherLimit;
        this.rate = rate;
    }

    public Double getLowerLimit() {
        return lowerLimit;
    }

    public Double getHigherLimit() {
        return higherLimit;
    }

    public Double getRate() {
        return rate;
    }
    
}
