package waitr.vendorapp.mc.waitruser.api.network;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import waitr.vendorapp.mc.waitruser.api.protocol.ItemsResponseList;
import waitr.vendorapp.mc.waitruser.api.protocol.OrdersResponseList;
import waitr.vendorapp.mc.waitruser.api.protocol.UserResponse;
import waitr.vendorapp.mc.waitruser.api.protocol.newOrderResponse;
import waitr.vendorapp.mc.waitruser.dataObjects.GcmToken;
import waitr.vendorapp.mc.waitruser.dataObjects.Order;

/**
 * Created by MananWason on 14-11-2015.
 */

public interface mAPI {

    @GET("/items")
    void getItems(Callback<ItemsResponseList> itemsResponseListCallback);

    @GET("/get_orders")
    void getOrders(@Query("user_id") int id, Callback<OrdersResponseList> ordersResponseListCallback);

    //TODO: ADD REQUESTS HERE

    @POST("/orders")
    void createOrder(@Body Order order, Callback<newOrderResponse> callback);

    @GET("/get_id")
    void getUserId(@Query("name") String name, @Query("email") String email, Callback<UserResponse> userResponseCallback);

    @POST("/set_gcm")
    void setGcm(@Body GcmToken newToken, Callback<String> callback);

}
