package waitr.vendorapp.mc.waitruser.Helpers;

import android.util.Log;

import waitr.vendorapp.mc.waitruser.Events.CounterEvent;
import waitr.vendorapp.mc.waitruser.UserApp;
import waitr.vendorapp.mc.waitruser.api.APIClient;
import waitr.vendorapp.mc.waitruser.api.processor.ItemsListResponseProcessor;
import waitr.vendorapp.mc.waitruser.api.processor.OrdersListResponseProcessor;

/**
 * Created by Manan Wason on 14/11/15.
 */
public class DataDownload {

    APIClient client = new APIClient();
    private int counter;

    public void downloadItems() {
        counter = 0;
        client.getmApi().getItems(new ItemsListResponseProcessor());
        counter++;
        UserApp.postEventOnUIThread(new CounterEvent(counter));

    }

    public void downloadOrders(int userId) {
        counter = 0;
        Log.d("user_id", userId + "");
        if (userId == -1) {
            Log.d("user_id", "userId not Formed");
        } else {
            client.getmApi().getOrders(userId, new OrdersListResponseProcessor());
        }
        counter++;
        UserApp.postEventOnUIThread(new CounterEvent(counter));

    }

    public void downloadAll(int id) {
        counter = 0;
        Log.d("user_id", id + "");
        if (id == -1) {
            Log.d("user_id", "userId not Formed");
        } else {
            client.getmApi().getOrders(id, new OrdersListResponseProcessor());
        }
        client.getmApi().getItems(new ItemsListResponseProcessor());
        counter += 2;
        Log.d("download", counter + "");
        UserApp.postEventOnUIThread(new CounterEvent(counter));

    }


}
