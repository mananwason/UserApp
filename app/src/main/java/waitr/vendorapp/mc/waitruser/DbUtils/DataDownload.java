package waitr.vendorapp.mc.waitruser.DbUtils;

import waitr.vendorapp.mc.waitruser.api.APIClient;
import waitr.vendorapp.mc.waitruser.api.processor.ItemsListResponseProcessor;

/**
 * Created by Manan Wason on 14/11/15.
 */
public class DataDownload {
    APIClient client = new APIClient();
    public void downloadItems() {
        client.getmApi().getItems(new ItemsListResponseProcessor());
    }
}
