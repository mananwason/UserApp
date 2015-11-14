package waitr.vendorapp.mc.waitruser;

import android.app.Application;

import waitr.vendorapp.mc.waitruser.DbUtils.DbSingleton;

/**
 * Created by Manan Wason on 15/11/15.
 */
public class UserApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        DbSingleton.init(this);
    }

}
