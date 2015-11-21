package waitr.vendorapp.mc.waitruser.api.protocol;

import com.google.gson.annotations.SerializedName;

import waitr.vendorapp.mc.waitruser.dataObjects.Order;

/**
 * Created by Manan Wason on 21/11/15.
 */
public class newOrderResponse {
    @SerializedName("id")
    public Order order;

}


