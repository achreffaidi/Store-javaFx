package store.model;

public class Product  implements Comparable<Product> {

    String title;
    String category;
    String description;
    float price ;
    String imageUrl;
    float offer;

    public Product(String title,String description, String category, float price,String imageUrl) {
        this.description = description;
        this.title = title;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
        this.offer = 0;
    }

    public Product(String title,String description, String category, float price,String imageUrl, float offer) {
        this(title,description,category,price,imageUrl);
        this.offer = offer;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    public float getOffer() {
        return offer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean hasOffer(){
        return offer > 0;
    }

    public float getNewPrice(){
        return price - price*offer/100;
    }

    @Override
    public int compareTo(Product o) {
        return (int)(getNewPrice() - o.getNewPrice());
    }
}
