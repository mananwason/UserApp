package waitr.vendorapp.mc.waitruser.DbUtils;

import android.util.Log;

import waitr.vendorapp.mc.waitruser.api.APIClient;
import waitr.vendorapp.mc.waitruser.api.processor.ItemsListResponseProcessor;
import waitr.vendorapp.mc.waitruser.api.processor.OrdersListResponseProcessor;

/**
 * Created by Manan Wason on 14/11/15.
 */
public class DataDownload {

    APIClient client = new APIClient();

    public void downloadItems() {
        client.getmApi().getItems(new ItemsListResponseProcessor());
    }

    public void downloadOrders(int userId) {
        Log.d("user_id", userId + "");
        if (userId == -1) {
            Log.d("user_id", "userId is -1");
        } else {
            client.getmApi().getOrders(userId, new OrdersListResponseProcessor());
        }
    }

}
