package waitr.vendorapp.mc.waitruser.api.protocol;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import waitr.vendorapp.mc.waitruser.dataObjects.User;

/**
 * Created by Manan Wason on 20/11/15.
 */
public class UserResponse {
    @SerializedName("user_id")
    public List<User> user;

}
