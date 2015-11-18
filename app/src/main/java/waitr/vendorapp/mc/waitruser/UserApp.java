package waitr.vendorapp.mc.waitruser;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

import waitr.vendorapp.mc.waitruser.DbUtils.DbSingleton;

/**
 * Created by Manan Wason on 15/11/15.
 */
public class UserApp extends Application {
    private static Bus sEventBus;

    public static Bus getEventBus() {
        if (sEventBus == null) {
            sEventBus = new Bus();
        }
        return sEventBus;
    }

    public static void postEventOnUIThread(final Object event) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                getEventBus().post(event);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DbSingleton.init(this);
    }

}
