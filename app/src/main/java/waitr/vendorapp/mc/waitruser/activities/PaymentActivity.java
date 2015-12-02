package waitr.vendorapp.mc.waitruser.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import waitr.vendorapp.mc.waitruser.DbUtils.DbContract;
import waitr.vendorapp.mc.waitruser.DbUtils.DbSingleton;
import waitr.vendorapp.mc.waitruser.Helpers.Constants;
import waitr.vendorapp.mc.waitruser.R;
import waitr.vendorapp.mc.waitruser.api.APIClient;
import waitr.vendorapp.mc.waitruser.api.protocol.newOrderResponse;
import waitr.vendorapp.mc.waitruser.dataObjects.Item;
import waitr.vendorapp.mc.waitruser.dataObjects.Order;

public class PaymentActivity extends AppCompatActivity {

    String totalCost;
    Button payNowButton;
    RadioButton cashOnPickUp, paytm;
    Map<String, String> paramMap;
    String randomInt;
    PaytmOrder paytmOrder;
    PaytmMerchant Merchant;
    StringBuilder builder;
    private PaytmPGService Service = null;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        totalCost = getIntent().getStringExtra("cost");
        payNowButton = (Button) findViewById(R.id.finalPayment);
        cashOnPickUp = (RadioButton) findViewById(R.id.cashOnPickupRadioButton);
        paytm = (RadioButton) findViewById(R.id.paytmRadioButton);
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cashOnPickUp.isChecked()) {
                    //TODO:: POST REQUEST OF ORDER. RETRIEVE ITEMS FROM CART DB, AND CLEAR CART.
                    DbSingleton dbSingleton = DbSingleton.getInstance();
                    dbSingleton.getItemsList();
                    builder = new StringBuilder();
                    for (Item item : dbSingleton.getCartList()) {
                        builder.append(item.getId()).append(",");
                    }
                    Log.d("builder", builder.toString());
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PaymentActivity.this);

                    Order order = new Order(1, sharedPreferences.getInt(Constants.UserIdKey, -1), builder.toString(), getCurrentDate(), Double.valueOf(totalCost.split(":")[1].replace(" ","")), false, false);
                    APIClient apiClient = new APIClient();
                    apiClient.getmApi().createOrder(order, new Callback<newOrderResponse>() {
                        @Override
                        public void success(newOrderResponse s, Response response) {
                            Log.d("new order", "success");
                            DbSingleton mDbSingleton = DbSingleton.getInstance();
                            mDbSingleton.deleteAllRecords(DbContract.Cart.TABLE_NAME);

                            Toast.makeText(PaymentActivity.this, "Order Received", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                            startActivity(intent);

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.d("new order", error.getCause().toString());

                        }
                    });
                } else {
                    Service = PaytmPGService.getStagingService(); //for testing environment
                    paramMap = new HashMap<>();
                    Random randomGenerator = new Random();
                    randomInt = String.valueOf(randomGenerator.nextInt(10000));
                    //these are mandatory parameters
                    paramMap.put("REQUEST_TYPE", "DEFAULT");
                    paramMap.put("ORDER_ID", randomInt);
                    paramMap.put("MID", "shopma19998593390567");
                    paramMap.put("CUST_ID", "sid0893");
                    paramMap.put("CHANNEL_ID", "WAP");
                    paramMap.put("INDUSTRY_TYPE_ID", "Retail");
                    paramMap.put("WEBSITE", "shpmteswap");
                    paramMap.put("TXN_AMOUNT", totalCost);
                    paramMap.put("THEME", "merchant");
                    paytmOrder = new PaytmOrder(paramMap);
                    Merchant = new PaytmMerchant("http://www.theshopmates.com/paytm/generate_checksum", "http://www.theshopmates.com/paytm/verify_checksum");
                    Service.initialize(paytmOrder, Merchant, null);
                    Service.startPaymentTransaction(PaymentActivity.this, true, true, new PaytmPaymentTransactionCallback() {
                        @Override
                        public void onTransactionSuccess(Bundle bundle) {
                            Toast.makeText(PaymentActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            //TODO : POST REQUEST FOR ORDER, CLEAR CART DATABASE.
                            //DbSingleton mDbSingleton = DbSingleton.getInstance();
                            //mDbSingleton.deleteAllRecords(DbContract.Cart.TABLE_NAME);
                        }

                        @Override
                        public void onTransactionFailure(String s, Bundle bundle) {
                            Toast.makeText(PaymentActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                            //DONT MAKE ANY POST REQUEST FOR ORDER. DONT CLEAR CART DATABASE.
                        }

                        @Override
                        public void networkNotAvailable() {
                            Toast.makeText(PaymentActivity.this, "Nw unavailable", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void clientAuthenticationFailed(String s) {
                            Toast.makeText(PaymentActivity.this, "Client auth failed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void someUIErrorOccurred(String s) {

                        }

                        @Override
                        public void onErrorLoadingWebPage(int i, String s, String s2) {

                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getCurrentDate() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        return nowAsISO;
    }
}
