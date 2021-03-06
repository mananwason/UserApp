package waitr.vendorapp.mc.waitruser.dataObjects;

import android.database.DatabaseUtils;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import waitr.vendorapp.mc.waitruser.DbUtils.DbContract;

/**
 * Created by siddharth on 11/14/15.
 */
public class Order {

    @SerializedName("id")
    int orderId;
    @SerializedName("user_id")
    int userId;
    @SerializedName("items")
    String items;
    @SerializedName("created_at")
    String dateOfOrder;
    @SerializedName("cost")
    double costOfOrder;
    @SerializedName("status")
    Boolean isOrderCompleted;
    @SerializedName("payment_status")
    Boolean isPaymentMade;

    public Order(int orderId, int userId, String items, String dateOfOrder, double costOfOrder, Boolean isOrderCompleted, Boolean isPaymentMade) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = items;
        this.dateOfOrder = dateOfOrder;
        this.costOfOrder = costOfOrder;
        this.isOrderCompleted = isOrderCompleted;
        this.isPaymentMade = isPaymentMade;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(String dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public double getCostOfOrder() {
        return costOfOrder;
    }

    public void setCostOfOrder(double costOfOrder) {
        this.costOfOrder = costOfOrder;
    }

    public Boolean getIsOrderCompleted() {
        return isOrderCompleted;
    }

    public void setIsOrderCompleted(Boolean isOrderCompleted) {
        this.isOrderCompleted = isOrderCompleted;
    }

    public Boolean getIsPaymentMade() {
        return isPaymentMade;
    }

    public void setIsPaymentMade(Boolean isPaymentMade) {
        this.isPaymentMade = isPaymentMade;
    }

    public String generateSql() {
        String query_normal = "INSERT INTO %s VALUES ('%d', '%d', %s, '%s' , '%s' , '%d', '%d' , '%d');";
        String order_name = "Order demo";
        String query = String.format(
                query_normal,
                DbContract.Orders.TABLE_NAME,
                orderId,
                userId,
                //TODO: CHANGE TO SQLESCAPESTRING
                DatabaseUtils.sqlEscapeString(order_name),
                "df",        //TODO: fill in actual items names instead of ids, would be easy if we get names not ids from server.
                "dsds",
                (int)costOfOrder,
                isOrderCompleted?1:0,
                isPaymentMade?1:0);

                /*isOrderCompleted?"completed":"not completed",
                isPaymentMade?"paid":"not paid");*/

        Log.d("query order", query);
        return query;
    }

}

