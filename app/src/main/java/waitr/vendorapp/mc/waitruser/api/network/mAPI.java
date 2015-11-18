package waitr.vendorapp.mc.waitruser.api.network;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import waitr.vendorapp.mc.waitruser.api.protocol.ItemsResponseList;
import waitr.vendorapp.mc.waitruser.api.protocol.OrdersResponseList;

/**
 * Created by MananWason on 14-11-2015.
 */

public interface mAPI {

    @GET("/items.json")
    void getItems(Callback<ItemsResponseList> itemsResponseListCallback);

    @GET("/orders.json")
    void getOrders(Callback<OrdersResponseList> ordersResponseListCallback);

    //TODO: ADD GET REQUESTS HERE

    @POST("/users")
    void createUser(Callback<String> callback);
}
