package com.example.c482.Model;

/**
 * Class represents a part that is produced in-house.
 *
 * @author Jamal Creary
 */

public class InHouse extends Part {
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return Returns the machine ID associated with the in-house part.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId The machine ID to be set.
     */

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
