package com.example.c482.Model;

/**
 * Class represents a part that is outsourced.
 *
 * @author Jamal Creary
 */

public class Outsourced extends Part {

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return Returns the companyName associated with the outSourced part.
     */

    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName The Company Name to be set.
     */

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
