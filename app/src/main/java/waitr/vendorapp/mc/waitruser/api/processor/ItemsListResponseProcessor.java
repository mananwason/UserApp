package waitr.vendorapp.mc.waitruser.api.processor;

import android.util.Log;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import waitr.vendorapp.mc.waitruser.Helpers.CommonTaskLoop;
import waitr.vendorapp.mc.waitruser.api.protocol.ItemsResponseList;
import waitr.vendorapp.mc.waitruser.dataObjects.Item;

/**
 * Created by MananWason on 14-11-2015.
 */
public class ItemsListResponseProcessor implements Callback<ItemsResponseList> {
    private static final String TAG = "Items";

    @Override
    public void success(final ItemsResponseList itemsResponseList, Response response) {
        Log.d("retro", "success");

        CommonTaskLoop.getInstance().post(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> queries = new ArrayList<String>();

                for (Item item : itemsResponseList.items) {
                    String query = item.generateSql();
                    Log.d("retro", item.getId() + "");
                    queries.add(query);
                    Log.d(TAG, query);
                }

                //TODO: EXECUTE QUERIES IN THE DB
                //TODO: ADD SUCCESS EVENT ON THE BUS
            }
        });

    }


    @Override
    public void failure(RetrofitError error) {
        //TODO: ADD FAILURE EVENT ON THE BUS
        //TODO: PREVENT FROM CRASHING
        Log.d("retro", error.getCause().toString());

    }
}
