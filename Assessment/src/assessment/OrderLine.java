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
public class OrderLine {
    int productID;
    int orderID;
    int orderLineID;
    int quantity;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getOrderLineID() {
        return orderLineID;
    }

    public void setOrderLineID(int orderLineID) {
        this.orderLineID = orderLineID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(double lineTotal) {
        this.lineTotal = lineTotal;
    }
    double lineTotal;

    public OrderLine() 
    {
        
    }
    public OrderLine(int orderLineID, int productID, int quantity, double lineTotal) 
    {
        this.productID = productID;
        this.orderLineID = orderLineID;
        this.quantity = quantity;
        this.lineTotal = lineTotal;
    }
    
    
}
