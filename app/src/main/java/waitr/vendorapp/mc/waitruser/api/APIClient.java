package waitr.vendorapp.mc.waitruser.api;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import waitr.vendorapp.mc.waitruser.api.network.mAPI;

/**
 * Created by MananWason on 14-11-2015.
 */

public final class APIClient {
    /**
     * This is the base url can be changed via a config Param
     * Or Build Config
     */

    static final int CONNECT_TIMEOUT_MILLIS = 20 * 1000; // 15s

    static final int READ_TIMEOUT_MILLIS = 50 * 1000; // 20s

    static final Gson gson = new Gson();


    private final mAPI mApi;

    public APIClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

        RestAdapter adapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setEndpoint(Urls.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        mApi = adapter.create(mAPI.class);
    }

    public mAPI getmApi() {
        return mApi;
    }
}
