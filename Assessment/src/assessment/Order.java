/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assessment;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 30205469
 */
public class Order {
    private int orderId;
    private Date orderDate;
    private double orderTotal;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    private String status;
    private HashMap<Integer, OrderLine> orderLines;

    public Order() 
    {
        orderLines = new HashMap<>();
    }

    public Order(int orderId, Date orderDate, double orderTotal, String username, String status) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
        this.username = username;
        this.status = status;
        this.orderLines = orderLines;
    }

    public HashMap<Integer, OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(HashMap<Integer, OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public int generateUniqueOrderlineId()
    {
        int counter = 0;
        for (Map.Entry<Integer, OrderLine> entry: orderLines.entrySet()) 
        {
            if (orderLines.containsKey(counter))
            {
               counter ++;
            }
        }
        return counter;
    }
    public int getNoofProduct(int productId)
    {
        for (Map.Entry<Integer, OrderLine> entry: orderLines.entrySet())
        {
            if(entry.getValue().getProduct().getProductId() == productId)
            {
                return entry.getValue().getQuantity();
            }
        }
        return 0;
    }
    public void removeOrderLine(int productId) throws SQLException, ClassNotFoundException
    {
        Iterator<Map.Entry<Integer,OrderLine>> iterator = orderLines.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<Integer, OrderLine> olEntry = iterator.next();
            OrderLine actualOrderLine = olEntry.getValue();
            
            if(actualOrderLine.getProduct().getProductId() == productId)
            {
                iterator.remove();
                orderTotal = orderTotal - actualOrderLine.getLineTotal();
                 
                DBhandler db = new DBhandler();
                db.deleteOrderLine(orderId, productId);
                db.updateOrderTotal(orderId, -actualOrderLine.getLineTotal());
            }
                
        }
    }
    public void addOrderLine(OrderLine oLine, boolean isRegistered ) throws SQLException, ClassNotFoundException
    {
        boolean isNewProduct = true;
        int orderLineId = 0;
        
        for (Map.Entry<Integer, OrderLine> entry: orderLines.entrySet())
        {
            if(entry.getValue().getProduct().getProductId() == oLine.getProduct().getProductId())
                {
                    isNewProduct = false;
                    orderLineId = entry.getValue().getOrderLineId();
                }
        }
        if(isNewProduct)
        {
            orderLines.put(oLine.getOrderLineId(), oLine);
            int oldOrderLineId = oLine.getOrderLineId();
            
            if(isRegistered)
            {
                DBhandler db = new DBhandler();
                orderLineId = db.addOrderLine(orderId, oLine);
                orderLines.remove(oldOrderLineId, oLine);
                orderLines.put(orderLineId, oLine);
                orderLines.get(orderLineId).setOrderLineId(orderLineId);
            }
            
        }else
        {
            int newOrderLineQuantity = orderLines.get(orderLineId).getQuantity() + oLine.getQuantity();
            double newOrderLineTotal = orderLines.get(orderLineId).getLineTotal() + oLine.getLineTotal();
            orderLines.get(orderLineId).setQuantity(newOrderLineQuantity);
            orderLines.get(orderLineId).setLineTotal(newOrderLineTotal);
            if(isRegistered)
            {
                DBhandler db = new DBhandler();
                db.updateOrderLine(orderLineId, newOrderLineQuantity, newOrderLineTotal);
                db.updateOrderTotal(orderId, oLine.getLineTotal());
            }
            orderTotal = orderTotal + oLine.getLineTotal();
            
        }
        
    }
    
    
}
