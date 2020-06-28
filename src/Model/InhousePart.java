/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author homie
 */
public class InhousePart extends Part{
    
    private int machineID;
    

    public InhousePart(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }


    public int getMachineId() {
        return machineID;
    }

    public void setMachineId(int machineID) {
        this.machineID = machineID;
    }
    
    
    
}
