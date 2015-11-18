package waitr.vendorapp.mc.waitruser.dataObjects;

import android.database.DatabaseUtils;
import android.util.Log;

import waitr.vendorapp.mc.waitruser.DbUtils.DbContract;

/**
 * Created by siddharth on 11/14/15.
 */
public class Order {

    int orderId;
    int userId;
    int vendorId;
    String vendorName;
    String userName;
    String itemIds;
    String dateOfOrder;
    double costOfOrder;
    Boolean isOrderCompleted;
    Boolean isPaymentMade;

    public Order(int orderId, int userId, String name, String itemIds, String dateOfOrder, double costOfOrder, Boolean isOrderCompleted, Boolean isPaymentMade) {
        this.orderId = orderId;
        this.userId = userId;
        this.userName = name;
        this.itemIds = itemIds;
        this.dateOfOrder = dateOfOrder;
        this.costOfOrder = costOfOrder;
        this.isOrderCompleted = isOrderCompleted;
        this.isPaymentMade = isPaymentMade;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.userName = name;
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

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
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
        String query_normal = "INSERT INTO %s VALUES ('%d', '%d', %s, %s , %s , '%d', '%d' , '%d');";
        String query = String.format(
                query_normal,
                DbContract.Orders.TABLE_NAME,
                orderId,
                userId,
                "Order Demo",
                "df",        //TODO: fill in actual items names instead of ids, would be easy if we get names not ids from server.
                "dsds",
                (int)costOfOrder,
                1,
                1);

                /*isOrderCompleted?"completed":"not completed",
                isPaymentMade?"paid":"not paid");*/

        Log.d("query order", query);
        return query;
    }

}

