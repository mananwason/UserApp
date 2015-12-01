package waitr.vendorapp.mc.waitruser.api.processor;

import android.util.Log;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import waitr.vendorapp.mc.waitruser.DbUtils.DbContract;
import waitr.vendorapp.mc.waitruser.DbUtils.DbSingleton;
import waitr.vendorapp.mc.waitruser.Events.ItemDownloadDoneEvent;
import waitr.vendorapp.mc.waitruser.Helpers.CommonTaskLoop;
import waitr.vendorapp.mc.waitruser.UserApp;
import waitr.vendorapp.mc.waitruser.api.protocol.ItemsResponseList;
import waitr.vendorapp.mc.waitruser.dataObjects.Item;

/**
 * Created by MananWason on 14-11-2015.
 */
public class ItemsListResponseProcessor implements Callback<ItemsResponseList> {
    private static final String TAG = "Items";

    @Override
    public void success(final ItemsResponseList itemsResponseList, Response response) {
        UserApp.postEventOnUIThread(new ItemDownloadDoneEvent(true));

        CommonTaskLoop.getInstance().post(new Runnable() {
            @Override
            public void run() {
                Log.d("retro", "success");
                ArrayList<String> queries = new ArrayList<String>();

                for (Item item : itemsResponseList.items) {
                    String query = item.generateSql();
//                    Log.d("retro item", item.getId() + "");
                    queries.add(query);
//                    Log.d(TAG, query);
                }

                DbSingleton dbSingleton = DbSingleton.getInstance();
                dbSingleton.clearDatabase(DbContract.Items.TABLE_NAME);
                dbSingleton.insertQueries(queries);

            }

        });

    }


    @Override
    public void failure(RetrofitError error) {
        UserApp.postEventOnUIThread(new ItemDownloadDoneEvent(false));

//        if (error.getKind().equals(RetrofitError.Kind.NETWORK))

    }
}
