/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assessment;

import Forms.MainMenu;
import java.sql.*;
import java.text.DecimalFormat;
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
    private HashMap<Integer, Product> product;
    private HashMap<String, Customer> customer;
    private HashMap<Integer, OrderLine> orderLine;
    private HashMap<Integer, Order> order;
    private Connection dbCon; 

    public DBhandler() throws SQLException, ClassNotFoundException {
        try{
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            this.dbCon = DriverManager.getConnection("jdbc:ucanaccess://src/db/ShopDB.accdb");
        }catch (SQLException ex){
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }
    
    public void closeDB() {
        try{
            dbCon.close();
        }
        catch (SQLException ex){
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Customer customerLogin(String username, String password){
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
    
    public Staff staffLogin(String username, String password){
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

    public HashMap loadCustomer() throws SQLException
    {
        customer = new HashMap();
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
            
            customer.put(rs.getString("Username"), c);
        }
        return customer;
    }
    
    public void updateCustomer(Customer updatedCustomer, String user) throws SQLException
    {
        String sql = "Update customers Set username = ?, password = ?, firstname = ?, lastname = ?, addressline1 = ?, addressline2 = ?, town = ?, postcode = ? Where username =?";
        Statement stmt = dbCon.createStatement();
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
    
    public void deleteCustomer(String username) throws SQLException
    {
        String sql = "SELECT * FROM customers WHERE username = '" + username + "'";
        Statement stmt = dbCon.createStatement();
        ResultSet rs = null;
        
        rs = stmt.executeQuery(sql);
        
        if (rs.next())
        {
            sql = "DELETE FROM customers WHERE username = '" + username + "'";
            int i = stmt.executeUpdate(sql);
        }
    }

    public HashMap loadProduct() throws SQLException
    {
        product = new HashMap();
        String sql = "SELECT * FROM Products";
        Statement stmt = dbCon.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
            while (rs.next())
            {
                if(rs.getString("Measurement").equals(""))
                {
                    Footwear fw = new Footwear();
                    fw.setProductId(rs.getInt("ProductID"));
                    fw.setProductName(rs.getString("ProductName"));
                    fw.setPrice(rs.getDouble("Price"));
                    fw.setStockLevel(rs.getInt("StockLevel"));
                    fw.setSize(rs.getInt("Size"));
                    product.put(fw.getProductId(), fw);  
                }
                else
                {
                    Clothing cl = new Clothing();
                    cl.setProductId(rs.getInt("ProductID"));
                    cl.setProductName(rs.getString("ProductName"));
                    cl.setPrice(rs.getDouble("Price"));
                    cl.setStockLevel(rs.getInt("StockLevel"));
                    cl.setMeasurement(rs.getString("Measurement"));
                    product.put(cl.getProductId(), cl);
                }
            }
        return product;
    }
   
    public String addProduct(String productname, double price, int stock, String measurement, int size) throws SQLException
    {
        String sqlstr = "SELECT * FROM products WHERE productname = '" + productname + "'";
        Statement st = dbCon.createStatement();
        ResultSet rs = null;
        rs = st.executeQuery(sqlstr);
        
        if(rs.next())
        {
            return "product already exists";
        }
        else
        {
            if(measurement.equals("")) 
            {
               sqlstr = "INSERT INTO Products (ProductName, Price, StockLevel, Size) values (?, ?, ?, ?)";
               PreparedStatement ps = dbCon.prepareStatement(sqlstr);
               ps.setString(1, productname);
               ps.setDouble(2, price);
               ps.setInt(3, stock);
               ps.setInt(4, size);
               int i = ps.executeUpdate();
               return "New product added!";
            }
            else if(size == 0) 
            {
                sqlstr = "INSERT INTO Products (ProductName, Price, StockLevel, Measurement) values (?, ?, ?, ?)";
                PreparedStatement ps = dbCon.prepareStatement(sqlstr);
                ps.setString(1, productname);
                ps.setDouble(2, price);
                ps.setInt(3, stock);
                ps.setString(4, measurement);
                int i = ps.executeUpdate();
                return "New product added!";
            }
            return null;
        }
    }
    
    public void updateProduct(Product updatedProduct) throws SQLException
    {
        String sql;
        Statement stmt = dbCon.createStatement();
        ResultSet rs = null;
        
        if(updatedProduct.getClass().getName().equals("classess.footwear"))
        {
            sql = "UPDATE Products SET productname =?, price =?, stocklevel =?,size =? WHERE productID = ?";
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
    
    public void deleteProduct(int productId) throws SQLException
    {
        String sql = "SELECT * FROM products WHERE productId = '" + productId + "'";
        Statement stmt = dbCon.createStatement();
        ResultSet rs = null;
        
        rs = stmt.executeQuery(sql);
        
        if (rs.next())
        {
            sql = "DELETE FROM products WHERE productId = '" + productId + "'";
            int i = stmt.executeUpdate(sql);
        }
    }
    
     public Order loadOrder(String user) throws SQLException 
    {
        Order order = new Order();
        String sqlstr = "SELECT * FROM orders WHERE username = '" + user + "' AND status = 'Incomplete'";
        Statement st = dbCon.createStatement();
        ResultSet rs = null;
        
        rs = st.executeQuery(sqlstr);
        
        if(rs.next())
        {
            order.setOrderDate(rs.getDate("OrderDate"));
            order.setOrderId(rs.getInt("OrderID"));
            order.setOrderTotal(rs.getInt("OrderTotal"));
            order.setStatus(rs.getString("Status"));
        }
        
        return order;
    }
    
     public void addOrder(Customer customer) throws SQLException
    {
        Statement stmt = dbCon.createStatement();
        String sql = "SELECT * FROM orders WHERE username ='" + customer.getUserName() + "' AND status = 'Incomplete'";
        
        ResultSet rs = stmt.executeQuery(sql);
        PreparedStatement ps = null;
        
        long beef = System.currentTimeMillis();
        java.sql.Date sqlDate = new java.sql.Date(beef);
        
        if(rs.next()) 
        {
            
        }
        else
        {
            sql = "INSERT INTO Orders (Username, OrderDate, OrderTotal, Status) VALUES (?,?,?,?)";
            ps = dbCon.prepareStatement(sql);
            ps.setString(1, customer.getUserName());
            ps.setDate(2, sqlDate);
            ps.setDouble(3, 0);
            ps.setString(4, "Incomplete");
            
            ps.executeUpdate(); 
        }
    }
    
    public void moveOrder(Order order, String username) throws SQLException
    {
        Statement st = dbCon.createStatement();
        String sql = "SELECT * FROM  orders WHERE orderID =" + order.getOrderId() + "";
        ResultSet rs = st.executeQuery(sql);
        PreparedStatement ps = null;
        int orderId = order.getOrderId();
        if(rs.next())
        {
            sql = "SELECT * FROM orderlines WHERE orderID = 1";
            rs = st.executeQuery(sql);
            while(rs.next()) 
            {
                addOrderLine(null, rs.getInt("Quantity"), orderId, rs.getInt("ProductID"));
            }
            sql = "DELETE FROM orderlines WHERE orderID = 1";
            ps = dbCon.prepareStatement(sql);
            int i = ps.executeUpdate();
            updateOrderTotal(orderId);
        }
        else
        {
            sql = "INSERT INTO orders (Username, OrderTotal, Status) VALUES (?,?,?)";
            ps = dbCon.prepareStatement(sql);
            ps.executeUpdate();
            updateOrderTotal(orderId);
        }
    }
    
    public void deleteOrder(int orderId) throws SQLException
     {
        Statement st = dbCon.createStatement();
        String sql = "SELECT * FROM orders WHERE orderID = " + orderId + ";"; 
        ResultSet rs = null;
        rs = st.executeQuery(sql);
        if(rs.next()) 
        {
            sql = "DELETE FROM orders WHERE orderID = " + orderId + ";";
            st.executeUpdate(sql);
            
            sql = "DELETE FROM orderlines WHERE orderID = " + orderId + ";";
            st.executeUpdate(sql);
        }
     }
     
    public boolean completeOrder(HashMap<Integer, OrderLine> orders, int orderId) throws SQLException
    {
        String sql = "UPDATE Orders SET Status = 'Complete' WHERE orderID = " + orderId + "";
        PreparedStatement ps = dbCon.prepareStatement(sql);
        boolean ordered = false;
         for(Map.Entry<Integer, OrderLine> orderEntry : orders.entrySet())
        {
            OrderLine orderLine = orderEntry.getValue();
            ordered = updateStockLevel(orderLine.getQuantity(), orderLine.getProductID());
            if(!ordered)
            {
                break;
            }
        }
        updateOrderTotal(orderId);
        if(ordered) 
        {
            int i = ps.executeUpdate();
            
            if(i > 0) 
            {   
                return true;
            }
            else
            {
                return false;
                
            }
        }
        return false;
         
         
    }
     
    public HashMap getOrders(String user) throws SQLException 
    {
        Statement st = dbCon.createStatement();
        order = new HashMap();
        String sql = "SELECT * FROM orders WHERE username = '" + user + "'";
        ResultSet rs = null;
        int count = 0;
        if(user.equals(""))
        {
            sql = "SELECT * FROM orders";
        }
        while(rs.next()) 
        {
            Order o = new Order();
            o.setOrderDate(rs.getDate("OrderDate"));
            o.setOrderId(rs.getInt("OrderID"));
            o.setOrderTotal(rs.getDouble("OrderTotal"));
            o.setUsername(rs.getString("Username"));
            o.setStatus(rs.getString("Status"));
            
            order.put(count, o);
            count++;
        }
        return order;
    }
     
    public void updateOrderTotal(int orderId) throws SQLException
    {
        double orderTotal = 0;
        String sql = "SELECT * FROM  orderlines WHERE orderID =" + orderId + "";
        Statement st = dbCon.createStatement();
        ResultSet rs = st.executeQuery(sql);
        DecimalFormat formatter = new DecimalFormat("#0.00");
        while(rs.next()) 
        {
            orderTotal = orderTotal + rs.getDouble("LineTotal");
        }
        orderTotal = Double.parseDouble(formatter.format(orderTotal));
        sql = "UPDATE orders SET ordertotal =" + orderTotal + " WHERE orderID =" + orderId+ "";
        PreparedStatement ps = dbCon.prepareStatement(sql);
        
        ps.executeUpdate();
    }
    
    public HashMap loadOrderLine(int orderId) throws SQLException
    {
        orderLine = new HashMap();
        String sql = "SELECT * FROM orderlines WHERE orderId = '" + orderId + "'";
        int count = 0;
        Statement stmt = dbCon.createStatement();
        ResultSet rs = null;
        rs = stmt.executeQuery(sql);
        
        while (rs.next())
        {
            OrderLine ol = new OrderLine();
            ol.setOrderID(rs.getInt("OrderID"));
            ol.setProductID(rs.getInt("ProductID"));
            ol.setQuantity(rs.getInt("LineTotal"));
            ol.setOrderLineID(rs.getInt("OrderLineID"));
            ol.setQuantity(rs.getInt("Quantity"));
            
           this.orderLine.put(count,ol);
           count ++;
        }
        return orderLine;
    }
    
        public boolean addOrderLine(String productName, int quantity, int orderId, int productID) throws SQLException 
    {
        double price = 0;
        Statement st = dbCon.createStatement();
        String sql;
        PreparedStatement ps = null;
        int i = 0;
        
        if(productName == null) 
        {
            sql = "SELECT * FROM products WHERE productID = " + productID + "";
        }
        else
        {
            sql = "SELECT * FROM products WHERE productname = '" + productName + "'";
        }
        
        ResultSet rs = st.executeQuery(sql);
        
        if(rs.next()) 
        {
            productID = rs.getInt("ProductID");
            price = rs.getDouble("Price");
        }
        
        DecimalFormat formatter = new DecimalFormat("#0.00");
        price = Double.parseDouble(formatter.format(price));
        sql = "SELECT * FROM orderlines WHERE productID =" + productID + " AND orderID =" + orderId + "";
        rs = st.executeQuery(sql);
        
        if(rs.next())
        {
            sql = "UPDATE orderlines SET quantity = ?, linetotal = ? WHERE productID = ? AND orderID = ?";
            ps = dbCon.prepareStatement(sql);
            
            
            ps.setInt(1, (rs.getInt("Quantity") + quantity));
            ps.setDouble(2, price * (rs.getInt("Quantity") + quantity));
            ps.setInt(3, productID);
            ps.setInt(4, orderId);
            
            i = ps.executeUpdate();
            updateOrderTotal(orderId);
            
            if(i > 0)
            {
                return false;
            }
        }
        else
        {
            sql = "INSERT into OrderLines (ProductId, Quantity, LineTotal, OrderID) VALUES (?,?,?,?)";
            ps = dbCon.prepareStatement(sql);
            ps.setInt(1, productID);
            ps.setInt(2, quantity);
            ps.setDouble(3, (price * quantity));
            ps.setInt(4, orderId);
            
            i = ps.executeUpdate();
            updateOrderTotal(orderId);
        }
        
        if(i > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
     public boolean deleteOrderLine(int orderLineId) throws SQLException 
    {
        Statement stmt = dbCon.createStatement();
        String  sql = "select * from orderlines where orderlineID = " + orderLineId;
        ResultSet rs = null;
        rs = stmt.executeQuery(sql);
        int i = 0;
        double price = 0;
        if(rs.next()) 
        {
            if(rs.getInt("Quantity") > 1)
            {
                sql = "SELECT * FROM products WHERE productID = " + rs.getInt("ProductID");
                rs = stmt.executeQuery(sql);
                if(rs.next()) 
                {
                    price = rs.getDouble("Price");
                    
                    sql = "SELECT * FROM orderlines WHERE orderlineID = " + orderLineId;
                    rs = stmt.executeQuery(sql);
                    if(rs.next()) 
                    {
                        sql = "update orderlines set quantity =" + (rs.getInt("Quantity") - 1) + ", linetotal= " + (rs.getInt("LineTotal") - price) + " where orderLineID = " + orderLineId;
                        i = stmt.executeUpdate(sql);
                    }
                    updateOrderTotal(rs.getInt("OrderID"));
                }
            }
            else
            {
                sql = "DELETE FROM orderlines WHERE orderLineID = " +orderLineId + "";
                i = stmt.executeUpdate(sql);
                updateOrderTotal(rs.getInt("OrderID"));
            }
        }
        if(i > 0) 
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean updateStockLevel(int quantity, int productId) throws SQLException 
    {
        Statement st = dbCon.createStatement();
        String sql = "SELECT * FROM products WHERE productID =" + productId;
        ResultSet rs = st.executeQuery(sql);
        int stockLevel = 0;
        if(rs.next())
        {
            if(rs.getInt("StockLevel") >= quantity)
            {
                stockLevel = rs.getInt("StockLevel");
                sql = "UPDATE products SET stocklevel = ? WHERE productid = ?";
                PreparedStatement ps = dbCon.prepareStatement(sql);
                ps.setInt(1, (stockLevel - quantity));
                ps.setInt(2, productId);
                ps.executeUpdate();
                return true;
            }
            else
            {
                return false;
            }
        }
        return false;
    }
}
