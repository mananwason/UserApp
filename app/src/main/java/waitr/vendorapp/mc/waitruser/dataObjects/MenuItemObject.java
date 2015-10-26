package waitr.vendorapp.mc.waitruser.dataObjects;

/**
 * Created by siddharth on 10/24/15.
 */
import android.graphics.Bitmap;

/**
 * Created by siddharth on 10/23/15.
 */
public class MenuItemObject {
    private Bitmap foodImage;
    private String foodName;
    private String contents;
    private double quantity;
    private double price;
    private double rating;
    private int id;

    public MenuItemObject(Bitmap foodImage, String foodName, String contents, double price, double rating,int id) {
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.contents = contents;
        this.price = price;
        this.rating = rating;
        this.id = id;
    }

    public void setFoodImage(Bitmap foodImage) {
        this.foodImage = foodImage;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Bitmap getFoodImage() {

        return foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setQuantity(double qty){
        this.quantity = qty;
    }

    public double getQuantity(){
        return quantity;
    }

    public String getContents() {
        return contents;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public int getID(){
        return id;
    }

}
