package waitr.vendorapp.mc.waitruser.api.network;

import retrofit.Callback;
import retrofit.http.GET;
import waitr.vendorapp.mc.waitruser.api.protocol.ItemsResponseList;

/**
 * Created by MananWason on 14-11-2015.
 */

public interface mAPI {

    @GET("/items")
    void getItems( Callback<ItemsResponseList> itemsResponseListCallback);

    //TODO: ADD GET REQUESTS HERE
}
