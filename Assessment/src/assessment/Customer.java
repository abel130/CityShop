/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assessment;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 30205469
 */
public class Customer extends User {
    private String addressLine1;
    private String addressLine2;
    private String town;
    private String postCode;
    private HashMap<Integer, Order> orders;
    private boolean isRegistered;

    public HashMap<Integer, Order> getOrders() {
        return orders;
    }

    public void setOrders(HashMap<Integer, Order> orders) {
        this.orders = orders;
    }

    public boolean isIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public Customer() {
        super();
        orders = new HashMap<>();
        isRegistered = false;
    }

    public Customer(String userName, String password, String firstName, String lastName ,String addressLine1, String addressLine2, String town, String postCode) {
        super(userName, password, firstName, lastName);
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.town = town;
        this.postCode = postCode;
        this.orders = orders;
        isRegistered = true;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
