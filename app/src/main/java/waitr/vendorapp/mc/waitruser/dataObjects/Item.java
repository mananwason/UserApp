package waitr.vendorapp.mc.waitruser.dataObjects;

/**
 * Created by siddharth on 10/24/15.
 */

/**
 * Created by siddharth on 10/23/15.
 */
public class Item {
    private String foodImage;
    private String foodName;
    private String contents;
    private double quantity;
    private double price;
    private double rating;
    private int id;

    public Item(int id, String foodName, String foodImage, String contents, double price, double rating) {
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.contents = contents;
        this.price = price;
        this.rating = rating;
        this.id = id;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double qty) {
        this.quantity = qty;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getID() {
        return id;
    }

    public String generateSql() {
        String query_normal = "INSERT INTO %s VALUES ('%d', %s, '%f', '%f', '%d');";
        //TODO: FORMAT SQL QUERY
        return query_normal;
    }
}
