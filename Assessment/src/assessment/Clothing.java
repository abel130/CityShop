/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assessment;

/**
 *
 * @author 30205469
 */
public class Clothing extends Product {
    private String measurement;

    public Clothing() {
        super();
    }

    public Clothing(String measurement, int productId, String productName, double price, int stockLevel) {
        super(productId, productName, price, stockLevel);
        this.measurement = measurement;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
    
    
}
