package waitr.vendorapp.mc.waitruser.api.protocol;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import waitr.vendorapp.mc.waitruser.dataObjects.OrderObject;

/**
 * Created by Manan Wason on 15/11/15.
 */
public class OrdersResponseList {
    @SerializedName("orders")
    public List<OrderObject> orders;
}
