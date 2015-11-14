package waitr.vendorapp.mc.waitruser.dataObjects;

import java.util.Date;

/**
 * Created by siddharth on 11/14/15.
 */
public class OrderObject {

    int orderId;
    int userId;
    int vendorId;
    String vendorName;
    String userName;
    String itemIds;
    Date dateOfOrder;
    double costOfOrder;
    String isOrderCompleted;
    String isPaymentMade;

    public OrderObject(int orderId, int userId, String name, String itemIds, Date dateOfOrder, double costOfOrder, String isOrderCompleted, String isPaymentMade) {
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

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public double getCostOfOrder() {
        return costOfOrder;
    }

    public void setCostOfOrder(double costOfOrder) {
        this.costOfOrder = costOfOrder;
    }

    public String isOrderCompleted() {
        return isOrderCompleted;
    }

    public void setIsOrderCompleted(String isOrderCompleted) {
        this.isOrderCompleted = isOrderCompleted;
    }

    public String isPaymentMade() {
        return isPaymentMade;
    }

    public void setIsPaymentMade(String isPaymentMade) {
        this.isPaymentMade = isPaymentMade;
    }

    public String generateSql() {
        String query_normal = "INSERT INTO %s VALUES ('%d', %s, '%f', '%f', '%d');";
        //TODO: FORMAT SQL QUERY
        return query_normal;
    }

}

