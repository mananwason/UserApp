package waitr.vendorapp.mc.waitruser.dataObjects;

/**
 * Created by siddharth on 10/24/15.
 */

import com.google.gson.annotations.SerializedName;

import waitr.vendorapp.mc.waitruser.DbUtils.DbContract;

/**
 * Created by siddharth on 10/23/15.
 */
public class Item {
    @SerializedName("")
    private String foodImage;

    @SerializedName("name")
    private String foodName;
    private String contents;
    private double quantityOrdered;

    @SerializedName("price")
    private double price;
    private double rating;

    @SerializedName("id")
    private int id;

    @SerializedName("quantity")
    private double qtyAvailable;



    public Item(int id, String foodName, String foodImage, String contents, double price, double rating,double qtyAvailable,double quantityOrdered) {
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.contents = contents;
        this.price = price;
        this.rating = rating;
        this.id = id;
        this.qtyAvailable = qtyAvailable;
        this.quantityOrdered = quantityOrdered;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public double getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(double quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getQtyAvailable() {
        return qtyAvailable;
    }

    public void setQtyAvailable(double qtyAvailable) {
        this.qtyAvailable = qtyAvailable;
    }

    public String generateSql() {
        String query_normal = "INSERT INTO %s VALUES ('%d', %s, '%f', '%s', '%s','%f','%f','%f');";
        //TODO: FORMAT SQL QUERY
        String query = String.format(query_normal, DbContract.Items.TABLE_NAME,id,foodName,quantityOrdered,foodImage,
                contents,price,rating,qtyAvailable);
        return query;
    }
}
