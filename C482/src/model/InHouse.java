package model;

public class InHouse extends Part {

    private int machineID;

    public InHouse( int partID, String name, double price, int numInStock, int min,int max, int machineID) {
        setPartID(partID);
        setPartName(name);
        setPartPrice(price);
        setPartInStock(numInStock);
        setMin(min);
        setMax(max);
        this.machineID = machineID;
    }

    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
