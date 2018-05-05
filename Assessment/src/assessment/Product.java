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
public class Product {
    int productId;
    String productName;
    double price;
    int stockLevel;

    public Product() {
    }

    public Product(int productId, String productName, double price, int stockLevel) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stockLevel = stockLevel;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }
    
    
}
