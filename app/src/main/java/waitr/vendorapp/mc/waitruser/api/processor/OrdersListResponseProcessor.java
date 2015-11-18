package waitr.vendorapp.mc.waitruser.api.processor;

import android.util.Log;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import waitr.vendorapp.mc.waitruser.DbUtils.DbSingleton;
import waitr.vendorapp.mc.waitruser.Helpers.CommonTaskLoop;
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

        CommonTaskLoop.getInstance().post(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> queries = new ArrayList<String>();

                for (Order order : ordersResponseList.orders) {
                    String query = order.generateSql();
                    Log.d("retro order", query);
                    queries.add(query);
                    Log.d(TAG, query);
                }

                //TODO: EXECUTE QUERIES IN THE DB
                DbSingleton dbSingleton = DbSingleton.getInstance();
                dbSingleton.insertQueries(queries);
                //TODO: ADD SUCCESS EVENT ON THE BUS
            }
        });

    }

    @Override
    public void failure(RetrofitError error) {
        //TODO: ADD FAILURE EVENT ON THE BUS
        Log.d("retro", error.getCause().toString());

    }
}
