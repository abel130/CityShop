/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assessment;

import Forms.MainMenu;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author 30205469
 */
public class DBhandler {
    
    private Connection dbCon; 

    public DBhandler() throws SQLException, ClassNotFoundException {
        try{
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        this.dbCon = DriverManager.getConnection("jdbc:ucanaccess://src/db/ShopDB.accdb");
        }catch (SQLException ex){
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }
    
    public void Close() {
        try{
            dbCon.close();
        }
        catch (SQLException ex){
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Customer CustomerLogin(String username, String password){
        
        try {
            String sql = "Select * from Customers where username='"+username+"'and password = '"+password+"'";
            
            Statement stmt = dbCon.createStatement(); 
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next()){
                //valid user
                Customer c = new Customer();
                c.setFirstName(rs.getString("FirstName"));
                c.setLastName(rs.getString("LastName"));
                c.setUserName(rs.getString("Username"));
                c.setPassword(rs.getString("Password"));
                c.setAddressLine1(rs.getString("AddressLine1"));
                c.setAddressLine2(rs.getString("AddressLine2"));
                c.setTown(rs.getString("Town"));
                c.setPostCode(rs.getString("Postcode"));
                return c;
                
            }
            else{
                return null;
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(DBhandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
          
    }
    public Staff StaffLogin(String username, String password){
        try {
            String sql = "Select * from Staff where username='"+username+"'and password = '"+password+"'";
            
            Statement stmt = dbCon.createStatement(); 
            ResultSet rs = stmt.executeQuery(sql);
        
            if (rs.next()){
                //valid user
                Staff s = new Staff();
                s.setFirstName(rs.getString("FirstName"));
                s.setLastName(rs.getString("LastName"));
                s.setUserName(rs.getString("Username"));
                s.setPassword(rs.getString("Password"));
                s.setSalary(rs.getDouble("Salary"));
                s.setPosition(rs.getString("Position"));
                return s;
                
            }
            else{
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBhandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    public HashMap LoadProducts()
    {
        HashMap<Integer, Product> products = new HashMap();
        
        String sql = "Select * from Products";
        
        try{
            Statement stmt = dbCon.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()){
             
                if(rs.getString("Measurement").equals(""))
                {
                    Footwear fw = new Footwear();
                    fw.setProductId(rs.getInt("ProductID"));
                    fw.setProductName(rs.getString("ProductName"));
                    fw.setPrice(rs.getDouble("Price"));
                    fw.setStockLevel(rs.getInt("StockLevel"));
                    fw.setSize(rs.getInt("Size"));
                    products.put(fw.getProductId(), fw);
                    
                }else{
                    Clothing cl = new Clothing();
                    cl.setProductId(rs.getInt("ProductID"));
                    cl.setProductName(rs.getString("ProductName"));
                    cl.setPrice(rs.getDouble("Price"));
                    cl.setStockLevel(rs.getInt("StockLevel"));
                    cl.setMeasurement(rs.getString("Measurement"));
                    products.put(cl.getProductId(), cl);
                }
            }
            
        } catch (SQLException ex){ 
            Logger.getLogger(DBhandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return products;
    }
    public Boolean registerCustomer(Customer c)
    {
        try 
        {
            Statement st = dbCon.createStatement();
            ResultSet rs = null;
            String sqlstr = "Select * from customers where Username = '" + c.getUserName() + "'";
           
            rs = st.executeQuery(sqlstr);
            
        
            
            if(rs.next())
            {
                System.out.println("User already exists!");
                return false;
            }
            else
            {
              //create new user and 
              //insert into database
                String insertSQl ="INSERT INTO CUSTOMERS (username, password, firstname, lastname, addressline1, addressline2, town, postcode) VALUES(?,?,?,?,?,?,?,?)";
                
                 PreparedStatement ps = dbCon.prepareStatement(insertSQl);
                 ps.setString(1, c.getUserName());
                 ps.setString(2, c.getPassword());
                 ps.setString(3, c.getFirstName());
                 ps.setString(4, c.getLastName());
                 ps.setString(5, c.getAddressLine1());
                 ps.setString(6, c.getAddressLine2());
                 ps.setString(7, c.getTown());
                 ps.setString(8, c.getPostCode());
                 
                 int i = ps.executeUpdate();
                 
                 if(i>0)
                 {
                     ps.close();
                     System.out.println("User Registered");
                     return true;
                 }
                
                return null;
            }
            
        }catch (SQLException ex)
        {
            
        }
        return false;
    }

    public void deleteOrderLine(int orderID, int productID) throws SQLException
    {
        Statement stmt = dbCon.createStatement();
        String  sql = " DELETE from orderLines WHERE productID ="+productID +" AND orderID =" +orderID;
        stmt.execute(sql);
    }
    public void updateOrderLine(int orderLineID, int newOrderLineQuantity, double newOrderLineTotal) throws SQLException
    {
        Statement stmt = dbCon.createStatement();
        String sql = " UPDATE from orderLines SET newOrderLineQuantity =" + newOrderLineQuantity +" , newOrderLineTotal = " + newOrderLineTotal +" WHERE orderLineID = " + orderLineID ;
        stmt.execute(sql);
    }
    public void updateCustomer(Customer updatedCustomer, String user) throws SQLException
    {
        String sql = "Update customers Set username = ?, password = ?, firstname = ?, lastname = ?, addressline1 = ?, addressline2 = ?, town = ?, postcode = ? Where username =?";
        Statement stmt = dbCon.createStatement();
        ResultSet rs = null;
        PreparedStatement ps = dbCon.prepareStatement(sql);
        
        ps.setString(1, updatedCustomer.getUserName());
        ps.setString(2, updatedCustomer.getPassword());
        ps.setString(3, updatedCustomer.getFirstName());
        ps.setString(4, updatedCustomer.getLastName());
        ps.setString(5, updatedCustomer.getAddressLine1());
        ps.setString(6, updatedCustomer.getAddressLine2());
        ps.setString(7, updatedCustomer.getTown());
        ps.setString(3, updatedCustomer.getPostCode());
        ps.setString(9, user);
        
        int i = ps.executeUpdate();
        
        if(i > 0)
        {
            System.out.println("Customer details updated");
        }
        else
        {
            System.out.println("Can't find customer details");
        }
    }
    public void updateProduct(Product updatedProduct) throws SQLException
    {
        String sql;
        Statement stmt = dbCon.createStatement();
        ResultSet rs = null;
        
        if(updatedProduct.getClass().getName().equals("classess.footwear"))
        {
            sql = "Update Products set productname =?, price =?, stocklevel =?,size =? Where productID = ?";
            PreparedStatement ps = dbCon.prepareStatement(sql);
            
            Footwear fw = (Footwear) updatedProduct;
            
            ps.setString(1, fw.getProductName());
            ps.setDouble(2, fw.getPrice());
            ps.setInt(3, fw.getStockLevel());
            ps.setInt(4, fw.getSize());
            ps.setInt(5, fw.getProductId());
            
            int i = ps.executeUpdate();
        }
        else if (updatedProduct.getClass().getName().equals("classess.Clothing"))
        {
            sql = "Update Products set productname =?, price =?, stocklevel =?,measurement =? Where productID = ?";
            PreparedStatement ps = dbCon.prepareStatement(sql);
            
            Clothing cl = (Clothing) updatedProduct;
            
            ps.setString(1, cl.getProductName());
            ps.setDouble(2, cl.getPrice());
            ps.setInt(3, cl.getStockLevel());
            ps.setString(4, cl.getMeasurement());
            ps.setInt(5, cl.getProductId());
            
            int i = ps.executeUpdate();
        }
    }
    public void deleteCustomer(String username) throws SQLException
    {
        String sql = "Select * from customers where username = '" + username + "'";
        Statement stmt = dbCon.createStatement();
        ResultSet rs = null;
        
        rs = stmt.executeQuery(sql);
        
        if (rs.next())
        {
            sql = "Delete from customers where userma,e = '" + username + "'";
            int i = stmt.executeUpdate(sql);
            System.out.println("User unregistered");
        }
        
    }
    public void deleteProduct(int productId) throws SQLException
    {
       String sql = "Select * from products where productId = '" + productId + "'";
        Statement stmt = dbCon.createStatement();
        ResultSet rs = null;
        
        rs = stmt.executeQuery(sql);
        
        if (rs.next())
        {
            sql = "Delete from products where productId = '" + productId + "'";
            int i = stmt.executeUpdate(sql);
            System.out.println("Product removed");
        }else
        {
            System.out.println("Invalid Product");
        }
    }
    public int addOrder(String userName, Order order) throws SQLException
    {
        int orderID = 0 ;
        Statement stmt = dbCon.createStatement();
        String sql = "INSERT INTO order(OrderDate, Username, orderTotal, Status) VALUES ("
                + new SimpleDateFormat("dd/mm/yyyy").format(order.getOrderDate()) +" , "
                +userName + " , "
                +order.getOrderTotal() + " , "
                +order.getStatus() + " ) ";
        stmt.execute(sql);
        
        ResultSet rs = stmt.getGeneratedKeys();
        
        if (rs.next())
        {
            orderID = rs.getInt(1);
        }
        return orderID;
    }
    public int addOrderLine (int orderId , OrderLine orderline) throws SQLException
    {
        int orderLineID = 0;
        Statement stmt = dbCon.createStatement();
        String sql = "INSERT INTO OrderLines (OrderLineId, ProductId, Quantity, LineTotal, OrderId) " +
                    "VALUES ('" + orderline.getOrderLineId() + "','" + orderline.getProduct().getProductId() + "','" + 
                    orderline.getQuantity() + "','" + orderline.getLineTotal() + "','" + orderId + "')";
        stmt.execute(sql);
        updateOrderTotal(orderId, orderline.getLineTotal());
        
        ResultSet rs = stmt.getGeneratedKeys();
        
        if (rs.next())
        {
            orderLineID = rs.getInt(1);
        }
        return orderLineID;
    }
    public void updateOrderTotal(int orderId, double lineTotal) throws SQLException
    {
        Statement stmt = dbCon.createStatement();
         String sql ="UPDATE Orders SET OrderTotal = OrderTotal + " + lineTotal +
                    " WHERE OrderId = '" + orderId + "'";
        stmt.execute(sql);
    }
    public void completeOrder(int orderId) throws SQLException
    {
        Statement stmt = dbCon.createStatement();
        String sql = "UPDATE Orders SET OrderDate = '" 
                    + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()) + "',"
                    + " Status = 'Complete' WHERE OrderId = '" + orderId + "'";
        stmt.execute(sql);
    }
    public void addProduct(Product newProduct) throws SQLException
    {
        int Size = 0 ;
        String Measurement = "";
        
        if(newProduct.getClass().getName().equals("models.Clothing"))
        {
            Clothing newClothing = (Clothing)newProduct;
            Measurement = newClothing.getMeasurement();
        }
        else
        {
            Footwear newFootwear = (Footwear)newProduct;
            Size = newFootwear.getSize();
                    
        }
        Statement stmt = dbCon.createStatement();
        String sql ="INSERT INTO Products (ProductID, ProductName, Price, StockLevel, Measurement, Size) " 
            + "VALUES ('" + newProduct.getProductId() + "','" + newProduct.getProductName() 
            + "','" + newProduct.getPrice() + "','" +newProduct.getStockLevel() + "','" 
            + Measurement + "'," + Size  + "')";
        stmt.execute(sql);  
    }
    public HashMap loadCustomers() throws SQLException
    {
        HashMap customers = new HashMap();
        String sql = "select * from customers";
        Statement stmt = dbCon.createStatement();
        ResultSet rs = null;
        rs = stmt.executeQuery(sql);
        
        while(rs.next())
        {
            Customer c = new Customer();
            c.setUserName(rs.getString("Username"));
            c.setPassword(rs.getString("Password"));
            c.setFirstName(rs.getString("FirstName"));
            c.setLastName(rs.getString("LastName"));
            c.setAddressLine1(rs.getString("AddressLine1"));
            c.setAddressLine2(rs.getString("AddressLine2"));
            c.setTown(rs.getString("Town"));
            c.setPostCode(rs.getString("PostCode"));
            
            customers.put(rs.getString("Username"), c);
        }
        return customers;
    }
    public HashMap loadCustomerOrders (String user) throws SQLException
    {
        HashMap orders = new HashMap();
        String sql = "Select * from orders where username = '" + user + "'";
        int count = 0;
        
        if(user.equals(""))
        {
            sql = "select * from orders";
        }
        Statement stmt = dbCon.createStatement();
        ResultSet rs = null;
        rs = stmt.executeQuery(sql);
        
        while (rs.next())
        {
            Order order = new Order();
            order.setOrderDate(rs.getDate("OrderDate"));
            order.setOrderId(rs.getInt("OrderId"));
            order.setOrderTotal(rs.getDouble("OrderTotal"));
            order.setUsername(rs.getString("UserName"));
            order.setStatus(rs.getString("Status"));
            
           orders.put(count, order);
           count ++;
        }
        return orders;
    }
    public HashMap loadCustomerOrderLine(int orderId) throws SQLException
    {
        HashMap orderLines = new HashMap();
        String sql = "Select * from orderlines where orderid = '" + orderId + "'";
        int count = 0;
        
        Statement stmt = dbCon.createStatement();
        ResultSet rs = null;
        rs = stmt.executeQuery(sql);
        
        while (rs.next())
        {
            OrderLine ol = new OrderLine();
            ol.setOrderId(rs.getInt("OrderId"));
            ol.setProductId(rs.getInt("OrderId"));
            ol.setLineTotal(rs.getInt("OrderTotal"));
            ol.setOrderLineId(rs.getInt("UserName"));
            ol.setQuantity(rs.getInt("Status"));
            
           orderLines.put(count,ol);
           count ++;
        }
        return orderLines;
    }
     
    
    
    
    
    
}
