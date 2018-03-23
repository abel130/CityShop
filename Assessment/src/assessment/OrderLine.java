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
    private int orderLineId;
    private int quantity;
    private double lineTotal;
    private Product product;
    private int productId;
    private int orderId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderLine(int orderLineIdIn, Product productIn, int quantityIn , double lineTotalIn) {
        orderLineId = orderLineIdIn;
        product = productIn;
        quantity = quantityIn;
        lineTotal= lineTotalIn;
    }
    public OrderLine(Order orderIn, Product productIn)
    {
        orderLineId = orderIn.generateUniqueOrderlineId();
        product = productIn;
        quantity = 1;
        lineTotal = product.getPrice() * quantity;
    }
    public OrderLine(Order orderIn, Product productIn, double lineTotalIn)
    {
        orderLineId = orderIn.generateUniqueOrderlineId();
        product = productIn;
        quantity = 1;
        lineTotal = lineTotalIn;
    }

    public OrderLine(Order order, Product product, int quantity, int orderId, int productId) {
        this.orderLineId = order.generateUniqueOrderlineId();
        this.quantity = quantity;
        this.lineTotal  = quantity * product.getPrice();
        this.product = product;
        this.orderId = orderId;
        this.productId = productId;
    }
    public int getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(int orderLineId) {
        this.orderLineId = orderLineId;
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
    
}
