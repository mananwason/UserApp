package waitr.vendorapp.mc.waitruser.api.protocol;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import waitr.vendorapp.mc.waitruser.dataObjects.Item;

/**
 * Created by MananWason on 14-11-2015.
 */
public class ItemsResponseList {
    @SerializedName("items")
    public List<Item> items;
}
