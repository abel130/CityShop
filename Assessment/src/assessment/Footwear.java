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
public class Footwear extends Product{
    private int Size;

    public Footwear() {
        super();
    }

    public Footwear(int Size, int productId, String productName, double price, int stockLevel) {
        super(productId, productName, price, stockLevel);
        this.Size = Size;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int Size) {
        this.Size = Size;
    }
    @Override
    public String toString(){
        return "Footwear{" + "Size" + Size + '}';
    }


    
    
    
}
