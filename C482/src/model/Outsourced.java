package model;

public class Outsourced extends Part {
    private String  companyName;

    public Outsourced(int partID, String name, double price, int numInStock, int min,int max,String companyName) {
        setPartID(partID);
        setPartName(name);
        setPartPrice(price);
        setPartInStock(numInStock);
        setMin(min);
        setMax(max);
        this.companyName = companyName;

    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
