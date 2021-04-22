package store.model;


import java.util.ArrayList;
import java.util.List;

public class CartManager {

    public List<CartItem> getItems() {
        return items;
    }

    List<CartItem> items = new ArrayList<CartItem>();

    public void addItem(CartItem cartItem){

        items.add(cartItem);

    }

    public float getTotal(){
        float sum = 0 ;
        for(CartItem c : items){
            sum += (c.unitPrice.get()*c.count.get());
        }
        return sum;
    }

    public int getCount(){

        return items.size();
    }

    public int getItemsCount(){
        int sum = 0 ;
        for(CartItem c : items) sum += c.count.get();
        return sum;
    }

}
