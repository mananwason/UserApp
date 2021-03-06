package waitr.vendorapp.mc.waitruser.api.processor;

import android.util.Log;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import waitr.vendorapp.mc.waitruser.DbUtils.DbContract;
import waitr.vendorapp.mc.waitruser.DbUtils.DbSingleton;
import waitr.vendorapp.mc.waitruser.Events.OrderDownloadDoneEvent;
import waitr.vendorapp.mc.waitruser.UserApp;
import waitr.vendorapp.mc.waitruser.api.protocol.OrdersResponseList;
import waitr.vendorapp.mc.waitruser.dataObjects.Order;

/**
 * Created by Manan Wason on 15/11/15.
 */
public class OrdersListResponseProcessor implements Callback<waitr.vendorapp.mc.waitruser.api.protocol.OrdersResponseList> {
    private static final String TAG = "Orders";

    @Override
    public void success(final OrdersResponseList ordersResponseList, Response response) {

        Log.d("retro", "success");
        ArrayList<String> queries = new ArrayList<String>();

        for (Order order : ordersResponseList.orders) {
            String query = order.generateSql();
            Log.d("retro order", order.getOrderId() + "");
            queries.add(query);
            Log.d(TAG, query);
        }

        DbSingleton dbSingleton = DbSingleton.getInstance();
        dbSingleton.clearDatabase(DbContract.Orders.TABLE_NAME);
        dbSingleton.insertQueries(queries);

        UserApp.postEventOnUIThread(new OrderDownloadDoneEvent(true));

        Log.d("retro", "success");
    }

    @Override
    public void failure(RetrofitError error) {
        UserApp.postEventOnUIThread(new OrderDownloadDoneEvent(false));

        //TODO: PREVENT FROM CRASHING
//        Log.d("retro", error.getCause().toString());

    }
}
