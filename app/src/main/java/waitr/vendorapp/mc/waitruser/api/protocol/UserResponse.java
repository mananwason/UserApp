package waitr.vendorapp.mc.waitruser.api.protocol;

import com.google.gson.annotations.SerializedName;

import waitr.vendorapp.mc.waitruser.dataObjects.User;

/**
 * Created by Manan Wason on 20/11/15.
 */
public class UserResponse {
    @SerializedName("id")
    public User user;

}
