package waitr.vendorapp.mc.waitruser.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import tourguide.tourguide.Overlay;
import tourguide.tourguide.Sequence;
import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;
import waitr.vendorapp.mc.waitruser.Events.ItemDownloadDoneEvent;
import waitr.vendorapp.mc.waitruser.Events.RefreshUiEvent;
import waitr.vendorapp.mc.waitruser.Fragments.CartFragment;
import waitr.vendorapp.mc.waitruser.Fragments.CompletedOrderFragment;
import waitr.vendorapp.mc.waitruser.Fragments.MenuFragment;
import waitr.vendorapp.mc.waitruser.Fragments.PendingOrderFragment;
import waitr.vendorapp.mc.waitruser.Fragments.SettingsFragment;
import waitr.vendorapp.mc.waitruser.Helpers.Constants;
import waitr.vendorapp.mc.waitruser.Helpers.DataDownload;
import waitr.vendorapp.mc.waitruser.R;
import waitr.vendorapp.mc.waitruser.Services.QuickstartPreferences;
import waitr.vendorapp.mc.waitruser.Services.RegistrationIntentService;
import waitr.vendorapp.mc.waitruser.UserApp;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView navigationView;
    private FrameLayout mainFrame;
    private ProgressBar downloadProgress;
    private String personName, personId, displayPic;
    private GoogleApiClient mGoogleApiClient1;
    private int counter;
    private int eventsDone;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public TourGuide mTutorialHandler;
    private Animation mEnterAnimation, mExitAnimation;
    View cart, navDrawerHamburgerIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter = 1;
        eventsDone = 0;
        downloadProgress = (ProgressBar) findViewById(R.id.progress);
        downloadProgress.setVisibility(View.VISIBLE);
        downloadProgress.setIndeterminate(true);

        DataDownload download = new DataDownload();
        download.downloadItems();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        download.downloadOrders(sharedPreferences.getInt(Constants.UserIdKey, -1));

        setUpToolbar();
        setUpNavDrawer();
        mainFrame = (FrameLayout) findViewById(R.id.layout_main);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        ImageView header = (ImageView) findViewById(R.id.headerDrawer);
        buildGoogleApiClient();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new MenuFragment()).commit();
        Intent receivedIntent = getIntent();
        personName = receivedIntent.getStringExtra("name");
        personId = receivedIntent.getStringExtra("id");
        displayPic = receivedIntent.getStringExtra("photo");    //url for dp
        cart = (View) findViewById(R.id.action_settings);
        navDrawerHamburgerIcon = (View) findViewById(android.R.id.home);
//        Log.d("url in main activity",displayPic);
//        if (displayPic != null) {
//            Picasso.with(this).load(displayPic).transform(new CircleTransform()).into(header);
//        }
        String gcmToken = sharedPreferences.getString(QuickstartPreferences.REGISTRATION_TOKEN, "");
        if (gcmToken.equals("")) {
            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    SharedPreferences sharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(context);
                    boolean sentToken = sharedPreferences
                            .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                    if (sentToken) {
                        //TODO: registration token sent to server, now?
                    } else {
                        //TODO: registration token not sent to server, now?
                    }
                }
            };

            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        } else {
            Log.d("gcm token", gcmToken);
        }

    }


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
        UserApp.getEventBus().unregister(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        UserApp.getEventBus().register(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient1.connect();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient1.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient1.isConnected()) {
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient1)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e("Access", "User access revoked!");
                        }

                    });
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient1 = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .build();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setupDrawerContent(navigationView, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new CartFragment()).commit();
            getSupportActionBar().setTitle(R.string.cart_items);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void syncComplete() {
        downloadProgress.setVisibility(View.GONE);
        Bus bus = UserApp.getEventBus();
        bus.post(new RefreshUiEvent());
        ImageView header_drawer = (ImageView) findViewById(R.id.headerDrawer);
        try {
            if (!(displayPic.isEmpty())) {
                Picasso.with(getApplicationContext()).load(Uri.parse(displayPic)).into(header_drawer);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Snackbar.make(mainFrame, getString(R.string.download_complete), Snackbar.LENGTH_SHORT).show();
    }

    private void downloadFailed() {
        downloadProgress.setVisibility(View.GONE);
        Snackbar.make(mainFrame, getString(R.string.download_failed), Snackbar.LENGTH_LONG).show();

    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    private void setUpNavDrawer() {
        if (mToolbar != null) {
            final android.support.v7.app.ActionBar ab = getSupportActionBar();
            assert ab != null;
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
            ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                    mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

            mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
            mActionBarDrawerToggle.syncState();
        }
    }

    private void setupDrawerContent(NavigationView navigationView, final Menu menu) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        menuItem.setChecked(true);
                        int id = menuItem.getItemId();

                        // menu.clear();
                        switch (id) {
                            case R.id.nav_menu:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_frame, new MenuFragment()).commit();
                                getSupportActionBar().setTitle(R.string.menu_items);

                                break;

                            case R.id.nav_pending_orders:
                                fragmentManager.beginTransaction().replace(
                                        R.id.content_frame, new PendingOrderFragment()).commit();
                                getSupportActionBar().setTitle(R.string.pending_orders);
                                break;
                            case R.id.nav_past_orders:
                                fragmentManager.beginTransaction().replace(
                                        R.id.content_frame, new CompletedOrderFragment()).commit();
                                getSupportActionBar().setTitle(R.string.past_orders);
                                break;
                            case R.id.nav_settings:
                                fragmentManager.beginTransaction().replace(
                                        R.id.content_frame, new SettingsFragment()).commit();
                                getSupportActionBar().setTitle("Notificaton Settings");
                                break;
                            case R.id.nav_sign_out:
                                logOut();
                                break;


                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    public void logOut() {
        if (mGoogleApiClient1.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient1);
            mGoogleApiClient1.disconnect();
            Toast.makeText(this, "You have been signed out successfully", Toast.LENGTH_SHORT).show();
            Intent mIntent = new Intent(this, Login.class);
            startActivity(mIntent);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient1.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mGoogleApiClient1.connect();
    }

    @Subscribe
    public void ItemsDownloadDone(ItemDownloadDoneEvent event) {
        Log.d("retro event", eventsDone + " " + counter);
        if (event.isState()) {
            eventsDone++;
            Log.d("retro event", eventsDone + " " + counter);
            if (counter == eventsDone) {
                syncComplete();
            }

        } else {
            downloadFailed();
        }

    }
}
