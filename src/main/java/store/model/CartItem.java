package store.model;

import javafx.beans.property.*;

public class CartItem {
    final SimpleStringProperty title;

    final SimpleStringProperty priceDetails;
    final SimpleFloatProperty unitPrice;
    final SimpleIntegerProperty count;

    final SimpleFloatProperty totalPrice;

    public CartItem(String title, float unitPrice, int count) {
        this.title = new SimpleStringProperty(title) ;
        this.unitPrice = new SimpleFloatProperty(unitPrice);
        this.count = new SimpleIntegerProperty(count);
        totalPrice = new SimpleFloatProperty(unitPrice*count);
        priceDetails = new SimpleStringProperty(count + " x " + unitPrice + " TND");
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty(){
        return title;
    }
    public FloatProperty unitPriceProperty(){
        return unitPrice;
    }
    public FloatProperty totalPriceProperty(){
        return totalPrice;
    }

    public IntegerProperty countProperty(){
        return count;
    }

    public float getUnitPrice() {
        return unitPrice.get();
    }

    public int getCount() {
        return count.get();
    }

    public float getTotalPrice(){
        return totalPrice.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }
    public void setUnitPrice(float unitPrice) {
        this.unitPrice.set(unitPrice);
    }
    public void setTotalPrice(float totalPrice) {
        this.totalPrice.set(totalPrice);
    }
    public void setCount(int count) {
        this.count.set(count);
    }


    String getPriceDetails(){
        return priceDetails.get();
    }


}
