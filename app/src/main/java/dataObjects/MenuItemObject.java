package dataObjects;

import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by siddharth on 10/23/15.
 */
public class MenuItemObject {
    private Bitmap foodImage;
    private String foodName;
    private String contents;
    private float price;
    private float rating;

    public MenuItemObject(Bitmap foodImage, String foodName, String contents, float price, float rating) {
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.contents = contents;
        this.price = price;
        this.rating = rating;
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

    public void setPrice(float price) {
        this.price = price;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Bitmap getFoodImage() {

        return foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getContents() {
        return contents;
    }

    public float getPrice() {
        return price;
    }

    public float getRating() {
        return rating;
    }
}

