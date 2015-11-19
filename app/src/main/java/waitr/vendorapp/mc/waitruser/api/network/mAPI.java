package waitr.vendorapp.mc.waitruser.api.network;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import waitr.vendorapp.mc.waitruser.api.protocol.ItemsResponseList;
import waitr.vendorapp.mc.waitruser.api.protocol.OrdersResponseList;
import waitr.vendorapp.mc.waitruser.api.protocol.UserResponse;
import waitr.vendorapp.mc.waitruser.dataObjects.Order;
import waitr.vendorapp.mc.waitruser.dataObjects.User;

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
    void createUser(@Body User user, Callback<String> callback);

    @POST("/orders")
    void createOrder(@Body Order order, Callback<String> callback);

    @GET("/get_id")
    void getUserId(@Query("name") String name, @Query("email") String email, Callback<UserResponse> userResponseCallback);

}
